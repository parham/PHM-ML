
package com.phm.ml.ds.edge;

import org.jgrapht.EdgeFactory;

import com.phm.ml.ParametersContainer;
import com.phm.ml.ds.FeatureNode;

/**
 *
 * @author phm
 */
public class NodeEdgeFactory implements EdgeFactory<FeatureNode, NodeEdge> {
	@Override
	public NodeEdge createEdge(FeatureNode source, FeatureNode target) {
		return new NodeEdge(source, target);
	}

	public NodeEdge createEdge(FeatureNode source, FeatureNode target, ParametersContainer pc) {
		return new NodeEdge(source, target, pc);
	}
}
