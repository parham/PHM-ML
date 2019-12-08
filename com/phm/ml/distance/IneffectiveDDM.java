

package com.phm.ml.distance;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class IneffectiveDDM implements DimensionalDistanceMeasure {

    @Override
    public DenseInstance calculate(Instance s1, Instance s2, DistanceInfo dinfo) {
        return new DenseInstance(new double[s1.noAttributes()]);
    }
    
}
