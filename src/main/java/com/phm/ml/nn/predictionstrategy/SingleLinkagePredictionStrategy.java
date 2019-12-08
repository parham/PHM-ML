
package com.phm.ml.nn.predictionstrategy;

import java.util.List;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 * @param <DFType>
 */
public class SingleLinkagePredictionStrategy extends PredictionStrategy {

	@Override
	public void predict(NeuronGroup net, List<Neuron> winners, Instance signal) {
		String lblsignal = (String) signal.classValue();
		if (winners.size() > 0 && lblsignal == null) {
			String type = (String) winners.get(0).getParameter(NEURON_LABEL);
			signal.setClassValue(type);
		}
	}

}
