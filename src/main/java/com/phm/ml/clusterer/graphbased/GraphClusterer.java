
package com.phm.ml.clusterer.graphbased;

import java.util.List;

import org.jgraph.graph.Edge;
import org.jgrapht.Graph;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 * @param <V>
 * @param <E>
 */
public interface GraphClusterer<V extends Instance, E extends Edge> {
	public List<Graph<V, E>> clusterGraph(Graph<V, E> graph);
}
