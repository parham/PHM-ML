
package com.phm.test.gps;

import com.phm.ml.ParametersContainer;
import com.phm.ml.io.CSVFileDatasetLoader;
import com.phm.ml.io.CSVFileDatasetSaver;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import static com.phm.ml.nn.algorithms.art.ARTTrainingSupervisor.ART_VIGILANCE_PARAMTER;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTTrainingSupervisor;
import static com.phm.ml.nn.algorithms.art.gaussian.GaussianARTTrainingSupervisor.NEURON_DEFAULT_STD;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.connection.ConnectionFactory;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;
import com.phm.ml.triangulation.HebbianCompetitiveTriangulator;
import java.io.File;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;
import org.apache.commons.csv.CSVFormat;

/**
 *
 * @author phm
 */
public class GPSGARTTimeMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CSVFileDatasetLoader csvl = new CSVFileDatasetLoader(
            new File("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/gps/gps2.csv"), CSVFormat.EXCEL);
        
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

        int numch = ddset.get(0).noAttributes();
        NeuronGroup ng = new NeuronGroup(new double [numch]);
        GaussianARTTrainingSupervisor gart = new GaussianARTTrainingSupervisor ();
        ng.supervisors.add(gart);
        gart.initialize(ng);
        ng.setParameter(ART_VIGILANCE_PARAMTER, 0.01f);
        ng.setParameter(NEURON_DEFAULT_STD, 0.9f);
        ng.analyzers.add ((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
            int numsig = (int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
            int numn = (int) ((NeuronGroup) n).neurons.size();
            System.out.println (numsig + " --> " + numn);
        });
        NeuronSignalGenerator nsig = new NeuronSignalGenerator(sig);
        nsig.signalAll(ng, gart.getName(), new NNResultContainer());
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
