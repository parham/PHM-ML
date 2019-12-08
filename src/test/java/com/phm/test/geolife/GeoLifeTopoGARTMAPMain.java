
package com.phm.test.geolife;

import com.phm.ml.ParametersContainer;
import com.phm.ml.io.CSVFileDatasetSaver;
import com.phm.ml.nn.IneffectiveInputStrategy;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.art.ARTTrainingSupervisor;
import com.phm.ml.nn.algorithms.art.topoegaussian.TopoEGARTTrainingSupervisor;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;
import java.io.File;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import org.apache.commons.csv.CSVFormat;

/**
 *
 * @author phm
 */
public class GeoLifeTopoGARTMAPMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GeoLifeDatasetLoader gl = new GeoLifeDatasetLoader(
            new File (""));

        DefaultDataset ds = new DefaultDataset();
        if (gl.load(ds) && ds.size() <= 0) {
            System.out.println ("Error...");
        }

        System.out.println ("Dataset is loaded ...");
        ListedSignalGenerator sig = new ListedSignalGenerator(ds, ListedSignalGenerator.GeneratorPolicy.GP_SEQUENTIAL);
        System.out.println ("Dataset size : " + ds.size());

        NeuronGroup ng = new NeuronGroup(new double[3]);
        TopoEGARTTrainingSupervisor gng = new TopoEGARTTrainingSupervisor ();
        ng.supervisors.add(gng);
        gng.initialize(ng);
        ng.setParameter(ARTTrainingSupervisor.ART_VIGILANCE_PARAMTER, 0.94f);
        //ng.setParameter(NEURON_DEFAULT_STD, 0.1f);
        ng.setInputStrategy(new IneffectiveInputStrategy ());
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
        File pathres = new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/Geolife Trajectories 1.3/topogart.csv");
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
