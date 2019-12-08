
package com.phm.ml.triangulation;

import org.jgraph.graph.Edge;
import org.jgrapht.Graph;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 * @param <V>
 * @param <E>
 */
public interface Triangulator<V extends Instance, E extends Edge> {
	public Graph<V, E> triangulate(Dataset ds);
}
