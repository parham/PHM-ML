
package com.phm.test;

import com.mxgraph.util.mxConstants;
import com.phm.ml.ParametersContainer;
import com.phm.ml.gui.GraphFrame;
import com.phm.ml.gui.GraphVisualComponent;
import com.phm.ml.siggen.ThresholdImage2DSignalGenerator;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.algorithms.soinn.SOINNSupervisor;
import static com.phm.ml.nn.algorithms.soinn.SOINNSupervisor.SOINN_LANDA_KEY;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class TestSOINN {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        NeuronGroup group = new NeuronGroup(new double[2]);
        ////////////////////////
        SOINNSupervisor tmp = new SOINNSupervisor ();
        group.supervisors.add(tmp);
        group.analyzers.add((NeuronAnalyzer) (String state, Neuron n,
                        ParametersContainer param, Instance current,
                        NNResultContainer result) -> {
            System.out.println (param.get(Neuron.RECIEVED_SIGNALS_NUM) + " --> " + group.neurons.size());
        });
        tmp.initialize(group);
        group.setParameter(SOINN_LANDA_KEY, 1100);
        BufferedImage img = ImageIO.read(new File("/home/phm/Pictures/sample2.png"));
        ThresholdImage2DSignalGenerator sig = new ThresholdImage2DSignalGenerator(img, new Color(0, 0, 0), ThresholdImage2DSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator sg = new NeuronSignalGenerator (sig);
        NNResultContainer result = new NNResultContainer ();
        sg.signalAll(group, tmp.getName(), result);
        System.out.println("It is finished ...");
//        GraphFrame<Neuron, Connection> frMain = new GraphFrame (group);
//        frMain.setVisible(true);
        GraphFrame<Neuron, Connection> frMain = new GraphFrame ();
        frMain.addGraph(new GraphVisualComponent<>(group));
        frMain.setVisible(true);
        int snum = (int) group.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
        System.out.println("Signals --> " + snum);
    }
    
}
