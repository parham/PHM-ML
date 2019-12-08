
package com.phm.ml.distance;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class DefaultDDM implements DimensionalDistanceMeasure {
    @Override
    public DenseInstance calculate(Instance s1, Instance s2, DistanceInfo dinfo) {
        DenseInstance dd = new DenseInstance(new double[s1.noAttributes()]);
        for (int index = 0; index < s1.noAttributes(); index++) {
            dd.put(index, s2.value(index) - s1.value(index));
        }
        dinfo.distancedim = dd;
        return dd;
    }
}
