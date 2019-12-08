
package com.phm.ml.nn.algorithms.art.egaussian;

import java.util.List;
import java.util.Objects;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTTrainingSupervisor;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class ExtendedGARTTrainingSupervisor extends GaussianARTTrainingSupervisor {

	protected EGARTLearningRateCalculator laPolicy;

	public ExtendedGARTTrainingSupervisor() {
		this(new DefaultLRC());
	}

	public ExtendedGARTTrainingSupervisor(EGARTLearningRateCalculator lp) {
		laPolicy = Objects.requireNonNull(lp);
	}

	@Override
	public String getName() {
		return "egart.train";
	}

	@Override
	protected void update(NeuronGroup ngroup, List<Neuron> neuron, Instance signal) {
		Neuron win = neuron.get(0);
		float nj = (float) win.getParameter(NEURON_CODEDSIGNAL);
		double glearn = laPolicy.learningrate(ngroup, win, signal);

		double[] stddim = (double[]) win.getParameter(NEURON_STD);
		nj++;
		for (int dim = 0; dim < stddim.length; dim++) {
			win.put(dim, ((1 - glearn) * win.value(dim)) + (glearn * signal.value(dim)));
			stddim[dim] = (float) Math.sqrt(((1 - glearn) * (stddim[dim] * stddim[dim]))
					+ (glearn * (signal.value(dim) - win.value(dim)) * (signal.value(dim) - win.value(dim))));
		}
		win.setParameter(NEURON_STD, stddim);
		win.setParameter(NEURON_CODEDSIGNAL, nj);
	}
}