
package com.phm.ml.distance;

import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;

/**
 *
 * @author phm
 */
public class IneffectiveDistanceMeasure implements DistanceMeasure {

    @Override
    public double measure(Instance x, Instance y) {
        return 0;
    }

    @Override
    public boolean compare(double x, double y) {
        return true;
    }

    @Override
    public double getMinValue() {
        return 0;
    }

    @Override
    public double getMaxValue() {
        return 1;
    }
}
