
package com.phm.ml.event;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author phm
 */
public class EventListenersManager {
	public HashMap<String, LinkedList<EventListener>> listeners = new HashMap<>();

	public void add(Event evt, EventListener el) {
		LinkedList<EventListener> list = listeners.get(evt.name);
		if (list == null) {
			list = new LinkedList<>();
			listeners.put(evt.name, list);
		}
		list.add(el);
	}

	public synchronized void event(final Event event) {
		LinkedList<EventListener> list = listeners.get(event.name);
		if (list != null) {
			list.stream().parallel().forEach((EventListener x) -> x.event(event));
		}
	}

	public void clear() {
		listeners.clear();
	}
}
