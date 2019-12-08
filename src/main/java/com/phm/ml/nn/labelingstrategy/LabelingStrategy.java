
package com.phm.ml.nn.labelingstrategy;

import java.util.List;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public abstract class LabelingStrategy {

	public static final String NEURON_LABEL = "neuron.label";

	public abstract void label(NeuronGroup parent, List<Neuron> winners, Instance current);
}
