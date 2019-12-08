
package com.phm.ml.nn.event;

import com.phm.ml.event.Event;
import com.phm.ml.nn.connection.Connection;
import java.util.Objects;

/**
 *
 * @author PARHAM
 */
public class ConnectionAddedEvent extends Event {
    public final Connection connection;
    
    public ConnectionAddedEvent (Connection con) {
        super ("ConnectionAddedEvent", "");
        connection = Objects.requireNonNull(con);
    }
}
