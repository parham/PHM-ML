
package com.phm.ml.siggen;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author PARHAM
 */
public abstract class SignalGenerator implements Enumeration<Instance> {

	protected final Dataset dataset;

	public SignalGenerator(Dataset ds) {
		dataset = Objects.requireNonNull(ds);
	}

	public abstract int countGeneratedSignals();

	public abstract int countRemainSignals();

	public Dataset getDataSet() {
		return dataset;
	}

	public List<Instance> nextElement(int num) {
		LinkedList<Instance> list = new LinkedList<>();
		if (num > 0) {
			for (int index = 0; index < num; index++) {
				Instance df = nextElement();
				if (df != null)
					list.add(df);
				else
					break;
			}
		}
		return list;
	}
}
