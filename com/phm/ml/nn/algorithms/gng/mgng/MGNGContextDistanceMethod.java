
package com.phm.ml.nn.algorithms.gng.mgng;

import com.phm.ml.ParametersContainer;
import com.phm.ml.distance.DistanceInfo;
import com.phm.ml.distance.DistanceMethod;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class MGNGContextDistanceMethod extends DistanceMethod {

    @Override
    public DistanceInfo distance(Instance sc1, Instance sc2, ParametersContainer pc) {
        float dw = 0;
        float dc = 0;
        final int ndims = sc1.noAttributes();
        double [] chs = new double [ndims];
        
        float [] lc = (float []) pc.get(MGNGTrainingSupervisor.NEURON_LOCAL_CONTEXT);
        float [] gc = (float []) pc.get(MGNGTrainingSupervisor.MGNG_GLOBAL_CONTEXT);
        float alpha = (Float) pc.get(MGNGTrainingSupervisor.MGNG_ALPHA);
        
        for (int dim = 0; dim < ndims; dim++) {
            dw += (float) Math.pow((sc2.value(dim) - sc1.value(dim)), 2.0);
            dc += (float) Math.pow((gc[dim] - lc[dim]), 2.0);
            chs [dim] = sc2.value(dim) - sc1.value(dim);
        }
        dw = (float) Math.abs(Math.sqrt(dw));
        dc = (float) Math.abs(Math.sqrt(dc));
        
        float dist = ((1 - alpha) * dw) - (alpha * dc);
        return new DistanceInfo (sc1, sc2, dist, new DenseInstance(chs));
    }
}
