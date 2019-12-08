
package com.phm.ml.nn.event;

import com.phm.ml.event.Event;
import com.phm.ml.nn.Neuron;
import java.util.Objects;

/**
 *
 * @author PARHAM
 */
public class NeuronRemovedEvent extends Event {
    public final Neuron neuron;
    
    public NeuronRemovedEvent (Neuron n) {
        super ("NeuronRemovedEvent", "");
        neuron = Objects.requireNonNull(n);
    }
}
