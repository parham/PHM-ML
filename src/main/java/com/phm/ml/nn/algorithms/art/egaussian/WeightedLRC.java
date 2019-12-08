
package com.phm.ml.nn.algorithms.art.egaussian;

import static com.phm.ml.nn.algorithms.art.gaussian.GaussianARTTrainingSupervisor.NEURON_CODEDSIGNAL;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class WeightedLRC implements EGARTLearningRateCalculator {

	public static final String WEIGHTED_LRC_LEARNRATE = "wlrc.learnrate";

	protected float learnRate = 1.0f;

	public WeightedLRC(float lr) {
		learnRate = lr;
	}

	@Override
	public double learningrate(NeuronGroup ngroup, Neuron neuron, Instance signal) {
		float nj = (float) neuron.getParameter(NEURON_CODEDSIGNAL);
		return learnRate / (nj + 1);
	}
}
