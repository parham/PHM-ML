
package com.phm.ml.triangulation;

import com.phm.ml.ParametersContainer;
import com.phm.ml.nn.algorithms.gng.GNGTrainingSupervisor;

/**
 *
 * @author phm
 */
public class GNGTriangulator extends NeuralNetworkTriangulator {

	public GNGTriangulator(ParametersContainer pc) {
		super(new GNGTrainingSupervisor(), pc);
	}
}
