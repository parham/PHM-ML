
package com.phm.ml.nn.event;

import java.util.Objects;

import com.phm.ml.event.Event;
import com.phm.ml.nn.Neuron;

/**
 *
 * @author PARHAM
 */
public class NeuronRemovedEvent extends Event {
	public final Neuron neuron;

	public NeuronRemovedEvent(Neuron n) {
		super("NeuronRemovedEvent", "");
		neuron = Objects.requireNonNull(n);
	}
}
