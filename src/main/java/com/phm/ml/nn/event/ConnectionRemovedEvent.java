
package com.phm.ml.nn.event;

import java.util.Objects;

import com.phm.ml.event.Event;
import com.phm.ml.nn.connection.Connection;

/**
 *
 * @author PARHAM
 */
public class ConnectionRemovedEvent extends Event {
	public final Connection connection;

	public ConnectionRemovedEvent(Connection con) {
		super("ConnectionRemovedEvent", "");
		connection = Objects.requireNonNull(con);
	}
}
