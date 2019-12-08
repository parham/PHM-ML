
package com.phm.ml.filter;

import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.InstanceFilter;

/**
 *
 * @author phm
 */
public class MeanFilter implements InstanceFilter {
    
    protected int filtersize = 3;
    
    public MeanFilter (int fsize) {
        filtersize = fsize;
    }

    @Override
    public void filter(Instance inst) {
        int fs = filtersize / 2;
        for (int index = fs; index < inst.noAttributes() - fs; index++) {
            double tmp = 0;
            for (int dim = index - fs; dim < index + fs; dim++) {
                tmp += inst.value(dim);
            }
            tmp /= filtersize;
            inst.put(index, tmp);
        }
    }

}
