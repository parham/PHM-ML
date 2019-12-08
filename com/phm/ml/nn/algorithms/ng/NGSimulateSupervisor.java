
package com.phm.ml.nn.algorithms.ng;

import com.phm.ml.ParametersContainer;
import com.phm.ml.distance.DistanceInfo;
import com.phm.ml.nn.Supervisor;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.NeuronComparePolicy;
import com.phm.ml.nn.NeuronDistanceMethod;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class NGSimulateSupervisor extends Supervisor {
    
    public static final String NEURON_DISTANCE = "neuron.distance";
    
    public static final NeuronComparePolicy COMPARE_POLICY = new NeuronComparePolicy(NEURON_DISTANCE);
    protected NeuronDistanceMethod distanceMethod;
    
    public NGSimulateSupervisor (NeuronDistanceMethod dm) {
        distanceMethod = Objects.requireNonNull(dm);
    }

    @Override
    public void initialize(Neuron neuron) {
        // Empty body
    }
    
    protected void onDistanceCalculation (NeuronGroup ngroup, List<Neuron> neuron, Instance signal) {
        List<Neuron> neurons = ngroup.neurons.toList();
        neurons.stream().forEach((Neuron n) -> {
            DistanceInfo dd = distanceMethod.distance(ngroup, n, signal);
            float md = (float) dd.distance;
            n.setParameter(NEURON_DISTANCE, md);
        });
    }
    
    @Override
    protected boolean superviseOperator (Neuron neuron, Instance signal, List<Neuron> result) {
        NeuronGroup ngroup = (NeuronGroup) neuron;
        List<Neuron> neurons = ngroup.neurons.toList();
        onDistanceCalculation(ngroup, neurons, signal);
        Collections.sort(neurons, COMPARE_POLICY);
        result.addAll(neurons);
        return true;
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
        resc.setParameter(NGSimulateSupervisor.NEURON_DISTANCE, neuron.getParameter(NGSimulateSupervisor.NEURON_DISTANCE));
        
        return resc;
    }

    @Override
    public String getName() {
        return "ng.simulate";
    }
}
