
package com.phm.ml.nn;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class IneffectiveInputStrategy extends InputStrategy {

	@Override
	public Instance input(Instance signal) {
		return signal;
	}
}
