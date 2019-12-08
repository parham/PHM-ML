
package com.phm.ml.nn.algorithms.som;

import com.phm.ml.nn.NeuronGroup;

/**
 *
 * @author phm
 */
public interface KohonenInitializer {
    public void initialize (NeuronGroup ngroup, KohonenTrainingSupervisor kohonen);
}
