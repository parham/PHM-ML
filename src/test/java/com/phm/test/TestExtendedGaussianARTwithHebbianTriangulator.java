
package com.phm.test;

import com.phm.ml.ParametersContainer;
import com.phm.ml.gui.GraphFrame;
import com.phm.ml.gui.GraphVisualComponent;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import static com.phm.ml.nn.algorithms.art.ARTTrainingSupervisor.ART_VIGILANCE_PARAMTER;
import com.phm.ml.nn.algorithms.art.egaussian.ExtendedGARTTrainingSupervisor;
import com.phm.ml.nn.algorithms.art.egaussian.PhmGARTLRC;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.connection.ConnectionFactory;
import com.phm.ml.nn.gui.NeuronGroupPresenterFrame;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ThresholdImage2DSignalGenerator;
import com.phm.ml.triangulation.HebbianCompetitiveTriangulator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;
import org.jgrapht.Graph;

/**
 *
 * @author phm
 */
public class TestExtendedGaussianARTwithHebbianTriangulator {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        NeuronGroup group = new NeuronGroup (new double[2]);

        ExtendedGARTTrainingSupervisor tmp = new ExtendedGARTTrainingSupervisor(new PhmGARTLRC(1.0f, 0.2f, 0.0001f));
        group.supervisors.add(tmp);
        tmp.initialize(group);
        group.setParameter(ART_VIGILANCE_PARAMTER, 0.70f);
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
        new Thread(() -> {
            NeuronGroupPresenterFrame frMain = new NeuronGroupPresenterFrame(group);
            frMain.setVisible(true);
        }).start();
        
        HebbianCompetitiveTriangulator<Neuron, Connection> gngt = 
                new HebbianCompetitiveTriangulator (new ConnectionFactory(), 
                                                    new EuclideanDistance());
        DefaultDataset dd = new DefaultDataset ();
        group.neurons.asDataSet(dd);
        Graph<Neuron, Connection> g = gngt.triangulate(dd);
        new Thread(() -> {
            GraphFrame<Neuron, Connection> frMain = new GraphFrame ();
            frMain.addGraph(new GraphVisualComponent<>(g));
            frMain.setVisible(true);
        }).start();
        
//        ///////////////////////
//        NeuronGroup group2 = new NeuronGroup(new double[2]);
//        ////////////////////////
//        GNGNoLearnSupervisor tmp2 = new GNGNoLearnSupervisor ();
//        group2.supervisors.add(tmp2);
//        group2.analyzers.add((NeuronAnalyzer) (String state, 
//                                               Neuron n,
//                                               ParametersContainer param, 
//                                               Instance current,
//                                               NNResultContainer result2) -> {
//            System.out.println (param.get(Neuron.RECIEVED_SIGNALS_NUM) + " --> " + group2.neurons.size());
//        });
//        tmp2.initialize(group2);
//        group2.setParameter(GNG_MAX_EDGE_AGE_KEY, 20);
//        group2.setParameter(GNG_LANDA_KEY, 1);
//        DefaultDataset ns = new DefaultDataset();
//        group.neurons.asDataSet(ns);
//        NeuronSignalGenerator sg2 = new NeuronSignalGenerator (new ListedSignalGenerator(ns, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM));
//        NNResultContainer result2 = new NNResultContainer ();
//        sg2.signalAll(group2, tmp2.getName(), result2);
//        new Thread(() -> {
//            NeuronGroupPresenterFrame frMain = new NeuronGroupPresenterFrame(group2);
//            frMain.setVisible(true);
//        }).start();
//        try (NeuronGroupOutputStream out = new NeuronGroupOutputStream(
//                new FileOutputStream("/home/phm/egart.txt"))) {
//            out.write(group);
//            out.flush();
//        }
    }
}
