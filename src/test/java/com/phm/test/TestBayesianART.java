
package com.phm.test;

import com.phm.ml.ParametersContainer;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.art.bayesian.BayesianARTTrainingSupervisor;
import static com.phm.ml.nn.algorithms.art.bayesian.BayesianARTTrainingSupervisor.BAYESIANART_MAX_HYPERVOLUME;
import static com.phm.ml.nn.algorithms.art.bayesian.BayesianARTTrainingSupervisor.BAYESIANART_VARIANCE_CONST_INITIALIZER;
import com.phm.ml.nn.extra.io.NeuronGroupOutputStream;
import com.phm.ml.nn.gui.NeuronGraphFrame;
import com.phm.ml.nn.gui.NeuronGroupPresenterFrame;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ThresholdImage2DSignalGenerator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class TestBayesianART {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        LinkedList<float []> list = new LinkedList<>();
        list.add(new float[2]);
        NeuronGroup group = new NeuronGroup (new double [2]);

        BayesianARTTrainingSupervisor tmp = new BayesianARTTrainingSupervisor(2);
        tmp.initialize(group);
        group.supervisors.add(tmp);
        group.setParameter(BAYESIANART_MAX_HYPERVOLUME, 20.0f);
        group.setParameter(BAYESIANART_VARIANCE_CONST_INITIALIZER, 0.01f);
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
        ThresholdImage2DSignalGenerator sig = new ThresholdImage2DSignalGenerator (img, new Color(100, 100, 100), ThresholdImage2DSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator sg = new NeuronSignalGenerator (sig);
        System.out.println ("Number of Signals in the dataset : " + sig.countRemainSignals());
        
        NNResultContainer result = new NNResultContainer ();
        sg.signalAll(group, tmp.getName(), result);
        System.out.println("It is finished ...");
        int snum = (int) group.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
        System.out.println("Signals --> " + snum);
        NeuronGraphFrame frMain = new NeuronGraphFrame (group);
        frMain.setVisible(true);
        try (NeuronGroupOutputStream out = new NeuronGroupOutputStream(
                new FileOutputStream("/home/phm/bart.txt"))) {
            out.write(group);
            out.flush();
        }
    }
}
