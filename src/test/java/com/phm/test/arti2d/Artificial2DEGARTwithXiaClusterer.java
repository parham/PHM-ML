
package com.phm.test.arti2d;

import com.phm.ml.ParametersContainer;
import com.phm.ml.clusterer.graphbased.GraphBasedClusterer;
import com.phm.ml.clusterer.graphbased.Xia2008Clusterer;
import com.phm.ml.io.CSVFileDatasetSaver;
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
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;
import com.phm.ml.triangulation.GNGNoLearnTriangulator;
import java.io.File;
import java.io.IOException;
import java.util.List;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import org.apache.commons.csv.CSVFormat;
import org.jgrapht.Graph;

/**
 *
 * @author phm
 */
public class Artificial2DEGARTwithXiaClusterer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //File path = new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/artificial data/edata.png");
        File path = new File ("/home/phm/Datasets/Arti2D/dataset.csv");
        DefaultDataset ds = new DefaultDataset();
        ArtificialDatasetLoader ads = new ArtificialDatasetLoader(path, CSVFormat.EXCEL);
        if (!ads.loadWithoutNoise(ds)) {
            System.out.println ("Dataset is failed to load");
        }
        ListedSignalGenerator sig = new ListedSignalGenerator(ds, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        System.out.println ("Dataset size : " + ds.size());
//        CSVFileDatasetSaver csvs = new CSVFileDatasetSaver(
//                            new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/artificial data/data.csv"), 
//                            CSVFormat.TDF);
//        if (csvs.save(ds)) {
//            System.out.println ("Dataset is saved ...");
//        } else {
//            System.out.println ("Dataset is failed ...");
//        }
        
        NeuronGroup ng = new NeuronGroup(new double[2]);
        ExtendedGARTTrainingSupervisor egart = new ExtendedGARTTrainingSupervisor (new PhmGARTLRC(1.0f, 0.2f, 0.0001f));
        ng.supervisors.add(egart);
        egart.initialize(ng);
        ng.setParameter(ART_VIGILANCE_PARAMTER, 0.70f);
        ng.setParameter(ART_VIGILANCE_PARAMTER, 0.70f);
        ng.analyzers.add ((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
            int numsig = (int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
            int numn = (int) ((NeuronGroup) n).neurons.size();
            System.out.println (numsig + " --> " + numn);
        });
        NeuronSignalGenerator nsig = new NeuronSignalGenerator(sig);
        nsig.signalAll(ng, egart.getName(), new NNResultContainer());
        System.out.println ("Learning is finished ...");
        GraphFrame<Neuron,Connection> ngf = new GraphFrame ();
        ngf.addGraph(new GraphVisualComponent<>(ng));
        ngf.setVisible(true);
        GraphBasedClusterer xc = new GraphBasedClusterer(new GNGNoLearnTriangulator(
                                                          new ParametersContainer()), 
                                                          new Xia2008Clusterer (
                                                          new ConnectionFactory()));
//        Xia2008Clusterer<Neuron, Connection> xc = new Xia2008Clusterer (
//                                              new HebbianCompetitiveTriangulator<>(
//                                              new ConnectionFactory(), new EuclideanDistance()),
//                                              new ConnectionFactory());        
        DefaultDataset ns = new DefaultDataset();
        ng.neurons.asDataSet(ns);
        List<Graph<Neuron, Connection>> graph = xc.clusterAsGraph(ns);
        GraphFrame<Neuron,Connection> gf = new GraphFrame ();
//        gf.setGraph(graph);
        gf.setVisible(true);
        DefaultDataset resds = new DefaultDataset();
        File resfile = new File ("/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/topoart_data/result_egart_dataset.csv");
        CSVFileDatasetSaver csvs = new CSVFileDatasetSaver(resfile, CSVFormat.EXCEL);
        for (Graph<Neuron,Connection> g : graph) {
            g.vertexSet().stream().forEach((Neuron n) -> {
                resds.add(n);
            });
        }
        csvs.save(resds);
    }
}
