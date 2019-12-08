
package com.phm.test.arti2d;

import com.phm.ml.ParametersContainer;
import com.phm.ml.clusterer.ClassBasedClusterer;
import com.phm.ml.gui.GraphFrame;
import com.phm.ml.gui.GraphVisualComponent;
import com.phm.ml.io.CSVFileDatasetSaver;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import static com.phm.ml.nn.algorithms.art.ARTMAPTrainingSupervisor.ARTMAP_EPSILON;
import static com.phm.ml.nn.algorithms.art.ARTTrainingSupervisor.ART_VIGILANCE_PARAMTER;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTMAPTrainingSupervisor;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTTrainingSupervisor;
import static com.phm.ml.nn.algorithms.art.gaussian.GaussianARTTrainingSupervisor.NEURON_DEFAULT_STD;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.connection.ConnectionFactory;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;
import org.apache.commons.csv.CSVFormat;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

/**
 *
 * @author phm
 */
public class Artificial2DGaussianARTMAPMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File path = new File ("/home/phm/Datasets/Arti2D/dataset.csv");
        //File path = new File ("/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/topoart_data/dataset.csv");
        // Training dataset
        DefaultDataset dstrain = new DefaultDataset();
        ArtificialDatasetLoader ads = new ArtificialDatasetLoader(path, CSVFormat.EXCEL);
        if (!ads.load(dstrain)) {
            System.out.println ("Dataset is failed to load");
        }
        // Testing dataset
        DefaultDataset dstest = new DefaultDataset();
        int testnum = (int) (0.1 * dstrain.size());
        Random rand = new Random(System.currentTimeMillis());
        for (int index = 0; index < testnum; index++) {
            int ind = rand.nextInt(dstrain.size());
            Instance testmp = dstrain.remove(ind);
            dstest.add(testmp);
        }
        
        ListedSignalGenerator sigTrain = new ListedSignalGenerator(dstrain, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        ListedSignalGenerator sigTest = new ListedSignalGenerator(dstest, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        
        NeuronSignalGenerator nsigTrain = new NeuronSignalGenerator(sigTrain);
        NeuronSignalGenerator nsigTest = new NeuronSignalGenerator(sigTest);
        
        System.out.println ("Dataset size : " + dstrain.size());
        
        NeuronGroup ng = new NeuronGroup(new double[2]);
        GaussianARTMAPTrainingSupervisor gart = new GaussianARTMAPTrainingSupervisor ();
        ng.supervisors.add(gart);
        gart.initialize(ng);
        ng.setParameter(ART_VIGILANCE_PARAMTER, 0.98f);
        ng.setParameter(NEURON_DEFAULT_STD, 0.9f);
        ng.setParameter(ARTMAP_EPSILON, -0.02f);
//        ng.analyzers.add ((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
//            int numsig = (int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
//            int numn = (int) ((NeuronGroup) n).neurons.size();
//            System.out.print (numsig + " --> " + numn);
//            if (result.getRecent().winners.size() > 0) {
//                System.out.print (" : " + result.getRecent().winners.get(0).classValue() + " :::: " + current.classValue());
//            }
//            System.out.println ();
//        });
        ng.analyzers.add ((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
            if (result.getRecent().winners.size() > 0) {
                int numsig = (int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
                int numn = (int) ((NeuronGroup) n).neurons.size();
                int wid = result.getRecent().winners.get(0).getID();
                EuclideanDistance ed = new EuclideanDistance();
                double dis = Math.abs (ed.calculateDistance (result.getRecent().winners.get(0), current));
                System.out.println (numsig + "\t" + numn + "\t" + wid + "\t" + dis);
            }
        });
        
        nsigTrain.signalAll(ng, gart.getName(), new NNResultContainer());
        System.out.println ("Learning is finished ...");
        
        File resfile = new File ("/home/phm/Dropbox/Projects/Topological Bayesian ARTMAP/Publication/results/experiment ART2D/bartmap.csv");
        CSVFileDatasetSaver csvs = new CSVFileDatasetSaver(resfile, CSVFormat.EXCEL);
        DefaultDataset dd = new DefaultDataset();
        ng.neurons.asDataSet(dd);
        csvs.save(dd);
        
        GraphFrame<Neuron, Connection> ngf = new GraphFrame ();
        ngf.addGraph (new GraphVisualComponent<>(ng));
        ngf.setVisible(true);
        
        DefaultDataset resds = new DefaultDataset();
        ng.neurons.asDataSet(resds);
        
        ClassBasedClusterer cbc = new ClassBasedClusterer();
        Dataset [] dres = cbc.cluster(resds);
        for (Dataset d : dres) {
            System.out.println (d.get(0).classValue() + " = " + d.size());
        }
        GraphFrame<Neuron, Connection> frMain = new GraphFrame ();
        for (Dataset d : dres) {
            Graph<Neuron, Connection> gt = new SimpleGraph (new ConnectionFactory());
            d.stream().forEach((x) -> {
                gt.addVertex(new Neuron(x));
            });
            GraphVisualComponent<Neuron,Connection> gv = new GraphVisualComponent<>(gt);
            gv.autoColoring = true;
            frMain.addGraph(gv);
        }
        frMain.setVisible(true);

        // Testing phase
        GaussianARTTrainingSupervisor gartTest = new GaussianARTTrainingSupervisor();
        ng.supervisors.add(gartTest);
        gartTest.initialize(ng);
        
        HashMap<String, Float> ac = new HashMap<>();
        HashMap<String, Float> acCat = new HashMap<>();
        int tot = sigTest.countRemainSignals();
        while (nsigTest.hasNext()) {
            NNResultContainer res = new NNResultContainer ();
            nsigTest.signal (ng, gartTest.getName(), res);
            String lbl = (String) res.getRecent().winners.get(0).classValue();
            String lblsig = (String) res.getRecent().signal.classValue();
            acCat.put(lblsig, acCat.getOrDefault(lblsig, 0.0f) + 1);
            if (lblsig != null && lbl != null && lbl.contentEquals(lblsig)) {
                ac.put(lblsig, ac.getOrDefault(lblsig, 0.0f) + 1);
            }
        }
        
        for (String key : ac.keySet()) {
            ac.put(key, ac.get(key) / acCat.get(key));
        }
        System.out.println (ac);
        
//        File resfile = new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/topoart_data/result_gartmap_dataset.csv");
//        CSVFileDatasetSaver csvs = new CSVFileDatasetSaver(resfile, CSVFormat.EXCEL);
//        csvs.save(resds);
    }
    
}
