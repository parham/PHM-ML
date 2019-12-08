    
package com.phm.test.mitssl;

import com.phm.ml.ParametersContainer;
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
import static com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor.TGART_DELTA;
import static com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor.TGART_MAX_EDGE_AGE_KEY;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;
import com.phm.ml.siggen.SignalGenerator;
import static com.phm.test.arti2d.Artificial2DTopoGARTMAPMain.getClassWithHighestProbability;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;

/**
 *
 * @author phm
 */
public class MITSSLTopoEGARTTestMain {
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
     */
    public static void main(String[] args) {
        DefaultDataset db = MITSSLDataset.loadMITSSLDataset(new File("/home/phm/Datasets/ChapelleSS/benchmark/SSL,set=2,X.tab"), 
                                                            new File("/home/phm/Datasets/ChapelleSS/benchmark/SSL,set=2,y.tab"), 4);
        System.out.println ("Successful...");
        System.out.println ("Number of data fields -    --> " + db.size());
        
        DefaultDataset dstest = new DefaultDataset();
        int testnum = (int) (0.2 * db.size());
        Random rand2 = new Random(System.currentTimeMillis());
        for (int index = 0; index < testnum; index++) {
            int ind = rand2.nextInt(db.size());
            Instance testmp = db.remove(ind);
            testmp.setClassValue (null);
            dstest.add(testmp);
        }
        
        NeuronGroup group = new NeuronGroup(new double [db.get(0).noAttributes()]);
        TopoEGARTTrainingSupervisor tegart = new TopoEGARTTrainingSupervisor (new PhmGARTLRC(0.5f, 0.00005f, 0.000001f)); // new DefaultLRC() 
//        GaussianARTMAPTrainingSupervisor tegart = new GaussianARTMAPTrainingSupervisor (); 
        tegart.initialize(group);
        group.supervisors.add(tegart);
        group.setParameter(ART_VIGILANCE_PARAMTER, 0.8f);
        group.setParameter(GaussianARTMAPTrainingSupervisor.NEURON_DEFAULT_STD, 0.5f);
        group.setParameter(ARTMAP_EPSILON, -0.02f);
        group.setParameter(TGART_DELTA, 150);
        group.setParameter(TGART_MAX_EDGE_AGE_KEY, 88);
        group.analyzers.add((NeuronAnalyzer) (String state, Neuron n,
                        ParametersContainer param, Instance current,
                        NNResultContainer result) -> {
            int numsig = (int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
            int numn = (int) ((NeuronGroup) n).neurons.size();
            System.out.print (numsig + " --> " + numn);
            if (result.getRecent().winners.size() > 0) {
                System.out.print (" : " + result.getRecent().winners.get(0).classValue() + " = " + 
                                          getClassWithHighestProbability((HashMap<Object, Double>) result.getRecent().signal.classValue()) + " :::: " + current.classValue());
            }
            System.out.println ();
        });
//        group.analyzers.add ((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
//            int numsig = (int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
//            int numn = (int) ((NeuronGroup) n).neurons.size();
//            int wid = 0;
//            double dis = 0;
//            if (result.getRecent().winners.size() > 0) {
//                wid = result.getRecent().winners.get(0).getID();
//                EuclideanDistance ed = new EuclideanDistance();
//                dis = Math.abs (ed.calculateDistance (result.getRecent().winners.get(0), current));
//            }
//            System.out.println (numsig + "\t" + numn + "\t" + wid + "\t" + dis);
//        });
        
        SignalGenerator sig = new ListedSignalGenerator(db, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator sg = new NeuronSignalGenerator(sig);
        
        long time = System.currentTimeMillis();
        
        int numsig = db.size();
        for (int index = 0; index < numsig; index++) {
            NNResultContainer result = new NNResultContainer ();
            sg.signal(group, tegart.getName(), result);
        }
        System.out.println("training procedure is finished ...");
        
        ListedSignalGenerator sigTest = new ListedSignalGenerator(dstest, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator nsigTest = new NeuronSignalGenerator(sigTest);
        
        HashMap<String, Float> ac = new HashMap<>();
        HashMap<String, Float> acCat = new HashMap<>();
        
        while (nsigTest.hasNext()) {
            NNResultContainer res = new NNResultContainer ();
            nsigTest.signal (group, tegart.getName(), res);
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
        
        for (String key : ac.keySet()) {
            ac.put(key, ac.get(key) / acCat.get(key));
        }
        System.out.println (ac);
        
        System.out.println ("Time = " + (System.currentTimeMillis() - time));
        
    }
    
}
