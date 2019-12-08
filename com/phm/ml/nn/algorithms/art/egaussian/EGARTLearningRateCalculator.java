
package com.phm.ml.nn.algorithms.art.egaussian;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public interface EGARTLearningRateCalculator {
    public double learningrate (NeuronGroup ngroup, Neuron neuron, Instance signal);
}
