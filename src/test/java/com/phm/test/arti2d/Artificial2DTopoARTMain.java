
package com.phm.test.arti2d;

import com.phm.ml.ParametersContainer;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import static com.phm.ml.nn.algorithms.art.ARTTrainingSupervisor.ART_VIGILANCE_PARAMTER;
import static com.phm.ml.nn.algorithms.art.gaussian.GaussianARTTrainingSupervisor.NEURON_DEFAULT_STD;
import com.phm.ml.nn.algorithms.art.topoart.TopoARTTrainingSupervisor;
import com.phm.ml.nn.gui.NeuronGraphFrame;
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
public class Artificial2DTopoARTMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File path = new File ("/home/phm/Datasets/Arti2D/dataset.csv");
        DefaultDataset dstrain = new DefaultDataset();
        ArtificialDatasetLoader ads = new ArtificialDatasetLoader(path, CSVFormat.EXCEL);
        if (!ads.load(dstrain)) {
            System.out.println ("Dataset is failed to load");
        }
        ListedSignalGenerator sig = new ListedSignalGenerator(dstrain, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        System.out.println ("2D Image Dataset is loaded ...");
        System.out.println ("Dataset size : " + dstrain.size());
//        CVSFileSaver csvs = new CVSFileSaver(
//                            new File ("/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/artificial data/data.csv"), 
//                            CSVFormat.TDF);
//        if (csvs.save(ds)) {
//            System.out.println ("Dataset is saved ...");
//        } else {
//            System.out.println ("Dataset is failed ...");
//        }
        
        NeuronGroup ng = new NeuronGroup(new double[2]);
        TopoARTTrainingSupervisor topoart = new TopoARTTrainingSupervisor ();
        ng.supervisors.add(topoart);
        topoart.initialize(ng);
        ng.setParameter(ART_VIGILANCE_PARAMTER, 0.96f);
        ng.setParameter(NEURON_DEFAULT_STD, 0.9f);
//        ng.analyzers.add ((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
//            int numsig = (int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
//            int numn = (int) ((NeuronGroup) n).neurons.size();
//            System.out.println (numsig + " --> " + numn);
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
        NeuronSignalGenerator nsig = new NeuronSignalGenerator(sig);
        nsig.signalAll(ng, topoart.getName(), new NNResultContainer());
        System.out.println ("Learning is finished ...");
        NeuronGraphFrame ngf = new NeuronGraphFrame(ng);
        ngf.setVisible(true);
    }
    
}
