
package com.phm.test.arti2d;

import com.phm.ml.ParametersContainer;
import com.phm.ml.gui.GraphFrame;
import com.phm.ml.gui.GraphVisualComponent;
import com.phm.ml.io.CSVFileDatasetSaver;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import static com.phm.ml.nn.algorithms.gng.GNGTrainingSupervisor.GNG_LANDA_KEY;
import static com.phm.ml.nn.algorithms.gng.GNGTrainingSupervisor.GNG_MAX_EDGE_AGE_KEY;
import com.phm.ml.nn.algorithms.soinn.SOINNSupervisor;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;
import java.awt.Color;
import java.io.File;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import org.apache.commons.csv.CSVFormat;

/**
 *
 * @author phm
 */
public class Artificial2DSOINNMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //File path = new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/artificial data/edata.png");
        //File path = new File ("/home/phm/Pictures/sample2.png");
        File path = new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/topoart_data/dataset.csv");
        //Artificial2DImageDataset ads = new Artificial2DImageDataset();
        DefaultDataset ds = new DefaultDataset();
        ArtificialDatasetLoader ads = new ArtificialDatasetLoader(path, CSVFormat.EXCEL);
        if (!ads.loadWithoutNoise(ds)) {
            System.out.println ("Dataset is failed to load");
        }
        ListedSignalGenerator sig = new ListedSignalGenerator(ds, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
        //System.out.println ("2D Image Dataset is loaded ...");
        System.out.println ("Dataset size : " + ds.size());
//        CVSFileSaver csvs = new CVSFileSaver(
//                            new File ("/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/artificial data/data.csv"), 
//                            CSVFormat.TDF);
//        if (csvs.save(ds)) {
//            System.out.println ("Dataset is saved ...");
//        } else {
//            System.out.println ("Dataset is failed ...");
//        }
        
        NeuronGroup ng = new NeuronGroup(new double[2]);
        SOINNSupervisor gng = new SOINNSupervisor();
        ng.supervisors.add(gng);
        gng.initialize(ng);
        ng.setParameter(GNG_MAX_EDGE_AGE_KEY, 200);
        ng.setParameter(GNG_LANDA_KEY, 200);
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
        File pathres = new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/topoart_data/result_soinn_l1_dataset.csv");
        CSVFileDatasetSaver saver = new CSVFileDatasetSaver(pathres, CSVFormat.EXCEL);
        if (!saver.save(resds)) {
            System.out.println ("Saving is failed ...");
        }
        System.out.println ("Visualization ...");
        GraphFrame<Neuron, Connection> ngf = new GraphFrame<>();
        GraphVisualComponent<Neuron, Connection> gg = new GraphVisualComponent<>(ng);
        gg.drawConnection = false;
        gg.nodeColor = Color.RED;
        gg.xSize = 15;
        gg.ySize = 15;
        ngf.addGraph(gg);
        ngf.setVisible(true);
    }
    
}
