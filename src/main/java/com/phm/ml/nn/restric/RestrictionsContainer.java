
package com.phm.ml.nn.restric;

import java.util.LinkedList;

import com.phm.ml.nn.Neuron;

/**
 *
 * @author PARHAM
 */
public class RestrictionsContainer extends LinkedList<Restriction> {
	public boolean fulfil(final Neuron net) {
		return this.size() > 0 && !(this.stream().map((Restriction x) -> x.fulfil(net)).allMatch((f) -> !f));
	}
}
