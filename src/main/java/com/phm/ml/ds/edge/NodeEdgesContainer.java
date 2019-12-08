
package com.phm.ml.ds.edge;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.phm.ml.ArraySet;
import com.phm.ml.ParametersContainer;
import com.phm.ml.ds.FeatureNode;

/**
 *
 * @author Parham Nooralishahi - PHM!
 */
public class NodeEdgesContainer extends CopyOnWriteArraySet<NodeEdge> {

	protected ArraySet<FeatureNode> neurons = new ArraySet<FeatureNode>();

	@Override
	public boolean add(NodeEdge c) {
		if (c != null && !this.contains(c)) {
			super.add(c);
			return true;
		}
		return false;
	}

	public boolean add(FeatureNode n1, FeatureNode n2, ParametersContainer pc) {
		return add(new NodeEdge(n1, n2, pc));
	}

	public boolean add(FeatureNode n1, FeatureNode n2) {
		return add(new NodeEdge(n1, n2));
	}

	public boolean remove(FeatureNode n1, FeatureNode n2) {
		return remove(new NodeEdge(n1, n2));
	}

	public boolean contains(FeatureNode n1, FeatureNode n2) {
		return contains(new NodeEdge(n1, n2));
	}

	public List<FeatureNode> neighbors(FeatureNode n) {
		LinkedList<FeatureNode> neighbors = new LinkedList<>();
		this.stream().map((x) -> x.connectTo(n)).filter((temp) -> (temp != null)).forEach((temp) -> {
			neighbors.add(temp);
		});

		return neighbors;
	}

	public Set<NodeEdge> edges(FeatureNode source, FeatureNode target) {
		Set<NodeEdge> s = new ArraySet<>();
		NodeEdge c = new NodeEdge(source, target);
		for (NodeEdge x : this) {
			if (c.equals(x)) {
				s.add(x);
			}
		}
		return s;
	}

	public NodeEdge edge(FeatureNode source, FeatureNode target) {
		NodeEdge c = new NodeEdge(source, target);
		for (NodeEdge x : this) {
			if (c.equals(x)) {
				return x;
			}
		}
		return null;
	}
}
