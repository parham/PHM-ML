package com.phm.ml.nn.algorithms.art.fuzzy;

import com.phm.ml.nn.InputStrategy;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class FuzzyARTInputStrategy extends InputStrategy {

	@Override
	public Instance input(Instance data) {
		final int ndim = data.noAttributes();
		double[] tmp = new double[ndim * 2];
		for (int index = 0; index < ndim; index++) {
			tmp[index] = data.value(index);
			tmp[ndim + index] = 1 - data.value(index);
		}
		return new DenseInstance(tmp);
	}
}
