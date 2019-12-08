
package com.phm.ml.nn.event;

import com.phm.ml.event.Event;
import com.phm.ml.nn.Neuron;
import java.util.Objects;

/**
 *
 * @author PARHAM
 */
public class NeuronAddedEvent extends Event {
    public final Neuron neuron;
    
    public NeuronAddedEvent (Neuron n) {
        super ("NeuronAddedEvent", "");
        neuron = Objects.requireNonNull(n);
    }
}
