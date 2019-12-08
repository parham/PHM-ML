
package com.phm.ml.triangulation;

import com.phm.ml.ParametersContainer;

/**
 *
 * @author phm
 */
public class GNGNoLearnTriangulator extends NeuralNetworkTriangulator {

    public GNGNoLearnTriangulator (ParametersContainer pc) {
        super(new GNGNoLearnSupervisor(), pc);
    }
}
