
package com.phm.ml.nn.restric;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;

/**
 *
 * @author PARHAM
 */
public class NeuronQuantityRestriction implements Restriction {
	public final int limit;

	public NeuronQuantityRestriction(int l) {
		limit = l;
	}

	@Override
	public boolean fulfil(Neuron net) {
		Object tmp = net.getParameter(NeuronGroup.NUM_NEURONS);
		return tmp != null && (Integer) tmp >= limit;
	}
}
