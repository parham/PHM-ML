
package com.phm.ml.nn.algorithms.art.egaussian;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class PhmGARTLRC implements EGARTLearningRateCalculator {
    
    protected float gParam = 0.05f;
    protected float betaMinParam = 0.0f;
    protected float omegaParam = 1.0f;
    
    public PhmGARTLRC (float omega, float g, float betamin) {
        omegaParam = omega;
        gParam = g;
        betaMinParam = betamin;
    }
    
    @Override
    public double learningrate(NeuronGroup ngroup, Neuron neuron, Instance signal) {
        float nj = (float) neuron.getParameter(ExtendedGARTTrainingSupervisor.NEURON_CODEDSIGNAL);
        return betaMinParam + omegaParam * Math.exp(-1 * gParam * nj);
    }
}
