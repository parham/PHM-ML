
package com.phm.ml.nn.algorithms.art.topoart;

import com.phm.ml.nn.IneffectiveInputStrategy;
import com.phm.ml.ParametersContainer;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.art.fuzzy.FuzzyARTTrainingSupervisor;
import com.phm.ml.nn.event.NeuronAddedEvent;
import java.util.LinkedList;
import java.util.List;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm 
 */
    
public class TopoFuzzyARTTrainingSupervisor extends FuzzyARTTrainingSupervisor {
    
    public static final String TOPOFUZZYART_LEARNING_RATE_B = "fuzzyart.learning.rate.b";
    public static final String TOPOFUZZYART_LEARNINGCYCLE = "topofuzzyart.learningcycle";
    public static final String TOPOFUZZYART_NEURONSTABILITY_THRESH = "topofuzzyart.nstability.thresh";
    public static final String TOPOFUZZYART_LABELS_LIST = "topofuzzyart.labels.list";
    public static final String TOPOFUZZYART_TOPOB_CAN_ACTIVATE = "topofuzzyart.topob.can.activate";
    
    public static final String NEURON_LEARNITEM_COUNTER = "neuron.learnitem.counter";
    public static final String NEURON_LABEL = "neuron.label";
    
    public TopoFuzzyARTTrainingSupervisor (int dim) {
        super (dim);
    }

    @Override
    public void initialize(Neuron ngroup) {
        super.initialize(ngroup);
        ngroup.setParameter(FUZZYART_LEARNING_RATE, 1.0f);
        ngroup.setParameter(TOPOFUZZYART_LEARNING_RATE_B, 0.3f); //0.3
        ngroup.setParameter(TOPOFUZZYART_LEARNINGCYCLE, 100);
        ngroup.setParameter(TOPOFUZZYART_NEURONSTABILITY_THRESH, 6);
        ngroup.setParameter(TOPOFUZZYART_LABELS_LIST, new LinkedList<Float>());
        ngroup.analyzers.add(new TopoARTbActivationPermission());
        ngroup.setInputStrategy(new IneffectiveInputStrategy ());
    }
    
    @Override
    protected Neuron initializeFuzzyARTNeuron (NeuronGroup ngroup, Neuron n) {
        n = super.initializeFuzzyARTNeuron(ngroup, n);
        n.setParameter(NEURON_LEARNITEM_COUNTER, 1);
        n.setParameter(NEURON_LABEL, 0.0f);
        return n;
    }
    
    @Override
    protected Neuron insert (NeuronGroup ngroup, Instance signal) {
        Neuron newNeuron = initializeFuzzyARTNeuron(ngroup, new Neuron(signal));
        ngroup.addInternalNeuron(newNeuron);
        ngroup.listeners.event(new NeuronAddedEvent(newNeuron));
        return newNeuron;
    }
    
    @Override
    public void update(NeuronGroup ngroup, List<Neuron> neuron, Instance signal) {
        //final float [] sd = signal.cdata(0);
        Neuron nA = neuron.get(0);
        //float [] dataA = nA.centroid.cdata(0);
        final int nAdims = nA.noAttributes();
        float learningRate = (float) ngroup.getParameter(FuzzyARTTrainingSupervisor.FUZZYART_LEARNING_RATE);
        for (int index = 0; index < nAdims; index++) {
            nA.put (index, ((1 - learningRate) * nA.value(index)) + (learningRate * Math.min(nA.value(index), signal.value(index))));
        }
        
        if (neuron.size() > 1) {
            Neuron nB = neuron.get(1);
            final int nBdims = nB.noAttributes();
            //float [] dataB = nB.centroid.cdata(0);
            float learningRateB = (float) ngroup.getParameter(TopoFuzzyARTTrainingSupervisor.TOPOFUZZYART_LEARNING_RATE_B);
            for (int index = 0; index < nBdims; index++) {
                nB.put(index, ((1 - learningRateB) * nB.value(index)) + (learningRateB * Math.min(nB.value(index), signal.value(index))));
            }
        }
    }
    
    @Override
    protected List<Neuron> findHighestActivatedNeurons (NeuronGroup ngroup) {
        LinkedList<Neuron> ns = new LinkedList<>(ngroup.neurons.toList());
        LinkedList<Neuron> list = new LinkedList<>();
        Neuron high = ns.get(0);
        //Neuron high2 = null;
        float vhigh = (float) high.getParameter(NEURON_ACTIVATION_VALUE);
        for (Neuron n : ns) {
            float v = (float) n.getParameter(NEURON_ACTIVATION_VALUE);
            if (vhigh <= v) {
                high = n;
                vhigh = v;
            }
        }
        list.addLast(high);
        ns.remove(high);
        /////
        if (ns.size() > 0) {
            Neuron high2 = ns.get(0);
            vhigh = (float) high2.getParameter(NEURON_ACTIVATION_VALUE);
            for (Neuron n : ns) {
                float v = (float) n.getParameter(NEURON_ACTIVATION_VALUE);
                if (vhigh <= v) {
                    high2 = n;
                    vhigh = v;
                }
            }
            list.addLast(high2);
        }
        
        return list;
    }
    
    protected boolean addInternalConnection (Neuron n1, Neuron n2) {
        if (!n1.connections.contains(n1, n2) &&
            !n2.connections.contains(n2, n1)) {
            n1.connections.add(n1, n2);
            n2.connections.add(n2, n1);
            return true;
        }
        return false;
    }
    protected boolean removeInternalConnection (Neuron n1, Neuron n2) {
        return n1.connections.remove(new Connection(n1, n2)) &&
               n2.connections.remove(new Connection(n2, n1));
    }
    
    @Override
    protected void onResonanceOccured (NeuronGroup ngroup, List<Neuron> neuron, Instance signal) {
        super.onResonanceOccured(ngroup, neuron, signal);
        if (neuron.size() > 1) {
            addInternalConnection(neuron.get(0), neuron.get(1));
        }
        neuron.stream().forEach((Neuron n) -> {
            n.processOnParameter(NEURON_LEARNITEM_COUNTER, new Neuron.ChangeQuantityByValue(1));
        });
    }
    
    @Override
    protected void onLastStep (NeuronGroup ngroup, Instance signal) {
        int lcyc = (int) ngroup.getParameter(TOPOFUZZYART_LEARNINGCYCLE);
        int numsig = (int) ngroup.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
        final int gamma = (int) ngroup.getParameter(TOPOFUZZYART_NEURONSTABILITY_THRESH);
        if (numsig != 0 && numsig % lcyc == 0) {
            List<Neuron> nlist = new LinkedList<>(ngroup.neurons.toList());
            nlist.stream().filter((Neuron n) -> {
                int na = (int) n.getParameter(NEURON_LEARNITEM_COUNTER);
                return na < gamma;
            }).forEach((Neuron x) -> {
                // Remove the neurons' connections
                Connection [] lcons = x.connections.toArray(new Connection [0]);
                for (Connection lcon : lcons) {
                    removeInternalConnection(lcon.neuronOne, lcon.neuronOne);
                }
                // Remove the neurons theirselves
                ngroup.neurons.remove(x);
            });
        }
        //onLabeling(ngroup, signal);
    }
    
    protected void onLabeling (NeuronGroup ngroup, Instance signal) {
        LinkedList<Float> labels = new LinkedList<>();
        LinkedList<Neuron> nlist = new LinkedList<>(ngroup.neurons.toList());
        
        float lindex = 0;
        while (nlist.size() > 0) {
            lindex++;
            LinkedList<Neuron> stack = new LinkedList<>();
            stack.addLast(nlist.get(0));
            labels.add(lindex);
            while (stack.size() > 0) {
                Neuron n = stack.removeLast();
                nlist.remove(n);
                n.setParameter(NEURON_LABEL, lindex);
                List<Neuron> neighbors = n.connections.neighbors();
                final float tempIndex = lindex;
                neighbors.stream().filter((Neuron x) -> {
                    return ((float) x.getParameter(NEURON_LABEL)) != tempIndex;
                }).forEach(stack::addLast);
            }
        }
        ngroup.setParameter(TOPOFUZZYART_LABELS_LIST, labels);
    }
    
    public static class TopoARTbActivationPermission implements NeuronAnalyzer {
        @Override
        public void analysis(String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) {
            boolean reso = (boolean) n.getParameter(TopoFuzzyARTTrainingSupervisor.ART_RESONANCE_OCCURED);
            int thresh = (int) n.getParameter(TopoFuzzyARTTrainingSupervisor.TOPOFUZZYART_NEURONSTABILITY_THRESH);
            int numLearn = (int) result.getRecent().winners.get(0).getParameter(TopoFuzzyARTTrainingSupervisor.NEURON_LEARNITEM_COUNTER);
            n.setParameter(TopoFuzzyARTTrainingSupervisor.TOPOFUZZYART_TOPOB_CAN_ACTIVATE, reso && (numLearn > thresh));
        }
    }
}
