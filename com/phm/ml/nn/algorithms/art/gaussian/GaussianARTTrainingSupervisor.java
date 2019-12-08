
package com.phm.ml.nn.algorithms.art.gaussian;

import com.phm.ml.nn.IneffectiveInputStrategy;
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.art.ARTTrainingSupervisor;
import com.phm.ml.nn.event.NeuronAddedEvent;
import java.util.List;
import net.sf.javaml.core.Instance;

/**
 * <p>
 * <b>Publication details:<br></b>
 * <b>Authors:</b> James R. Williamson <br>
 * <b>Year:</b> 1998 <br>
 * <b>Title:</b> Gaussian ARTMAP: A Neural Network for Fast Incremental Learning of Noisy Multidimensional Maps <br>
 * <b>Published In:</b> Neural Networks 9(5) <br>
 * <b>Page:</b> 881–897 <br>
 * <b>DOI:</b> 10.1016/0893-6080(95)00115-8 <br>
 * </p>
 * @author Parham Nooralishahi - PHM!
 * @email parham.nooralishahi@gmail.com
 */
public class GaussianARTTrainingSupervisor extends ARTTrainingSupervisor {

    public static final String NEURON_PROBABILITY = "neuron.probability";
    public static final String NEURON_CODEDSIGNAL = "neuron.coded.signals";
    public static final String NEURON_DEFAULT_STD = "neuron.def.std";
    public static final String NEURON_STD = "neuron.std";
    
    @Override
    public String getName() {
        return "gaussianart.train";
    }

    @Override
    public void initialize(Neuron ngroup) {
        ngroup.setParameter(ART_VIGILANCE_PARAMTER, 0.96f);
        ngroup.setParameter(ART_RESONANCE_OCCURED, false);
        ngroup.setParameter(NEURON_DEFAULT_STD, 0.9f);
        /////////////////////
        ngroup.setInputStrategy(new IneffectiveInputStrategy ());
    }

    protected Neuron initializeGaussianARTNeuron (NeuronGroup ngroup, 
                                                  Neuron n) {
        n.setParameter(NEURON_ACTIVATION_VALUE, 0.0f);
        n.setParameter(NEURON_MATCHVALUE, 0.0f);
        n.setParameter(NEURON_PROBABILITY, 0.0f);
        n.setParameter(NEURON_CODEDSIGNAL, 1.0f);
        n.setParameter(NEURON_STD, ngroup.getParameter(NEURON_DEFAULT_STD));
        
        return n;
    }
    
    @Override
    protected Neuron insert (NeuronGroup ngroup, 
                             Instance signal) {
        // Initialize new neuron
        double [] dimstd = new double [signal.noAttributes()];
        float defstd = (float) ngroup.getParameter(NEURON_DEFAULT_STD);
        for (int index = 0; index < dimstd.length; index++) {
            dimstd [index] = defstd;
        }
        /////////////////////////
        Neuron newNeuron = initializeGaussianARTNeuron (ngroup, new Neuron(signal));
        newNeuron.setParameter(NEURON_STD, dimstd);
        ngroup.addInternalNeuron(newNeuron);
        ngroup.listeners.event(new NeuronAddedEvent(newNeuron));
        return newNeuron;
    }

    @Override
    protected NNResult prepareResult(Neuron neuron, Instance signal, List<Neuron> winners, NNResult resc) {
        return new NNResult(System.currentTimeMillis(), neuron.getParametersContainer(), winners);
    }

    @Override
    protected double activate(NeuronGroup ngroup, Neuron neuron, Instance signal) {
        float p = (float) ngroup.getParameter (ART_VIGILANCE_PARAMTER);
        float nj = (float) neuron.getParameter (NEURON_CODEDSIGNAL);
        double [] stddim = (double[]) neuron.getParameter(NEURON_STD);
        float Gj = 0.0f;
        double mulstd = 1.0;

        for (int dim = 0; dim < stddim.length; dim++) {
            double tmp = 0.0f;
            mulstd *= stddim [dim];
            tmp = (signal.value (dim) - neuron.value(dim)) / stddim [dim];
            Gj += tmp * tmp;
        }
        Gj = (float) Math.exp(-0.5f * Gj);
        mulstd = nj / mulstd;
        float gj = (float) (Gj > p ? mulstd * Gj : 0.0f);
        neuron.setParameter(NEURON_ACTIVATION_VALUE, gj);
        neuron.setParameter(NEURON_MATCHVALUE, Gj);
        return gj;
    }

    @Override
    protected double match(NeuronGroup ngroup, Neuron neuron, Instance signal) {
        float gmatch = (float) neuron.getParameter (GaussianARTTrainingSupervisor.NEURON_MATCHVALUE);
        return gmatch;
    }

    @Override
    protected void update(NeuronGroup ngroup, List<Neuron> neuron, Instance signal) {
            Neuron win = neuron.get(0);
            float nj = (float) win.getParameter(NEURON_CODEDSIGNAL);
            double [] stddim = (double []) win.getParameter(NEURON_STD);
            nj++;
            for (int dim = 0; dim < stddim.length; dim++) {
                win.put (dim, ((1 - (1 / nj)) * win.value(dim)) + ((1 / nj) * signal.value(dim)));
                stddim [dim] = (float) Math.sqrt (((1 - (1/nj)) * (stddim[dim] * stddim[dim])) + 
                                         ((1/nj) * (signal.value(dim) - win.value(dim))
                                                 * (signal.value(dim) - win.value(dim))));
            }
            win.setParameter(NEURON_STD, stddim);
            win.setParameter(NEURON_CODEDSIGNAL, nj);
    }
}
