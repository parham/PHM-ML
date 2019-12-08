
package com.phm.test;

import com.phm.ml.ParametersContainer;
import com.phm.ml.siggen.ThresholdImage2DSignalGenerator;
import com.phm.ml.nn.extra.io.NeuronGroupOutputStream;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.algorithms.gng.mgng.MGNGTrainingSupervisor;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.gui.NeuronGroupPresenterFrame;
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
 * @author phm
 */
public class TestMGNG {
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        NeuronGroup group = new NeuronGroup(new double [2]);
        MGNGTrainingSupervisor tmp = new MGNGTrainingSupervisor();
        tmp.initialize(group);
        group.supervisors.add(tmp);
        group.analyzers.add((NeuronAnalyzer) (String state, Neuron n,
                        ParametersContainer param, Instance current,
                        NNResultContainer result) -> {
            System.out.println (param.get(Neuron.RECIEVED_SIGNALS_NUM));
        });
        BufferedImage img = ImageIO.read(new File("/home/phm/Pictures/sample2.png"));
        ThresholdImage2DSignalGenerator sig = new ThresholdImage2DSignalGenerator(img, new Color(100, 100, 100), ThresholdImage2DSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator sg = new NeuronSignalGenerator (sig);
        NNResultContainer result = new NNResultContainer ();
        sg.signalAll(group, "mgng.train", result);
        System.out.println("It is finished ...");
        new Thread(() -> {
            NeuronGroupPresenterFrame frMain = new NeuronGroupPresenterFrame(group);
            frMain.setVisible(true);
        }).start();
        try {
            try (NeuronGroupOutputStream out = new NeuronGroupOutputStream(
                    new FileOutputStream("/home/phm/mgng.txt"))) {
                out.write(group);
                out.flush();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestMGNG.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestMGNG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
