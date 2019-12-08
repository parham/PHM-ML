
package com.phm.test;

import com.phm.ml.ParametersContainer;
import com.phm.ml.gui.GraphFrame;
import com.phm.ml.gui.GraphVisualComponent;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTMAPTrainingSupervisor;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTTrainingSupervisor;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.extra.io.NeuronGroupOutputStream;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ThresholdImage2DSignalGenerator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class TestGaussianARTMAP {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        NeuronGroup group = new NeuronGroup (new double[2]);
        GaussianARTMAPTrainingSupervisor tmp = new GaussianARTMAPTrainingSupervisor();
        tmp.initialize(group);
        group.supervisors.add(tmp);
        //group.parameters.put(FUZZYART_LEARNING_RATE, 1.0f);
        group.analyzers.add((NeuronAnalyzer) 
                            (String state, 
                             Neuron n, 
                             ParametersContainer param, 
                             Instance current, 
                             NNResultContainer result) -> {
            System.out.println ((int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM) + " ---> " +
                                ((NeuronGroup) n).neurons.size());
        });

        BufferedImage img = ImageIO.read(new File("/home/phm/Pictures/sample2.png"));
        ThresholdImage2DSignalGenerator sig = new ThresholdImage2DSignalGenerator (img, new Color(0, 0, 0), ThresholdImage2DSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator sg = new NeuronSignalGenerator (sig);
        
        NNResultContainer result = new NNResultContainer ();
        sg.signalAll(group, tmp.getName(), result);
        System.out.println("It is finished ...");
        int snum = (int) group.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
        System.out.println("Signals --> " + snum);
        
        GraphFrame<Neuron, Connection> frMain = new GraphFrame ();
        frMain.addGraph(new GraphVisualComponent<>(group));
        frMain.setVisible(true);
        
        try {
//            NeuronGroupOutputStream out = new NeuronGroupOutputStream(
//                    new FileOutputStream("G:\\PHM - I AM ONE\\Projects\\Review on Neural Network for using on 3D downsampling\\mgng.txt"));
            try (NeuronGroupOutputStream out = new NeuronGroupOutputStream(
                    new FileOutputStream("/home/phm/gart.txt"))) {
                //            NeuronGroupOutputStream out = new NeuronGroupOutputStream(
//                    new FileOutputStream("G:\\PHM - I AM ONE\\Projects\\Review on Neural Network for using on 3D downsampling\\mgng.txt"));
                out.write(group);
                out.flush();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestFuzzyART.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestFuzzyART.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
