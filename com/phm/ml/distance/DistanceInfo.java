
package com.phm.ml.distance;

import java.io.Serializable;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class DistanceInfo implements Serializable {
    public Instance entityOne;
    public Instance entityTwo;
    public double distance = 0.0f;
    public DenseInstance distancedim;
    
    public DistanceInfo (Instance e1, Instance e2) {
        entityOne = e1;
        entityTwo = e2;
    }
    public DistanceInfo (Instance e1, Instance e2, 
                         double dis, DenseInstance disdim) {
        this (e1, e2);
        distance = dis;
        distancedim = (DenseInstance) disdim.copy();
    }
    public DistanceInfo () {
        // Empty body
    }
}
