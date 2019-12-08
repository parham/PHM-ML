
package com.phm.ml.distance;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public interface DimensionalDistanceMeasure {
	public DenseInstance calculate(Instance s1, Instance s2, DistanceInfo dinfo);
}
