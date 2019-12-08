
package com.phm.test;


import com.phm.ml.ParametersContainer;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.algorithms.art.topoart.TopoARTTrainingSupervisor;
import com.phm.ml.siggen.ThresholdImage2DSignalGenerator;
import com.phm.ml.nn.extra.io.NeuronGroupOutputStream;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.gui.NeuronGroupPresenterFrame;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class TestTopoART {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        LinkedList<float []> list = new LinkedList<>();
        list.add(new float [4]);
        NeuronGroup group = new NeuronGroup (new double [4]);
        TopoARTTrainingSupervisor tmp = new TopoARTTrainingSupervisor ();
        group.supervisors.add(tmp);
        tmp.initialize(group);
        
        group.analyzers.add((NeuronAnalyzer) (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) -> {
            System.out.println ((int) n.getParameter(Neuron.RECIEVED_SIGNALS_NUM) + " --> " +
                                ((NeuronGroup) n).neurons.size());
        });
        
        BufferedImage img = ImageIO.read(new File("/home/phm/Pictures/coins.png"));
//        BufferedImage img = ImageIO.read(new File("G:\\PHM - I AM ONE\\Projects\\Review on Neural Network for using on 3D downsampling\\Dataset\\2D\\coins.png"));
        ThresholdImage2DSignalGenerator sig = new ThresholdImage2DSignalGenerator(img, new Color(100, 100, 100), ThresholdImage2DSignalGenerator.GeneratorPolicy.GP_RANDOM);
        NeuronSignalGenerator sg = new NeuronSignalGenerator (sig);
        
        NNResultContainer result = new NNResultContainer ();
        sg.signalAll(group, tmp.getName(), result);
        System.out.println("It is finished ...");
        int snum = (int) group.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
        System.out.println("Signals --> " + snum);
        new Thread(() -> {
            NeuronGroupPresenterFrame frMain = new NeuronGroupPresenterFrame(group);
            frMain.setVisible(true);
        }).start();
        System.out.println("Signals --> " + snum);
        try (NeuronGroupOutputStream out = new NeuronGroupOutputStream(
                new FileOutputStream("/home/phm/egart.txt"))) {
            out.write(group);
            out.flush();
        }
    }
    
}
