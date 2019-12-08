
package com.phm.ml.nn.event;

import com.phm.ml.event.Event;
import com.phm.ml.nn.connection.Connection;
import java.util.Objects;

/**
 *
 * @author PARHAM
 */
public class ConnectionRemovedEvent extends Event {
    public final Connection connection;
    
    public ConnectionRemovedEvent (Connection con) {
        super ("ConnectionRemovedEvent", "");
        connection = Objects.requireNonNull(con);
    }
}
