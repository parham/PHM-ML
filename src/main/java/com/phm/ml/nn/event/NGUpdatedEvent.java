
package com.phm.ml.nn.event;

import java.util.Objects;

import com.phm.ml.event.Event;
import com.phm.ml.nn.NeuronGroup;

/**
 *
 * @author PARHAM
 */
public class NGUpdatedEvent extends Event {
	public final NeuronGroup group;

	public NGUpdatedEvent(NeuronGroup ng) {
		super("NGUpdatedEvent", "");
		group = Objects.requireNonNull(ng);
	}
}
