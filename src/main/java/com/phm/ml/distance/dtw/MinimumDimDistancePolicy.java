
package com.phm.ml.distance.dtw;

import com.phm.ml.distance.DimensionalDistanceMeasure;
import com.phm.ml.distance.DistanceInfo;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class MinimumDimDistancePolicy implements DimensionalDistanceMeasure {

	@Override
	public DenseInstance calculate(Instance s1, Instance s2, DistanceInfo dinfo) {

		double[] disDim = new double[s1.noAttributes()];
		for (int index = 0; index < disDim.length; index++) {
			if (index < s2.noAttributes()) {
				disDim[index] = s2.value(index) - s1.value(index);
			}
		}

		DTWDistanceInfo tdinfo = (DTWDistanceInfo) dinfo;
		for (DTWDistanceInfo.WarpingIndex p : tdinfo.warpingPath) {
			double td = (s2.value(p.col) - s1.value(p.row));
			if (Math.abs(disDim[p.row]) > Math.abs(td)) {
				disDim[p.row] = td;
			}
		}
		dinfo.distancedim = new DenseInstance(disDim);
		return dinfo.distancedim;
	}
}