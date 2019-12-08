
package com.phm.ml.nn.algorithms.art.egaussian;

import com.phm.ml.ArraySet;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class ProbabilisticGARTMAPTrainingSupervisor extends ExtendedGARTMAPTrainingSupervisor {
    
    public static final String PGARTMAP_PMIN = "pgartmap.p.min";
    
    public static final String NEURON_OCCURENCE_VECTOR = "neuron.occurence.vector";
    
    public ProbabilisticGARTMAPTrainingSupervisor () {
       this (new DefaultLRC ());
    }
    public ProbabilisticGARTMAPTrainingSupervisor (EGARTLearningRateCalculator lp) {
        laPolicy = Objects.requireNonNull(lp);
    }
    
    protected Object getClassWithHighestProbability (HashMap<Object, Double> clss) {
        if (clss != null && clss.size() > 0) {
            LinkedList<Object> keys = new LinkedList<>(clss.keySet());
            Object maxl = keys.get(0);
            double maxv = clss.getOrDefault(maxl, 0.0);
            for (Object k : keys) {
                double tmp = clss.getOrDefault(k, 0.0);
                if (tmp > maxv) {
                    maxl = k;
                    maxv = tmp;
                }
            }
            return maxl;
        }
        return null;
    }
    protected void resetNeuronClass (NeuronGroup ngroup, Neuron n) {
        HashMap<Object, Double> clsmap = (HashMap<Object, Double>) n.getParameter(NEURON_OCCURENCE_VECTOR);
        Object lbl = getClassWithHighestProbability(clsmap);
        if (lbl == null) lbl = DEFAULT_CLASS;
        n.setClassValue(lbl);
    }
    @Override
    protected Neuron initializeGaussianARTMAPNeuron (NeuronGroup ngroup, 
                                                     Neuron n) {
        n.setParameter(NEURON_ACTIVATION_VALUE, 0.0f);
        n.setParameter(NEURON_MATCHVALUE, 0.0f);
        n.setParameter(NEURON_PROBABILITY, 0.0f);
        n.setParameter(NEURON_CODEDSIGNAL, 1.0f);
        n.setParameter(NEURON_STD, ngroup.getParameter(NEURON_DEFAULT_STD));
        HashMap<Object, Double> clsmap = new HashMap<>();
        ArraySet<Object> clslist = (ArraySet<Object>) ngroup.getParameter(ARTMAP_DCLASS_LIST);
        for (Object css : clslist) {
            clsmap.put(css, 0.0);
        }
        n.setParameter(NEURON_OCCURENCE_VECTOR, clsmap);
        resetNeuronClass(ngroup, n);
        return n;
    }
    @Override
    public void initialize(Neuron ngroup) {
        super.initialize(ngroup);
        ngroup.setParameter(PGARTMAP_PMIN, 0.32f);
    }
    
    @Override
    public String getName() {
        return "epgartmap.train";
    }
    @Override
    protected void update(NeuronGroup ngroup, List<Neuron> neuron, Instance signal) {
        super.update(ngroup, neuron, signal);
        Neuron win = neuron.get(0);
        updateClassValue (ngroup, win, signal);
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
        Neuron newNeuron = initializeGaussianARTMAPNeuron (ngroup, new Neuron(signal));
        updateClassValue (ngroup, newNeuron, signal);
        newNeuron.setParameter(NEURON_STD, dimstd);
        ngroup.addInternalNeuron(newNeuron);
        
        return newNeuron;
    }
    @Override
    protected boolean labelMatched (NeuronGroup ngroup, List<Neuron> winner, Instance signal) {
        HashMap<Object, Double> clsmap = (HashMap<Object, Double>) winner.get(0).getParameter(NEURON_OCCURENCE_VECTOR);
        double value = clsmap.getOrDefault (signal.classValue(), 0.0);
        float codedsig = (float) winner.get(0).getParameter(NEURON_CODEDSIGNAL) + 1;
        float pmin = (float) ngroup.getParameter(PGARTMAP_PMIN);
        return pmin <= (value / codedsig);
    }
    
    protected void updateClassValue (NeuronGroup ngroup, Neuron n, Instance signal) {
        HashMap<Object, Double> clsmap = (HashMap<Object, Double>) n.getParameter(NEURON_OCCURENCE_VECTOR);
        double v = clsmap.containsKey(signal.classValue()) ? clsmap.get(signal.classValue()) : 0.0f;
        clsmap.put((String) signal.classValue(), v + 1);
        resetNeuronClass(ngroup, n);
    }
}
