
package com.phm.test.gps;

import java.io.File;

import org.apache.commons.csv.CSVFormat;

import com.phm.ml.ParametersContainer;
import com.phm.ml.io.CSVFileDatasetLoader;
import com.phm.ml.io.CSVFileDatasetSaver;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTTrainingSupervisor;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;

import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class GPSGARTMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CSVFileDatasetLoader csvl = new CSVFileDatasetLoader(
            new File("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/gps/gps1.csv"), CSVFormat.EXCEL);
        
        DefaultDataset ds = new DefaultDataset();
        if (!csvl.load(ds)) {
            System.out.println ("Error is failed ...");
        }
        System.out.println ("Dataset size : " + ds.size());
        
        DefaultDataset ddset = new DefaultDataset(ds);
        for (int index = 0; index < 3; index++) {
            ddset.addAll(ds);
        }
//        File dd = new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/Geolife Trajectories 1.3/data.csv");        
//        CSVFileDatasetSaver saverdd = new CSVFileDatasetSaver(dd, CSVFormat.EXCEL);
//                if (!saverdd.save(ddset)) {
//            System.out.println ("Saving is failed ...");
//        }
        
        System.out.println ("Dataset is loaded ...");
        ListedSignalGenerator sig = new ListedSignalGenerator(ddset, ListedSignalGenerator.GeneratorPolicy.GP_SEQUENTIAL);
        System.out.println ("Dataset size : " + ddset.size());

        NeuronGroup ng = new NeuronGroup(new double[3]);
        GaussianARTTrainingSupervisor gng = new GaussianARTTrainingSupervisor ();
        ng.supervisors.add(gng);
        gng.initialize(ng);
//        ng.parameters.put(GNG_MAX_EDGE_AGE_KEY, 30);
//        ng.parameters.put(GNG_LANDA_KEY, 50);
        ng.analyzers.add ((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
            int numsig = (int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
            int numn = (int) ((NeuronGroup) n).neurons.size();
            System.out.println (numsig + " --> " + numn);
        });
        NeuronSignalGenerator nsig = new NeuronSignalGenerator(sig);
        nsig.signalAll(ng, gng.getName(), new NNResultContainer());
        System.out.println ("Learning is finished ...");
        DefaultDataset resds = new DefaultDataset();
        ng.neurons.asDataSet(resds);
        File pathres = new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/gps/gart.csv");
        CSVFileDatasetSaver saver = new CSVFileDatasetSaver(pathres, CSVFormat.EXCEL);
        if (!saver.save(resds)) {
            System.out.println ("Saving is failed ...");
        }
//        System.out.println ("Visualization ...");
//        GraphFrame<Neuron, Connection> ngf = new GraphFrame ();
//        ngf.addGraph(new GraphVisualComponent<>(ng));
//        ngf.setVisible(true);
    }
    
}
