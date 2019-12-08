
package com.phm.ml.event;

/**
 *
 * @author phm
 * @param <EventType>
 */
public interface EventListener<EventType extends Event> {
    public void event (EventType event);
}
