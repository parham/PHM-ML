
package com.phm.ml.distance.dtw;

import com.phm.ml.distance.DimensionalDistanceMeasure;
import com.phm.ml.distance.DistanceInfo;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class AverageDimDistancePolicy implements DimensionalDistanceMeasure {

	@Override
	public DenseInstance calculate(Instance s1, Instance s2, DistanceInfo dinfo) {
		double[] dims = new double[s1.noAttributes()];
		double[] ndims = new double[dims.length];
		DTWDistanceInfo dinf = (DTWDistanceInfo) dinfo;
		for (DTWDistanceInfo.WarpingIndex p : dinf.warpingPath) {
			dims[p.row] += (s2.value(p.col) - s1.value(p.row));
			ndims[p.row]++;
		}
		for (int index = 0; index < dims.length; index++) {
			if (ndims[index] != 0) {
				dims[index] /= ndims[index];
			}
		}
		dinf.distancedim = new DenseInstance(dims);
		return dinf.distancedim;
	}
}
