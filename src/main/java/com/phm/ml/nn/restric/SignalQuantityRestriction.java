
package com.phm.ml.nn.restric;

import com.phm.ml.nn.Neuron;

/**
 *
 * @author PARHAM
 */
public class SignalQuantityRestriction implements Restriction {
	public final int limit;

	public SignalQuantityRestriction(int l) {
		limit = l;
	}

	@Override
	public boolean fulfil(Neuron net) {
		Object tmp = net.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
		return tmp != null && (Integer) tmp >= limit;
	}
}
