
package com.phm.test.uci;

import com.phm.ml.ParametersContainer;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import static com.phm.ml.nn.algorithms.art.ARTMAPTrainingSupervisor.ARTMAP_EPSILON;
import static com.phm.ml.nn.algorithms.art.ARTTrainingSupervisor.ART_VIGILANCE_PARAMTER;
import com.phm.ml.nn.algorithms.art.egaussian.PhmGARTLRC;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTMAPTrainingSupervisor;
import com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor;
import static com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor.TGART_CF_MIN;
import static com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor.TGART_MAX_EDGE_AGE_KEY;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;
import com.phm.ml.siggen.SignalGenerator;
import static com.phm.test.arti2d.Artificial2DTopoGARTMAPMain2.getClassWithHighestProbability;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.tools.data.FileHandler;

/**
 *
 * @author phm
 */
public class UCITestTopoEGART2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.println ("Dataset is loading ...");
        List<UCILoader.UCIReaderRecord> list = 
                        UCILoader.loadDSRecords("/home/phm/Datasets/uci_har/train/Inertial Signals/",
                                                "/home/phm/Datasets/uci_har/train/y_train.txt");
        System.out.println ("Dataset is converting " + list.size() + " data records");
        Dataset db = UCILoader.loadAsDataset(list);
        System.out.println ("Number of data fields ---> " + db.size());
        
        int trainSize = (int) (db.size() * 0.01f);
        int testSize = (int) (db.size() * 0.005f);
        
        System.out.println ("Number of data fields for training ---> " + trainSize);
        System.out.println ("Number of data fields for testing---> " + testSize);
        
        NeuronGroup group = new NeuronGroup (new double [3]);
        TopoEGARTTrainingSupervisor tegart = new TopoEGARTTrainingSupervisor (new PhmGARTLRC(0.5f, 0.00005f, 0.000001f)); //  new DefaultLRC()
        tegart.initialize(group);
        group.supervisors.add(tegart);
        //ng.setParameter(TGART_CORRESPONDENT_THRESH, 0.00001f);
        group.setParameter(TGART_CF_MIN, 0.00001f);
        group.setParameter(ART_VIGILANCE_PARAMTER, 0.9f);
        group.setParameter(GaussianARTMAPTrainingSupervisor.NEURON_DEFAULT_STD, 0.6f);
        group.setParameter(ARTMAP_EPSILON, -0.02f);
        group.setParameter(TGART_MAX_EDGE_AGE_KEY, 10000);
//        group.analyzers.add((NeuronAnalyzer) (String state, Neuron n,
//                        ParametersContainer param, Instance current,
//                        NNResultContainer result) -> {
//            int numsig = (int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
//            int numn = (int) ((NeuronGroup) n).neurons.size();
//            System.out.print (numsig + " --> " + numn);
//            if (result.getRecent().winners.size() > 0) {
//                System.out.print (" : " + result.getRecent().winners.get(0).classValue() + " = " + 
//                                          getClassWithHighestProbability((HashMap<Object, Double>) result.getRecent().signal.classValue()) + " :::: " + current.classValue());
//            }
//            System.out.println ();
//        });
        group.analyzers.add ((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
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
        
        SignalGenerator sig = new ListedSignalGenerator(db, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator sg = new NeuronSignalGenerator(sig);
        
        for (int index = 0; index < trainSize; index++) {
            NNResultContainer result = new NNResultContainer ();
            sg.signal(group, tegart.getName(), result);
        }
        System.out.println("training procedure is finished ...");
        ///////////////
        System.out.println("Test procedure...");
        SignalGenerator sigtest = new ListedSignalGenerator(db, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator sgtest = new NeuronSignalGenerator(sigtest);
        HashMap<String, Float> ac = new HashMap<>();
        HashMap<String, Float> acCat = new HashMap<>();
        while (sgtest.hasNext() && sgtest.countGeneratedSignals() < testSize) { //  
            NNResultContainer restest = new NNResultContainer ();
            Instance inst = sgtest.next();
            inst.setClassValue(null);
            group.feed(tegart.getName(), inst, restest);
            if (restest.getRecent().winners.size() > 0) {
                String lbl = (String) restest.getRecent().winners.get(0).classValue();
                HashMap<Object, Double> clssmap = (HashMap<Object, Double>) restest.getRecent().signal.classValue();
                String lblsig = (String) getClassWithHighestProbability(clssmap);
                acCat.put(lblsig, acCat.getOrDefault(lblsig, 0.0f) + 1);
                if (lblsig != null && lbl != null && lbl.contentEquals(lblsig)) {
                    ac.put(lblsig, ac.getOrDefault(lblsig, 0.0f) + 1);
                }
            }
        }
        
        System.out.println ("******** RESULT ****************");
        for (String key : ac.keySet()) {
            ac.put(key, ac.get(key) / acCat.get(key));
        }
        System.out.println (ac);
        System.out.println (acCat);
//        
//        DefaultDataset ddsv = new DefaultDataset();
//        group.neurons.asDataSet(ddsv);
//        FileHandler.exportDataset(ddsv, new File("/home/phm/HAR_CART.csv"));
    }
}