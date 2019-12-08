
package com.phm.ml.nn;

import com.phm.ml.ParametersContainer;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class NNResult {
    public long timestamp = 0;
    public Instance signal;
    public ParametersContainer parameters;
    public LinkedList<Neuron> winners;
    
    public NNResult (long ts, ParametersContainer p, List<Neuron> wins) {
        timestamp = ts;
        parameters = Objects.requireNonNull(p);
        winners = new LinkedList<>(wins);
    }
    public NNResult () {
        winners = new LinkedList<>();
    }
    public Object setParameter (String key, Object value) {
        return parameters.put(key, value);
    }
}
