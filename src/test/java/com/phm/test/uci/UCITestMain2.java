

package com.phm.test.uci;

import com.phm.ml.ParametersContainer;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import static com.phm.ml.nn.algorithms.art.ARTMAPTrainingSupervisor.ARTMAP_EPSILON;
import static com.phm.ml.nn.algorithms.art.ARTTrainingSupervisor.ART_VIGILANCE_PARAMTER;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTMAPTrainingSupervisor;
import com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor;
import static com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor.TGART_MAX_EDGE_AGE_KEY;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;
import com.phm.ml.siggen.SignalGenerator;
import static com.phm.test.arti2d.Artificial2DTopoGARTMAPMain2.getClassWithHighestProbability;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

/**
 *
 * @author phm
 */
public class UCITestMain2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        DefaultDataset db = new DefaultDataset();
        if (UCILoader.loadHAR(new File("/run/media/phm/PHM/PHM - I AM ONE/Projects/2015 - OSSMGNG/Dataset/UCI HAR/UCI HAR Dataset/train/data_train.txt"),
                              new File("/run/media/phm/PHM/PHM - I AM ONE/Projects/2015 - OSSMGNG/Dataset/UCI HAR/UCI HAR Dataset/train/y_train.txt"), db)) {
            
            System.out.println ("Test Dataset is being created ...");
            DefaultDataset dbtest = new DefaultDataset();
            Random rand = new Random(System.currentTimeMillis());
            int numtest = (int) (db.size() * 0.3f);
            for (int index = 0; index < numtest; index++) {
                int id = rand.nextInt(db.size());
                Instance df = db.remove(id);
                df.setClassValue(null);
                dbtest.add(df);
            }
            
            System.out.println ("Number of data fields for testing ---> " + dbtest.size());
            System.out.println ("Dataset is created ...");
            System.out.println ("Learning ...");
            
            NeuronGroup group = new NeuronGroup(new double [5]);
            TopoEGARTTrainingSupervisor tegart = new TopoEGARTTrainingSupervisor ();
//            GaussianARTMAPTrainingSupervisor tegart = new GaussianARTMAPTrainingSupervisor();
            
            tegart.initialize(group);
            group.supervisors.add(tegart);
            group.setParameter(ART_VIGILANCE_PARAMTER, 0.9f);
            group.setParameter(GaussianARTMAPTrainingSupervisor.NEURON_DEFAULT_STD, 0.6f);
            group.setParameter(ARTMAP_EPSILON, -0.02f);
            group.setParameter(TGART_MAX_EDGE_AGE_KEY, 100);
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
            
            SignalGenerator sig = new ListedSignalGenerator(db, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
            NeuronSignalGenerator sg = new NeuronSignalGenerator(sig);
            NNResultContainer result = new NNResultContainer ();
            sg.signalAll(group, tegart.getName(), result);
            System.out.println("training procedure is finished ...");
            
            System.out.println("Test procedure...");
            SignalGenerator sigtest = new ListedSignalGenerator(dbtest, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
            NeuronSignalGenerator sgtest = new NeuronSignalGenerator(sigtest);

            HashMap<String, Float> ac = new HashMap<>();
            HashMap<String, Float> acCat = new HashMap<>();
            while (sgtest.hasNext() && sgtest.countGeneratedSignals() < 10000) { //  
                NNResultContainer restest = new NNResultContainer ();
                sgtest.signal(group, tegart.getName(), restest);

                String lbl = (String) restest.getRecent().winners.get(0).classValue();
                HashMap<Object, Double> clssmap = (HashMap<Object, Double>) restest.getRecent().signal.classValue();
                String lblsig = (String) getClassWithHighestProbability(clssmap);
                acCat.put(lblsig, acCat.getOrDefault(lblsig, 0.0f) + 1);
                if (lblsig != null && lbl != null && lbl.contentEquals(lblsig)) {
                    ac.put(lblsig, ac.getOrDefault(lblsig, 0.0f) + 1);
                }

            }
            
            System.out.println ("******** RESULT ****************");
            for (String key : ac.keySet()) {
                ac.put(key, ac.get(key) / acCat.get(key));
            }
            System.out.println (ac);
            System.out.println (acCat);

            DefaultDataset ddsv = new DefaultDataset();
            group.neurons.asDataSet(ddsv);
            FileHandler.exportDataset(ddsv, new File("/home/phm/HAR_CART.csv"));
            
        } else {
            System.out.println ("Failed");
        }
    }
    
}
