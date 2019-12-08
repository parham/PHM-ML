
package com.phm.ml.nn.event;

import java.util.Objects;

import com.phm.ml.event.Event;
import com.phm.ml.nn.connection.Connection;

/**
 *
 * @author PARHAM
 */
public class ConnectionAddedEvent extends Event {
	public final Connection connection;

	public ConnectionAddedEvent(Connection con) {
		super("ConnectionAddedEvent", "");
		connection = Objects.requireNonNull(con);
	}
}
