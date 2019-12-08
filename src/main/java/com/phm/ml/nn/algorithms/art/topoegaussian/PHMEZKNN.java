
package com.phm.ml.nn.algorithms.art.topoegaussian;

import java.util.Set;

import com.phm.ml.ArraySet;

import net.sf.javaml.distance.DistanceMeasure;

/**
 *
 * @author phm
 */
public class PHMEZKNN extends ExtendedZhiliangKNearestNeighbors {
	public PHMEZKNN(DistanceMeasure dm, int k, double cmin, Set<Object> cls) {
		super(dm, k, cmin);
		classes = new ArraySet<>(cls);
	}

	@Override
	protected void initClassDistribution() {
		// Empty body
	}
}
