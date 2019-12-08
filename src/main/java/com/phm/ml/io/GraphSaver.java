
package com.phm.ml.io;

import org.jgrapht.Graph;

/**
 *
 * @author phm
 * @param <V>
 * @param <E>
 */
public interface GraphSaver<V, E> {
	public boolean save(Graph<V, E> g);
}
