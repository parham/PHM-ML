
package com.phm.ml.nn;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.phm.ml.nn.restric.RestrictionsContainer;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public abstract class Supervisor {

	public final RestrictionsContainer restrictions = new RestrictionsContainer();

	public abstract String getName();

	public abstract void initialize(Neuron neuron);

	protected abstract boolean superviseOperator(Neuron neuron, Instance signal, List<Neuron> result);

	protected abstract NNResult prepareResult(Neuron neuron, Instance signal, List<Neuron> winners, NNResult resc);

	public boolean supervise(Neuron neuron, Instance signal, NNResultContainer res) {
		boolean status = false;
		if (!restrictions.fulfil(neuron)) {
			LinkedList<Neuron> result = new LinkedList<>();
			status = superviseOperator(neuron, signal, result);
			// Generate result
			int numsig = (int) neuron.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
			NNResult resc = new NNResult();
			resc.timestamp = numsig;
			resc.signal = signal;
			resc.winners = new LinkedList<>(result);
			prepareResult(neuron, signal, result, resc);
			res.add(resc);
		}
		return status;
	}

	public boolean isFulfil(Neuron neuron) {
		return restrictions.fulfil(neuron);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Supervisor && obj.hashCode() == hashCode();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + Objects.hashCode(getName());
		return hash;
	}
}
