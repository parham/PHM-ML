
package com.phm.ml.nn.predictionstrategy;

import java.util.List;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public abstract class PredictionStrategy {

	public static final String NEURON_LABEL = "neuron.label";

	public abstract void predict(NeuronGroup net, List<Neuron> winners, Instance signal);
}