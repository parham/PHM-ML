
package com.phm.ml.clusterer;

import java.util.HashMap;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class ClassBasedClusterer implements Clusterer {

    @Override
    public Dataset[] cluster(Dataset dtst) {
        HashMap<Object, Dataset> dres = new HashMap<>();
        dtst.stream().forEach((Instance x) -> {
            if (!dres.containsKey(x.classValue())) {
                dres.put(x.classValue(), new DefaultDataset());
            }
            Dataset tmp = dres.get(x.classValue());
            tmp.add(x);
        });
        Dataset [] dr = new Dataset [dres.size()];
        int index = 0;
        for (Object cl : dres.keySet()) {
            dr [index++] = dres.get(cl);
        }
        return dr;
    }
    
}
