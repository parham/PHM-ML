package com.phm.test;




import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.siggen.ThresholdImage2DSignalGenerator;
import com.phm.ml.nn.extra.io.NeuronGroupOutputStream;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import static com.phm.ml.nn.algorithms.art.ARTTrainingSupervisor.ART_VIGILANCE_PARAMTER;
import com.phm.ml.nn.algorithms.art.fuzzy.FuzzyARTInputStrategy;
import com.phm.ml.nn.algorithms.art.fuzzy.FuzzyARTTrainingSupervisor;
import com.phm.ml.nn.gui.NeuronGraphFrame;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author phm
 */
public class TestFuzzyART {
    public static void main(String[] args) throws IOException {
//        FuzzyARTNeuralNetwork fnn = new FuzzyARTNeuralNetwork ();
//        fnn.addAnalyzer((NeuronAnalyzer<DataField<float []>>) 
//                            (String state, Neuron<DataField<float []>> n, 
//                            ParametersContainer param, DataField<float []> current, 
//                            NNResultContainer result) -> {
//            System.out.println ((int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM));
//        });
//        fnn.setDimension(2);
//        fnn.initialize();
        
        NeuronGroup ng = new NeuronGroup(new double [4]);
        ng.setInputStrategy(new FuzzyARTInputStrategy());
        FuzzyARTTrainingSupervisor fart = new FuzzyARTTrainingSupervisor(4);
        ng.supervisors.add(fart);
        fart.initialize(ng);
        ng.setParameter(ART_VIGILANCE_PARAMTER, 0.96f);
        
        BufferedImage img = ImageIO.read(new File("/home/phm/Pictures/coins.png"));
        ThresholdImage2DSignalGenerator sig = new ThresholdImage2DSignalGenerator(img, new Color(100, 100, 100), ThresholdImage2DSignalGenerator.GeneratorPolicy.GP_RANDOM);        
        NNResultContainer result = new NNResultContainer ();
        NeuronSignalGenerator nsig = new NeuronSignalGenerator(sig);
        nsig.signalAll(ng, fart.getName(), result);
        System.out.println("It is finished ...");
        int snum = (int) ng.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
        System.out.println("Signals --> " + snum);
        
        NeuronGraphFrame frMain = new NeuronGraphFrame(ng);
        frMain.setVisible(true);
        
        try {
            try (NeuronGroupOutputStream out = new NeuronGroupOutputStream(
                    new FileOutputStream("/home/phm/fart.txt"))) {
                out.write(ng);
                out.flush();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestFuzzyART.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestFuzzyART.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
