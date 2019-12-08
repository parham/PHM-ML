
package com.phm.ml.nn;

import com.phm.ml.ParametersContainer;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public interface NeuronAnalyzer {
    public void analysis (String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result);
}
