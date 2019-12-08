
package com.phm.ml.nn.algorithms.art.fuzzy;

import com.phm.ml.ParametersContainer;
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.art.ARTMAPTrainingSupervisor;
import com.phm.ml.nn.event.NeuronAddedEvent;
import java.util.LinkedList;
import java.util.List;
import net.sf.javaml.core.Instance;

/**
 * <p>
 * <b>Publication details:<br></b>
 * <b>Authors:</b> Gail A. Carpenter, Stephen Grossberg, Natalya Markuzon, John H. Reynolds, and David B. Rosen <br>
 * <b>Year:</b> 1992 <br>
 * <b>Title:</b> Fuzzy ARTMAP: A neural network architecture for incremental supervised learning of analog multidimensional maps <br>
 * <b>Published In:</b> IEEE Transactions on Neural Networks 3(5) <br>
 * <b>Page:</b> 698 - 713 <br>
 * <b>DOI:</b> 10.1109/72.159059 <br>
 * </p>
 * @author Parham Nooralishahi - PHM!
 * @email parham.nooralishahi@gmail.com
 */
public class FuzzyARTMAPTrainingSupervisor extends ARTMAPTrainingSupervisor {

    public static final String DEFAULT_LABEL = "null";
    
    public static final String FUZZYARTMAP_CHOICE_PARAMTER = "fuzzyart.choices.param";
    public static final String FUZZYARTMAP_LEARNING_RATE = "fuzzyart.learning.rate";
    
    @Override
    public void initialize(Neuron neuron) {
        neuron.setParameter(ARTMAP_VIGILANCE_PARAMTER, 0.92f);
        neuron.setParameter(FUZZYARTMAP_CHOICE_PARAMTER, 0.00001f);
        neuron.setParameter(FUZZYARTMAP_LEARNING_RATE, 0.5f);
        neuron.setParameter(ARTMAP_RESONANCE_OCCURED, false);
        neuron.setParameter(ARTMAP_DCLASS_LIST, new LinkedList<String>());
        neuron.setParameter(ARTMAP_EPSILON, 0.001);
        ////////////
        neuron.setInputStrategy(new FuzzyARTInputStrategy());
    }

    protected Neuron initializeFuzzyARTNeuron (NeuronGroup ng, 
                                                       Neuron n) {
        n.setParameter(NEURON_ACTIVATION_VALUE, 0.0f);
        n.setParameter(NEURON_MATCHVALUE, 0.0f);
        return n;
    }
    
    
    @Override
    protected double activate(NeuronGroup ngroup, Neuron neuron, Instance signal) {
        double choiceValue = (float) ngroup.getParameter(FuzzyARTTrainingSupervisor.FUZZYART_CHOICE_PARAMTER);
        double vsubs = 0;
        double vn = 0;
        final int ndims = neuron.noAttributes();
        for (int index = 0; index < ndims; index++) {
            vsubs += Math.min(neuron.value (index), signal.value(index));
            vn += neuron.value(index);
        }
        double res = vsubs / (choiceValue + vn);
        
        return res;
    }

    @Override
    protected double match(NeuronGroup ngroup, Neuron neuron, Instance signal) {
        double vsubs = 0;
        double vs = 0;
        int ndims = neuron.noAttributes();
        for (int index = 0; index < ndims; index++) {
            vsubs += Math.min(signal.value(index), neuron.value(index));
            vs += signal.value(index);
        }
        return vsubs / vs;
    }

    @Override
    protected void update(NeuronGroup ngroup, List<Neuron> neuron, Instance signal) {
        Neuron win = neuron.get(0);
        double learningRate = (float) ngroup.getParameter(FuzzyARTTrainingSupervisor.FUZZYART_LEARNING_RATE);
        final int ndims = win.noAttributes();
        
        for (int index = 0; index < ndims; index++) {
            win.put(index, ((1 - learningRate) * win.value(index)) + 
                                     (learningRate * Math.min(win.value(index), signal.value(index))));
        }
    }

    @Override
    protected Neuron insert(NeuronGroup ngroup, Instance signal) {
        double [] sd = new double[signal.noAttributes()];
        for (int index = 0; index < sd.length; index++) {
            sd [index] = signal.value(index);
        }
        Neuron newNeuron = initializeFuzzyARTNeuron(ngroup, new Neuron(sd));
        ngroup.addInternalNeuron(newNeuron);
        ngroup.listeners.event(new NeuronAddedEvent(newNeuron));
        return newNeuron;
    }

    @Override
    public String getName() {
        return "fuzzyartmap.train";
    }


    @Override
    protected NNResult prepareResult(Neuron neuron, Instance signal, List<Neuron> winners, NNResult resc) {
        resc.parameters = new ParametersContainer ();
        resc.setParameter(Neuron.SYSTEM_STATUS, neuron.getParameter(Neuron.SYSTEM_STATUS));
        resc.setParameter(Neuron.NEURON_ID, neuron.getParameter(Neuron.NEURON_ID));
        resc.setParameter(Neuron.NEURON_CENTROID, neuron.getParameter(Neuron.NEURON_CENTROID));
        resc.setParameter(Neuron.NEURON_DIMENSION, neuron.getParameter(Neuron.NEURON_DIMENSION));
        resc.setParameter(Neuron.NEURON_NUM_CHANNEL, neuron.getParameter(Neuron.NEURON_NUM_CHANNEL));
        resc.setParameter(Neuron.NUM_CONNECTIONS, neuron.getParameter(Neuron.NUM_CONNECTIONS));
        resc.setParameter(Neuron.RECIEVED_SIGNALS_NUM, neuron.getParameter(Neuron.RECIEVED_SIGNALS_NUM));
        resc.setParameter(NeuronGroup.NUM_NEURONS, neuron.getParameter(NeuronGroup.NUM_NEURONS));
        resc.setParameter(FUZZYARTMAP_CHOICE_PARAMTER, neuron.getParameter(FUZZYARTMAP_CHOICE_PARAMTER));
        resc.setParameter(FUZZYARTMAP_LEARNING_RATE, neuron.getParameter(FUZZYARTMAP_LEARNING_RATE));
        resc.setParameter(ARTMAP_RESONANCE_OCCURED, neuron.getParameter(ARTMAP_RESONANCE_OCCURED));
        resc.setParameter(ARTMAP_TEMPLATES_LIMIT, neuron.getParameter(ARTMAP_TEMPLATES_LIMIT));
        resc.setParameter(ARTMAP_VIGILANCE_PARAMTER, neuron.getParameter(ARTMAP_VIGILANCE_PARAMTER));
        resc.setParameter(NEURON_ACTIVATION_VALUE, neuron.getParameter(NEURON_ACTIVATION_VALUE));
        return resc;
    }
    
}
