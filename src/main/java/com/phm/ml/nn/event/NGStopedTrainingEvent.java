
package com.phm.ml.nn.event;

import java.util.Objects;

import com.phm.ml.event.Event;
import com.phm.ml.nn.NeuronGroup;

/**
 *
 * @author PARHAM
 */
public class NGStopedTrainingEvent extends Event {
	public final NeuronGroup group;

	public NGStopedTrainingEvent(NeuronGroup ng) {
		super("NGStopedEvent", "");
		group = Objects.requireNonNull(ng);
	}
}
