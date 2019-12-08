
package com.phm.test.arti2d;

import com.phm.ml.ParametersContainer;
import com.phm.ml.gui.GraphFrame;
import com.phm.ml.gui.GraphVisualComponent;
import com.phm.ml.io.CSVFileDatasetLoader;
import com.phm.ml.io.CSVFileDatasetSaver;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.gng.GNGTrainingSupervisor;
import static com.phm.ml.nn.algorithms.gng.GNGTrainingSupervisor.GNG_LANDA_KEY;
import static com.phm.ml.nn.algorithms.gng.GNGTrainingSupervisor.GNG_MAX_EDGE_AGE_KEY;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;
import java.io.File;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;
import org.apache.commons.csv.CSVFormat;

/**
 *
 * @author phm
 */
public class Artificial2DGNGMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File path = new File ("/home/phm/Datasets/Arti2D/dataset_nonoise.csv");
        //File path = new File ("/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/topoart_data/dataset.csv");
        // Training dataset
        //DefaultDataset ds = new DefaultDataset();
        DefaultDataset dstrain = new DefaultDataset();
        CSVFileDatasetLoader ads = new CSVFileDatasetLoader(path, CSVFormat.EXCEL);
//        ArtificialDatasetLoader ads = new ArtificialDatasetLoader(path, CSVFormat.EXCEL);
        if (!ads.load(dstrain)) {
            System.out.println ("Dataset is failed to load");
        }

        ListedSignalGenerator sigTrain = new ListedSignalGenerator(dstrain, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator nsigTrain = new NeuronSignalGenerator(sigTrain);
        
        NeuronGroup ng = new NeuronGroup(new double [2]);
        //TopoEGARTTrainingSupervisor gart = new TopoEGARTTrainingSupervisor ();
        GNGTrainingSupervisor gart = new GNGTrainingSupervisor();
        ng.supervisors.add(gart);
        gart.initialize(ng);
        ng.setParameter(GNG_MAX_EDGE_AGE_KEY, 88);
        ng.setParameter(GNG_LANDA_KEY, 100);
 
        ng.analyzers.add ((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
            int numsig = (int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
            int numn = (int) ((NeuronGroup) n).neurons.size();
            int wid = 0;
            double dis = 0;
            if (result.getRecent().winners.size() > 0) {
                wid = result.getRecent().winners.get(0).getID();
                EuclideanDistance ed = new EuclideanDistance();
                dis = Math.abs (ed.calculateDistance (result.getRecent().winners.get(0), current));
            }
            System.out.println (numsig + "\t" + numn + "\t" + wid + "\t" + dis);
        });
        
        nsigTrain.signalAll(ng, gart.getName(), new NNResultContainer());
        //System.out.println ("Learning is finished ...");
        GraphFrame<Neuron, Connection> ngf = new GraphFrame ();
        ngf.addGraph (new GraphVisualComponent<>(ng));
        ngf.setVisible(true);
        
        DefaultDataset dd = new DefaultDataset();
        CSVFileDatasetSaver csv = new CSVFileDatasetSaver(new File("/home/phm/Dropbox/Projects/Topological Bayesian ARTMAP/Publication/results/soinn.csv"), CSVFormat.EXCEL);
        ng.neurons.asDataSet(dd);
        csv.save(dd);
//        DefaultDataset resds = new DefaultDataset();
//        ng.neurons.asDataSet(resds);
//        ClassBasedClusterer cbc = new ClassBasedClusterer();
//        Dataset [] dres = cbc.cluster(resds);
//        for (Dataset d : dres) {
//            System.out.println (d.get(0).classValue() + " = " + d.size());
//        }
//        GraphFrame<Neuron, Connection> frMain = new GraphFrame ();
//        for (Dataset d : dres) {
//            Graph<Neuron, Connection> gt = new SimpleGraph (new ConnectionFactory());
//            d.stream().forEach((x) -> {
//                gt.addVertex(new Neuron(x));
//            });
//            GraphVisualComponent<Neuron,Connection> gv = new GraphVisualComponent<>(gt);
//            gv.autoColoring = true;
//            frMain.addGraph(gv);
//        }
//        frMain.setVisible(true);
//
//        HashMap<String, Float> ac = new HashMap<>();
//        HashMap<String, Float> acCat = new HashMap<>();
//        
//        int index = 20000;
//        while (nsigTest.hasNext() && index-- > 0) {
//            NNResultContainer res = new NNResultContainer ();
//            nsigTest.signal (ng, gart.getName(), res);
//            if (res.getRecent().winners.size() > 0) {
//                String lbl = (String) res.getRecent().winners.get(0).classValue();
//                HashMap<Object, Double> clssmap = (HashMap<Object, Double>) res.getRecent().signal.classValue();
//                String lblsig = (String) getClassWithHighestProbability(clssmap);
//
//                acCat.put(lblsig, acCat.getOrDefault(lblsig, 0.0f) + 1);
//                if (lblsig != null && lbl != null && lbl.contentEquals(lblsig)) {
//                    ac.put(lblsig, ac.getOrDefault(lblsig, 0.0f) + 1);
//                }
//            }
//        }
//        
//        GraphFrame<Neuron, Connection> frMain2 = new GraphFrame ();
//        for (Dataset d : dres) {
//            Graph<Neuron, Connection> gt = new SimpleGraph (new ConnectionFactory());
//            d.stream().forEach((x) -> {
//                gt.addVertex(new Neuron(x));
//            });
//            GraphVisualComponent<Neuron,Connection> gv = new GraphVisualComponent<>(gt);
//            gv.autoColoring = true;
//            frMain2.addGraph(gv);
//        }
//        frMain2.setVisible(true);
////        
////        System.out.println ("******** RESULT ****************");
////        for (String key : ac.keySet()) {
////            ac.put(key, ac.get(key) / acCat.get(key));
////        }
////        System.out.println (ac);
////        
////        DefaultDataset ddsv = new DefaultDataset();
////        ng.neurons.asDataSet(ddsv);
////        FileHandler.exportDataset(ddsv, new File("/home/phm/TOPO.csv"));
//
////
//        File resfile = new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/topoart_data/result_gartmap_dataset.csv");
//        CSVFileDatasetSaver csvs = new CSVFileDatasetSaver (resfile, CSVFormat.EXCEL);
//        csvs.save(resds);
    }
    
}
