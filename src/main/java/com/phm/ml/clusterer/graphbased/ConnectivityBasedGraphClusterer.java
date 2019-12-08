
package com.phm.ml.clusterer.graphbased;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.jgraph.graph.Edge;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 * @param <V>
 * @param <E>
 */
public class ConnectivityBasedGraphClusterer<V extends Instance, E extends Edge> implements GraphClusterer<V, E> {

	protected EdgeFactory<V, E> edgeFactory;

	public ConnectivityBasedGraphClusterer(EdgeFactory<V, E> ef) {
		edgeFactory = ef;
	}

	@Override
	public List<Graph<V, E>> clusterGraph(Graph<V, E> graph) {
		LinkedList<Graph<V, E>> clst = new LinkedList<>();
		LinkedList<V> ns = new LinkedList<>(graph.vertexSet());
		while (ns.size() > 0) {
			Stack<V> stack = new Stack<>();
			V tmp = stack.push(ns.remove(0));
			SimpleGraph<V, E> c = new SimpleGraph(edgeFactory);
			while (stack.size() > 0) {
				tmp = stack.pop();
				if (!c.containsVertex(tmp)) {
					c.addVertex(tmp);
					Set<E> tmpc = graph.edgesOf(tmp);
					for (E e : tmpc) {
						V target = e.getSource().equals(tmp) ? (V) e.getTarget() : (V) e.getSource();
						ns.remove(target);
						stack.push(target);
					}
				}
			}
			// Add Graphs
			Set<V> vs = c.vertexSet();
			for (V t : vs) {
				Set<E> te = graph.edgesOf(t);
				for (E e : te) {
					c.addEdge((V) e.getSource(), (V) e.getTarget());
				}
			}
			clst.add(c);
		}
		if (clst.size() > 0) {
			return clst;
		}
		return null;
	}

}
