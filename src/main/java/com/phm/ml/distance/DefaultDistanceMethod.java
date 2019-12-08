
package com.phm.ml.distance;

import java.util.Objects;

import com.phm.ml.ParametersContainer;

import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;

/**
 *
 * @author phm
 */
public class DefaultDistanceMethod extends DistanceMethod {

	protected DistanceMeasure distanceMeasure;
	protected DimensionalDistanceMeasure dimdistanceMethod;

	public DefaultDistanceMethod() {
		this(new EuclideanDistance(), new DefaultDDM());
	}

	public DefaultDistanceMethod(DistanceMeasure dm) {
		this(dm, new DefaultDDM());
	}

	public DefaultDistanceMethod(DistanceMeasure dm, DimensionalDistanceMeasure ddm) {
		distanceMeasure = Objects.requireNonNull(dm);
		dimdistanceMethod = Objects.requireNonNull(ddm);
	}

	@Override
	public DistanceInfo distance(Instance sc1, Instance sc2, ParametersContainer pc) {
		DistanceInfo disinfo = new DistanceInfo();
		disinfo.entityOne = sc1;
		disinfo.entityTwo = sc2;
		disinfo.distance = distanceMeasure.measure(sc1, sc2);
		disinfo.distancedim = dimdistanceMethod.calculate(sc1, sc2, disinfo);
		return disinfo;
	}

}
