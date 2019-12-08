
package com.phm.ml.filter;

import java.util.Collections;
import java.util.LinkedList;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class NormalizeDatasetFilter implements DatasetFilter {

    public NormalizeDatasetFilter () {
        // Empty body
    }
    
    @Override
    public void filter(Dataset ds) {
        int numdim = ds.get(0).noAttributes();
        for (int dim = 0; dim < numdim; dim++) {
            LinkedList<Double> dt = new LinkedList<>();
            for (int index = 0; index < ds.size(); index++) {
                dt.add(ds.get(index).value(dim));
            }
            double min = Collections.min (dt);
            double max = Collections.max (dt);
            // Normalize dataset
            for (Instance d : ds) {
                double v = d.value(dim);
                v = (v - min) / (max - min);
                d.put(dim, v);
            }
        }
    }
}
