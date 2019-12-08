
package com.phm.ml.distance;

import com.phm.ml.ParametersContainer;
import java.util.LinkedList;
import java.util.List;
import net.sf.javaml.core.Instance;

/**
 *
 * @author PARHAM
 */
public abstract class DistanceMethod {
    
    public abstract DistanceInfo distance (Instance sc1, Instance sc2, ParametersContainer pc);
    
    public List<DistanceInfo> distance (List<Instance> arr, Instance s, ParametersContainer pc) {
        LinkedList<DistanceInfo> res = new LinkedList<>();
        arr.stream().forEach((Instance ss) -> res.add(distance (ss, s, pc)));
        return res;
    }
}