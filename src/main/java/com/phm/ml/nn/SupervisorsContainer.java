
package com.phm.ml.nn;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class SupervisorsContainer extends LinkedList<Supervisor> {
	public boolean supervise(String name, Neuron n, Instance signal, NNResultContainer result) {
		final AtomicBoolean status = new AtomicBoolean(false);
		this.stream().filter((x) -> (x.getName().contentEquals(name))).forEach((Supervisor x) -> {
			status.set(x.supervise(n, signal, result));
		});
		return status.get();
	}
}
