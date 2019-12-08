
package com.phm.ml.triangulation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgraph.graph.Edge;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import com.phm.ml.ArraySet;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;

/**
 *
 * @author phm
 * @param <V>
 * @param <E>
 */
public class HebbianCompetitiveTriangulator<V extends Instance, E extends Edge> implements Triangulator<V, E> {

	protected EdgeFactory<V, E> edgeFactory;
	protected DistanceMeasure distance;

	public HebbianCompetitiveTriangulator(EdgeFactory<V, E> ef, DistanceMeasure dm) {
		edgeFactory = ef;
		distance = dm;
	}

	protected List<HRecord> calculateDistanceAndSort(V d, Dataset ds) {
		LinkedList<HRecord> res = new LinkedList<HRecord>();
		for (Instance x : ds) {
			double dis = Math.abs(distance.measure(x, d));
			if (dis != 0) {
				res.add(new HRecord((V) x, dis));
			}
		}
		Collections.sort(res);
		return res;
	}

	@Override
	public Graph<V, E> triangulate(Dataset ds) {
		SimpleGraph<V, E> graph = new SimpleGraph(edgeFactory);
		if (ds.size() < 4) {
			for (int index = 0; index < ds.size(); index++) {
				V dt = (V) ds.get(index);
				if (!graph.containsVertex(dt))
					graph.addVertex(dt);
			}
			ArraySet<V> vs = new ArraySet<>(graph.vertexSet());
			for (int index = 0; index < ds.size() - 1; index++) {
				V d1 = vs.get(index);
				V d2 = vs.get(index + 1);
				if (!graph.containsEdge(d1, d2))
					graph.addEdge(d1, d2);
			}
//            V d1 = (V) ds.get(0);
//            V d2 = (V) ds.get(1);
//            V d3 = (V) ds.get(2);
//            if (!graph.containsVertex(d1)) graph.addVertex(d1);
//            if (!graph.containsVertex(d2)) graph.addVertex(d2);
//            if (!graph.containsVertex(d3)) graph.addVertex(d3);
//            if (!graph.containsEdge(d1, d2)) graph.addEdge(d1, d2);
//            if (!graph.containsEdge(d2, d3)) graph.addEdge(d2, d3);
//            if (!graph.containsEdge(d1, d3)) graph.addEdge(d1, d3);

		} else {
			for (int index = 0; index < ds.size(); index++) {
				V inst = (V) ds.get(index);
				List<HRecord> rds = calculateDistanceAndSort(inst, ds);
				HRecord s0 = rds.get(0);
				HRecord s1 = rds.get(1);
				graph.addVertex(inst);
				if (!graph.containsVertex(s0.data))
					graph.addVertex(s0.data);
				if (!graph.containsVertex(s1.data))
					graph.addVertex(s1.data);
				if (!graph.containsEdge(s0.data, s1.data))
					graph.addEdge(s0.data, s1.data);
				if (!graph.containsEdge(inst, s0.data))
					graph.addEdge(inst, s0.data);
				if (!graph.containsEdge(inst, s1.data))
					graph.addEdge(inst, s1.data);
			}
		}
		return graph;
	}

	protected class HRecord implements Comparable<HRecord> {
		public double dis;
		public V data;

		public HRecord(V d, double dist) {
			dis = dist;
			data = d;
		}

		@Override
		public int compareTo(HRecord o) {
			if (o.dis < dis)
				return 1;
			else if (o.dis > dis)
				return -1;
			else
				return 0;
		}
	}

}
