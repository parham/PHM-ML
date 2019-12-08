
package com.phm.ml.nn.algorithms.art;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.restric.Restriction;

/**
 *
 * @author phm
 */
public class TemplateNumberRestriction implements Restriction {

	@Override
	public boolean fulfil(Neuron net) {
		Object tmp = net.getParameter(ARTTrainingSupervisor.ART_TEMPLATES_LIMIT);
		int nt = ((NeuronGroup) net).neurons.size();
		return tmp != null && ((int) tmp) >= nt;
	}
}
