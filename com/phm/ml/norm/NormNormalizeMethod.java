
package com.phm.ml.norm;

import com.phm.ml.ParametersContainer;
import java.util.LinkedList;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.InstanceFilter;

/**
 *
 * @author phm
 */
public class NormNormalizeMethod implements InstanceFilter {

    @Override
    public void filter(Instance inst) {
        double mean = 0.0f;
        // Calculate mean 
        for (int index = 0; index < inst.noAttributes(); index++) {
            mean += inst.value(index);
        }
        mean /= inst.noAttributes();
        // Calculate deviation
        double dev = 0.0f;
        for (int index = 0; index < inst.noAttributes(); index++) {
            dev += (inst.value(index) - mean) * (inst.value(index) - mean);
        }
        dev = Math.sqrt (dev / inst.noAttributes());
        if (dev == 0) {
            dev = 0.000001f;
        }
        
        for (int index = 0; index < inst.noAttributes(); index++) {
            inst.put(index, (inst.value(index) - mean) / dev);
        }
    }
}
