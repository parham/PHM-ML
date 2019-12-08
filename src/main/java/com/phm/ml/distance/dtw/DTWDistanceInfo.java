
package com.phm.ml.distance.dtw;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.phm.ml.distance.DistanceInfo;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class DTWDistanceInfo extends DistanceInfo {

	public LinkedList<WarpingIndex> warpingPath = new LinkedList<>();
	public CostMatrix costMatrix;
	public float normalizingFactor;

	public DTWDistanceInfo(Instance sc1, Instance sc2, float dis, CostMatrix cost, List<WarpingIndex> path,
			float norm) {
		super(sc1, sc2);
		this.distance = dis;
		this.distancedim = new DenseInstance(new double[sc1.noAttributes()]);
		warpingPath.addAll(path);
		costMatrix = Objects.requireNonNull(cost);
		normalizingFactor = norm;
	}

	public DTWDistanceInfo() {
		// Empty body
	}

	public static class WarpingIndex {
		public final int row;
		public final int col;
		public final double value;

		public WarpingIndex(int r, int c) {
			this(r, c, 0.0f);
		}

		public WarpingIndex(int r, int c, double v) {
			row = r;
			col = c;
			value = v;
		}
	}
}
