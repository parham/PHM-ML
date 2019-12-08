
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
import com.phm.ml.nn.algorithms.art.egaussian.DefaultLRC;
import com.phm.ml.nn.algorithms.art.egaussian.PhmGARTLRC;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTMAPTrainingSupervisor;
import com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor;
import static com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor.TGART_CF_MIN;
import static com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor.TGART_CORRESPONDENT_THRESH;
import static com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor.TGART_MAX_EDGE_AGE_KEY;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.connection.ConnectionFactory;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
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
public class Artificial2DTopoGARTMAPMain2 {

    public static Object getClassWithHighestProbability (HashMap<Object, Double> clss) {
        if (clss != null) {
            LinkedList<Object> keys = new LinkedList<>(clss.keySet());
            Object maxl = keys.get(0);
            double maxv = clss.getOrDefault(maxl, 0.0);
            for (Object k : keys) {
                double tmp = clss.getOrDefault(k, 0.0);
                if (tmp > maxv) {
                    maxl = k;
                    maxv = tmp;
                }
            }
            return maxl;
        }
        return "";
    }
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        File path = new File ("/home/phm/Datasets/Arti2D/dataset.csv");
//        File path = new File ("/home/phm/Datasets/Arti2D/dataset_nonoise.csv");
        //File path = new File ("/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/topoart_data/dataset.csv");
        // Training dataset
        DefaultDataset ds = new DefaultDataset();
        //DefaultDataset dstrain = new DefaultDataset();
        ArtificialDatasetLoader ads = new ArtificialDatasetLoader(path, CSVFormat.EXCEL);
        if (!ads.load(ds)) {
            System.out.println ("Dataset is failed to load");
        }
        DefaultDataset dstrain = new DefaultDataset();
        System.out.println ("Filtering the training data ...");
        for (int index = 0; index < ds.size(); index++) {
            String lbl = (String) ds.get(index).classValue();
            if (!lbl.contentEquals("NOISE")) {
                dstrain.add(ds.get(index));
            }
        }
        
        
        // Training dataset
//        DefaultDataset dstrain = new DefaultDataset();
//        Random rand = new Random(System.currentTimeMillis());
//        int trainnum = 2000;
//        for (int index = 0; index < trainnum; index++) {
//            int ind = rand.nextInt(ds.size());
//            Instance tmp = ds.remove(ind);
//            dstrain.add(tmp);
//        }
        // Testing dataset
        DefaultDataset dstest = new DefaultDataset();
        int testnum = (int) (0.8 * dstrain.size());
        Random rand2 = new Random(System.currentTimeMillis());
        for (int index = 0; index < testnum; index++) {
            int ind = rand2.nextInt(dstrain.size());
            Instance testmp = dstrain.remove(ind);
            testmp.setClassValue (null);
            dstest.add( testmp);
        }
        
        ListedSignalGenerator sigTrain = new ListedSignalGenerator(dstrain, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        ListedSignalGenerator sigTest = new ListedSignalGenerator(dstest, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        
        NeuronSignalGenerator nsigTrain = new NeuronSignalGenerator(sigTrain);
        NeuronSignalGenerator nsigTest = new NeuronSignalGenerator(sigTest);
        
        NeuronGroup ng = new NeuronGroup(new double[2]);
        //TopoEGARTTrainingSupervisor gart = new TopoEGARTTrainingSupervisor ();
        TopoEGARTTrainingSupervisor gart = new TopoEGARTTrainingSupervisor (new PhmGARTLRC(0.5f, 0.00005f, 0.000001f)); //  new DefaultLRC()
        ng.supervisors.add(gart);
        gart.initialize(ng);
        //ng.setParameter(TGART_CORRESPONDENT_THRESH, 0.00001f);
        ng.setParameter(TGART_CF_MIN, 0.00001f);
        ng.setParameter(ART_VIGILANCE_PARAMTER, 0.9f);
        ng.setParameter(GaussianARTMAPTrainingSupervisor.NEURON_DEFAULT_STD, 0.6f);
        ng.setParameter(ARTMAP_EPSILON, -0.02f);
        ng.setParameter(TGART_MAX_EDGE_AGE_KEY, 10000);
//        ng.analyzers.add ((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
//            int numsig = (int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
//            int numn = (int) ((NeuronGroup) n).neurons.size();
//            System.out.print (numsig + " --> " + numn);
//            if (result.getRecent().winners.size() > 0) {
//                System.out.print (" : " + result.getRecent().winners.get(0).classValue() + " = " + 
//                                          getClassWithHighestProbability((HashMap<Object, Double>) result.getRecent().signal.classValue()) + " :::: " + current.classValue());
//            }
//            System.out.println ();
//        });   
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
        
        DefaultDataset resds = new DefaultDataset();
        ng.neurons.asDataSet(resds);
        ClassBasedClusterer cbc = new ClassBasedClusterer();
        Dataset [] dres = cbc.cluster(resds);
//        for (Dataset d : dres) {
//            System.out.println (d.get(0).classValue() + " = " + d.size());
//        }
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

        HashMap<String, Float> ac = new HashMap<>();
        HashMap<String, Float> acCat = new HashMap<>();
        
        int index = 20000;
        while (nsigTest.hasNext() && index-- > 0) {
            NNResultContainer res = new NNResultContainer ();
            nsigTest.signal (ng, gart.getName(), res);
            if (res.getRecent().winners.size() > 0) {
                String lbl = (String) res.getRecent().winners.get(0).classValue();
                HashMap<Object, Double> clssmap = (HashMap<Object, Double>) res.getRecent().signal.classValue();
                String lblsig = (String) getClassWithHighestProbability(clssmap);

                acCat.put(lblsig, acCat.getOrDefault(lblsig, 0.0f) + 1);
                if (lblsig != null && lbl != null && lbl.contentEquals(lblsig)) {
                    ac.put(lblsig, ac.getOrDefault(lblsig, 0.0f) + 1);
                }
            }
        }
        
        GraphFrame<Neuron, Connection> frMain2 = new GraphFrame ();
        for (Dataset d : dres) {
            Graph<Neuron, Connection> gt = new SimpleGraph (new ConnectionFactory());
            d.stream().forEach((x) -> {
                gt.addVertex(new Neuron(x));
            });
            GraphVisualComponent<Neuron,Connection> gv = new GraphVisualComponent<>(gt);
            gv.autoColoring = true;
            frMain2.addGraph(gv);
        }
        frMain2.setVisible(true);
        
        File resfile = new File ("/home/phm/result_gartmap_dataset.csv");
        CSVFileDatasetSaver csvs = new CSVFileDatasetSaver (resfile, CSVFormat.EXCEL);
        csvs.save(resds);
//        
//        System.out.println ("******** RESULT ****************");
//        for (String key : ac.keySet()) {
//            ac.put(key, ac.get(key) / acCat.get(key));
//        }
//        System.out.println (ac);
//        
//        DefaultDataset ddsv = new DefaultDataset();
//        ng.neurons.asDataSet(ddsv);
//        FileHandler.exportDataset(ddsv, new File("/home/phm/TOPO.csv"));

//
//        File resfile = new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/topoart_data/result_gartmap_dataset.csv");
//        CSVFileDatasetSaver csvs = new CSVFileDatasetSaver (resfile, CSVFormat.EXCEL);
//        csvs.save(resds);
    }
    
}
