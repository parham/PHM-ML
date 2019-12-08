
package com.phm.test;

import com.phm.ml.ParametersContainer; 
import com.phm.ml.siggen.ThresholdImage2DSignalGenerator;
import com.phm.ml.nn.NeuronGroup; 
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.algorithms.ng.NGTrainingSupervisor;
import com.phm.ml.nn.extra.io.NeuronGroupOutputStream;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.gui.NeuronGraphFrame;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
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
 * @author PARHAM
 */
public class TestNG {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        NeuronGroup group = new NeuronGroup(new double [2]);
        BufferedImage img = ImageIO.read(new File("/home/phm/Pictures/coins.png"));
        ThresholdImage2DSignalGenerator sig = new ThresholdImage2DSignalGenerator(img, new Color(100, 100, 100), ThresholdImage2DSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator sg = new NeuronSignalGenerator (sig);
        
        NGTrainingSupervisor tmp = new NGTrainingSupervisor(2, 100, sg.countRemainSignals());
        group.supervisors.add(tmp);
        group.analyzers.add ((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
            System.out.println(n.getParameter(Neuron.RECIEVED_SIGNALS_NUM));
        });
        tmp.initialize(group);
        NNResultContainer result = new NNResultContainer ();
        sg.signalAll(group, "ng.train", result);
        System.out.println("It is finished ...");
        
        NeuronGraphFrame frMain = new NeuronGraphFrame(group);
        frMain.setVisible(true);
        //sg.signalAll(group, "gng.train");
        //panelNeurons.repaint();
        try {
//            NeuronGroupOutputStream out = new NeuronGroupOutputStream(
//                    new FileOutputStream("G:\\PHM - I AM ONE\\Projects\\Review on Neural Network for using on 3D downsampling\\gng.txt"));
            try (NeuronGroupOutputStream out = new NeuronGroupOutputStream(
                    new FileOutputStream("/home/phm/ng.txt"))) {
                //            NeuronGroupOutputStream out = new NeuronGroupOutputStream(
//                    new FileOutputStream("G:\\PHM - I AM ONE\\Projects\\Review on Neural Network for using on 3D downsampling\\gng.txt"));
                out.write(group);
                out.flush();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestNG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestNG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
