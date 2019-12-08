
package com.phm.test.mitssl;

import com.phm.ml.ParametersContainer;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import static com.phm.ml.nn.algorithms.art.ARTMAPTrainingSupervisor.ARTMAP_EPSILON;
import static com.phm.ml.nn.algorithms.art.ARTTrainingSupervisor.ART_VIGILANCE_PARAMTER;
import com.phm.ml.nn.algorithms.art.bayesian.BayesianARTTrainingSupervisor;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTMAPTrainingSupervisor;
import static com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor.TGART_DELTA;
import static com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor.TGART_MAX_EDGE_AGE_KEY;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;
import com.phm.ml.siggen.SignalGenerator;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;

/**
 *
 * @author phm
 */
public class MITSSLBayesianARTMAPTest {
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
        System.out.println ("Number of data fields ---> " + db.size());
        
        NeuronGroup group = new NeuronGroup(new double [db.get(0).noAttributes()]);
//        TopoEGARTTrainingSupervisor tegart = new TopoEGARTTrainingSupervisor ();
        BayesianARTTrainingSupervisor tegart = new BayesianARTTrainingSupervisor (6); 
        tegart.initialize(group);
        group.supervisors.add(tegart);
        group.setParameter(ART_VIGILANCE_PARAMTER, 0.4f);
        group.setParameter(GaussianARTMAPTrainingSupervisor.NEURON_DEFAULT_STD, 0.2f);
        group.setParameter(ARTMAP_EPSILON, -0.02f);
        group.setParameter(TGART_DELTA, 150);
        group.setParameter(TGART_MAX_EDGE_AGE_KEY, 88);
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
        
        int numsig = db.size();
        for (int index = 0; index < numsig; index++) {
            NNResultContainer result = new NNResultContainer ();
            sg.signal(group, tegart.getName(), result);
        }
        System.out.println("training procedure is finished ...");
    }
}
