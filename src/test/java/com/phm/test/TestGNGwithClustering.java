
package com.phm.test;

import com.phm.ml.ParametersContainer;
import com.phm.ml.clusterer.graphbased.ConnectivityBasedGraphClusterer;
import com.phm.ml.clusterer.graphbased.GraphBasedClusterer;
import com.phm.ml.clusterer.graphbased.Xia2008Clusterer;
import com.phm.ml.gui.GraphVisualComponent;
import com.phm.ml.gui.GraphFrame;
import com.phm.ml.siggen.ThresholdImage2DSignalGenerator;
import com.phm.ml.nn.extra.io.NeuronGroupOutputStream;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.algorithms.gng.GNGTrainingSupervisor;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.connection.ConnectionFactory;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.triangulation.GNGNoLearnTriangulator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import org.jgrapht.Graph;

/**
 *
 * @author phm
 */
public class TestGNGwithClustering {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        NeuronGroup group = new NeuronGroup(new double[2]);
        ////////////////////////
        GNGTrainingSupervisor tmp = new GNGTrainingSupervisor();
        group.supervisors.add(tmp);
        group.analyzers.add((NeuronAnalyzer) (String state, Neuron n,
                        ParametersContainer param, Instance current,
                        NNResultContainer result) -> {
            System.out.println (param.get(Neuron.RECIEVED_SIGNALS_NUM) + " --> " + group.neurons.size());
        });
        tmp.initialize(group);
        BufferedImage img = ImageIO.read(new File("/home/phm/Pictures/sample2.png"));
        ThresholdImage2DSignalGenerator sig = new ThresholdImage2DSignalGenerator(img, new Color(0, 0, 0), ThresholdImage2DSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator sg = new NeuronSignalGenerator (sig);
        NNResultContainer result = new NNResultContainer ();
        sg.signalAll(group, tmp.getName(), result);
        System.out.println("It is finished ...");
        
        ConnectivityBasedGraphClusterer<Neuron, Connection> xc = new ConnectivityBasedGraphClusterer (new ConnectionFactory());
//        GraphBasedClusterer xc = new GraphBasedClusterer(new GNGNoLearnTriangulator(
//                                                          new ParametersContainer()), 
//                                                          new ConnectivityBasedGraphClusterer (
//                                                          new ConnectionFactory()));
        DefaultDataset ns = new DefaultDataset();
        group.neurons.asDataSet(ns);
        List<Graph<Neuron, Connection>> graph = xc.clusterGraph(group);
        
        GraphFrame<Neuron, Connection> frMain = new GraphFrame ();
        for (Graph<Neuron, Connection> gx : graph) {
            GraphVisualComponent<Neuron, Connection> gvc = new GraphVisualComponent<>(gx);
            gvc.autoColoring = true;
            frMain.addGraph(gvc);
        }
        frMain.setVisible(true);
        int snum = (int) group.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
        System.out.println("Signals --> " + snum);
    }
    
}
