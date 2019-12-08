
package com.phm.ml.nn.event;

import java.util.Objects;

import com.phm.ml.event.Event;
import com.phm.ml.nn.Neuron;

/**
 *
 * @author PARHAM
 */
public class NeuronAddedEvent extends Event {
	public final Neuron neuron;

	public NeuronAddedEvent(Neuron n) {
		super("NeuronAddedEvent", "");
		neuron = Objects.requireNonNull(n);
	}
}
