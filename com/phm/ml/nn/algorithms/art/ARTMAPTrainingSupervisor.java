
package com.phm.ml.nn.algorithms.art;

import com.phm.ml.ArraySet;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronComparePolicy;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.Supervisor;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public abstract class ARTMAPTrainingSupervisor extends Supervisor {
    public static final String DEFAULT_CLASS = "nolabel";
    public static final String ARTMAP_DCLASS_LIST = "artmap.dclass.list";
    public static final String ARTMAP_VIGILANCE_PARAMTER = "artmap.vigilance.param";
    public static final String ARTMAP_RESONANCE_OCCURED = "artmap.resonance.occured";
    public static final String ARTMAP_TEMPLATES_LIMIT = "artmap.templates.limits";
    public static final String ARTMAP_EPSILON = "artmap.epsilon";
    
    public static final String NEURON_ACTIVATION_VALUE = "neuron.activation";
    public static final String NEURON_ACTIVATION_BACKUP = "neuron.activation.backup";
    public static final String NEURON_MATCHVALUE = "neuron.matchvalue";
    
    //public static final NeuronActivationComparePolicy COMPARE_POLICY = new NeuronActivationComparePolicy ();
    public static final NeuronComparePolicy COMPARE_POLICY = new NeuronComparePolicy (NEURON_ACTIVATION_VALUE);
    
    protected abstract double activate (NeuronGroup ngroup, Neuron neuron, Instance signal);
    protected abstract double match (NeuronGroup ngroup, Neuron neuron, Instance signal);
    protected abstract void update (NeuronGroup ngroup, List<Neuron> neuron, Instance signal);
    protected abstract Neuron insert (NeuronGroup ngroup, Instance signal);
    
    protected float vigilTemp = 0;
    protected boolean isChanged = false;
    
    protected void beforeCalculateActivationValues (NeuronGroup ngroup, Instance signal) {
        // Empty body
    }
    protected void onCalculateActivationValues (NeuronGroup ngroup, Instance signal) {
        ngroup.neurons.toList().stream().forEach((Neuron x) -> {
            double value = activate(ngroup, x, signal);
            x.setParameter(NEURON_ACTIVATION_VALUE, (float) value);
            x.setParameter(NEURON_ACTIVATION_BACKUP, (float) value);
        });
    }
    protected void afterCalculateActivationValues (NeuronGroup ngroup, Instance signal) {
        
    }
    
    protected List<Neuron> findHighestActivatedNeurons (NeuronGroup ngroup) {
        List<Neuron> ns = ngroup.neurons.toList();
        Neuron high = Collections.max (ns, COMPARE_POLICY);
        LinkedList<Neuron> list = new LinkedList<>();
        list.add(high);
        return list;
    }
    protected void afterFindHighestActivatedNeurons (NeuronGroup ngroup, List<Neuron> winners) {
        
    }

    protected boolean doesResonanceOccured (NeuronGroup ngroup, 
                                            List<Neuron> neuron, 
                                            Instance signal) {
        double reson = (float) ngroup.getParameter (ARTMAP_VIGILANCE_PARAMTER);
        double nv = match(ngroup, neuron.get(0), signal);
        boolean res = nv > reson;
        ngroup.setParameter (ARTMAP_RESONANCE_OCCURED, res);
        return res; 
    }
    
    protected void beforeResonanceOccured (NeuronGroup ngroup, 
                                           List<Neuron> neuron, Instance signal) {
        // Empty body
    }
    
    protected void onResonanceOccured (NeuronGroup ngroup, 
                                       List<Neuron> neuron, Instance signal) {
        update(ngroup, neuron, signal);
    }
    
    protected void afterResonanceOccured (NeuronGroup ngroup, 
                                          List<Neuron> neuron, 
                                          Instance signal) {
        
    }
    protected void beforeResonanceDoesntOccured (NeuronGroup ngroup, 
                                                 List<Neuron> neuron, 
                                                 Instance signal) {
        // Empty body
    }
    protected void onResonanceDoesntOccured (NeuronGroup ngroup, 
                                             List<Neuron> neuron, 
                                             Instance signal) {
        neuron.get(0).setParameter (NEURON_ACTIVATION_VALUE, 0.0f);
    }
    protected void afterResonanceDoesntOccured (NeuronGroup ngroup,
                                                List<Neuron> neuron, 
                                                Instance signal) {
        // Empty body
    }
    protected void onLastStep (NeuronGroup ngroup, Instance signal) {
        // Empty body
    }
    protected void onFinalization (NeuronGroup ngroup, Instance signal) {
        if (isChanged) {
            isChanged = false;
            ngroup.setParameter (ARTMAP_VIGILANCE_PARAMTER, vigilTemp);
        }
    }
    protected boolean labelExists (NeuronGroup ngroup, Instance signal) {
        ArraySet<String> clss = (ArraySet<String>) ngroup.getParameter(ARTMAP_DCLASS_LIST);
        return clss.contains((String) signal.classValue());
    }
    protected void addClass (NeuronGroup ngroup, Instance signal) {
        ArraySet<String> clss = (ArraySet<String>) ngroup.getParameter(ARTMAP_DCLASS_LIST);
        clss.add((String) signal.classValue());
        ngroup.setParameter(ARTMAP_DCLASS_LIST, clss);
    }
    protected boolean labelMatched (NeuronGroup ngroup, List<Neuron> winner, Instance signal) {
        String nlbl = (String) winner.get(0).classValue();
        String slbl = (String) signal.classValue();
        return nlbl.contentEquals(slbl);
    }
    protected void decreaseResonanceThresh (NeuronGroup ngroup, List<Neuron> winners, Instance signal) {
        onResonanceDoesntOccured(ngroup, winners, signal);
        float vigilthresh = (float) ngroup.getParameter(ARTMAP_VIGILANCE_PARAMTER);
        float actv = (float) winners.get(0).getParameter(NEURON_MATCHVALUE);
        float epsil = (float) ngroup.getParameter(ARTMAP_EPSILON);
        float temp = actv + epsil;
        ////////////////
        isChanged = true;
        vigilTemp = vigilthresh;
        ngroup.setParameter(ARTMAP_VIGILANCE_PARAMTER, temp);
    }
    protected boolean passSignal (Neuron neuron, Instance signal) {
        return true;
    }
    @Override
    protected boolean superviseOperator(Neuron neuron, Instance signal, List<Neuron> result) {
        NeuronGroup ngroup = (NeuronGroup) neuron;
        List<Neuron> neurons = ngroup.neurons.toList();
        boolean insertFlag = true;
        if (passSignal(neuron, signal)) {
            if (labelExists((NeuronGroup) neuron, signal)) {
                // Calculation Activation value for all templates
                beforeCalculateActivationValues(ngroup, signal);
                onCalculateActivationValues(ngroup, signal);
                afterCalculateActivationValues(ngroup, signal);

                for (int index = 0; index < neurons.size(); index++) {
                    // Find the template with highest activation value
                    List<Neuron> highActives = findHighestActivatedNeurons (ngroup);
                    afterFindHighestActivatedNeurons(ngroup, highActives);
                    if (doesResonanceOccured (ngroup, highActives, signal)) {
                        if (labelMatched(ngroup, highActives, signal)) {
                            beforeResonanceOccured (ngroup, highActives, signal);
                            onResonanceOccured (ngroup, highActives, signal);
                            afterResonanceOccured (ngroup, highActives, signal);
                            result.addAll (highActives);
                            insertFlag = false;
                            break;
                        } else {
                            decreaseResonanceThresh(ngroup, highActives, signal);
                        }
                    } else {
                        onResonanceDoesntOccured(ngroup, highActives, signal);
                    }
                }
            } else {
                addClass ((NeuronGroup) neuron, signal);
            }
        
            // Insertation procedure
            if (insertFlag) {
                Neuron nn = insert (ngroup, signal);
                result.add(nn);
            }

            onLastStep(ngroup, signal);
            onFinalization(ngroup, signal);
        }
        return true;
    }
}
