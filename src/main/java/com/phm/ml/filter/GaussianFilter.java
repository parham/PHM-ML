
package com.phm.ml.filter;

import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.InstanceFilter;

/**
 *
 * @author phm
 */
public class GaussianFilter implements InstanceFilter {
	@Override
	public void filter(Instance inst) {
		final double c = Math.sqrt(1.0f / Math.PI);
		for (int index = 0; index < inst.noAttributes(); index++) {
			double tmp = (c * (Math.exp(-1 * (inst.value(index) * inst.value(index)))));
			inst.put(index, tmp);
		}
	}
}