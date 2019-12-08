
package com.phm.test;

import com.phm.ml.ParametersContainer;
import com.phm.ml.distance.DefaultDistanceMethod;
import com.phm.ml.nn.algorithms.som.KohonenRandomizeInitializer;
import com.phm.ml.nn.algorithms.som.KohonenTrainingSupervisor;
import com.phm.ml.siggen.ThresholdImage2DSignalGenerator;
import com.phm.ml.nn.extra.io.NeuronGroupOutputStream;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.NeuronDistanceMethod;
import com.phm.ml.nn.gui.NeuronGraphFrame;
import com.phm.ml.nn.gui.NeuronGroupPresenterFrame;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;

/**
 *
 * @author phm
 */
public class TestKohonen {
    public static void main(String[] args) throws IOException {

        NeuronGroup group = new NeuronGroup(new double[2]);
        ////////////////////////
        List<double []> bounds = new LinkedList<>();
        double [] b1 = {0.5f, 0.6f};
        bounds.add(b1);
        double [] b2 = {0.5f, 0.6f};
        bounds.add(b2);
        
        BufferedImage img = ImageIO.read(new File("/home/phm/Pictures/coins.png"));
        ThresholdImage2DSignalGenerator sig = new ThresholdImage2DSignalGenerator(img, new Color(0, 0, 0), ThresholdImage2DSignalGenerator.GeneratorPolicy.GP_RANDOM);
        
        KohonenTrainingSupervisor tmp = new KohonenTrainingSupervisor(new KohonenRandomizeInitializer(bounds), 
                                                                      new NeuronDistanceMethod(new DefaultDistanceMethod(new EuclideanDistance())), 
                                                                      new NeuronDistanceMethod(new DefaultDistanceMethod(new EuclideanDistance())), 
                                                                      20, 20, sig.countRemainSignals());
        group.supervisors.add(tmp);
        group.analyzers.add((NeuronAnalyzer) (String state, Neuron n,
                        ParametersContainer param, Instance current,
                        NNResultContainer result) -> {
            System.out.println (param.get(Neuron.RECIEVED_SIGNALS_NUM) + " --> " + group.neurons.size());
        });
        tmp.initialize(group);
        
        NeuronSignalGenerator sg = new NeuronSignalGenerator (sig);
        NNResultContainer result = new NNResultContainer ();
        sg.signalAll(group, tmp.getName(), result);
        System.out.println("It is finished ...");
        NeuronGraphFrame frMain = new NeuronGraphFrame(group);
        frMain.setVisible(true);
        int snum = (int) group.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
        System.out.println("Signals --> " + snum);
        try (NeuronGroupOutputStream out = new NeuronGroupOutputStream(
                new FileOutputStream("/home/phm/gng.txt"))) {
            out.write(group);
            out.flush();
        }
    }
}
