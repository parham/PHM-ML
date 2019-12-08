
package com.phm.ml.ds.edge;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.Edge;

import com.phm.ml.ParametersContainer;
import com.phm.ml.ds.FeatureNode;
import com.phm.ml.nn.connection.Connection;

/**
 *
 * @author phm
 */
public class NodeEdge implements Edge, Serializable {

	private FeatureNode beginNode;
	private FeatureNode endNode;
	private final ParametersContainer parameters = new ParametersContainer();
	private AttributeMap attributes = new AttributeMap();

	public NodeEdge() {

	}

	public NodeEdge(FeatureNode b, FeatureNode e) {
		beginNode = Objects.requireNonNull(b);
		endNode = Objects.requireNonNull(e);
	}

	public NodeEdge(FeatureNode b, FeatureNode e, ParametersContainer pc) {
		this(b, e);
		parameters.putAll(pc);
	}

	public void setBeginNode(FeatureNode begin) {
		beginNode = Objects.requireNonNull(begin);
	}

	public FeatureNode getBeginNode() {
		return beginNode;
	}

	public void setEndNode(FeatureNode end) {
		endNode = Objects.requireNonNull(end);
	}

	public FeatureNode getEndNode() {
		return endNode;
	}

	public boolean include(FeatureNode n) {
		return (n != null && (n.equals(beginNode) || n.equals(endNode)));
	}

	public FeatureNode connectTo(FeatureNode n) {
		if (n == null) {
			return null;
		}
		if (n.equals(beginNode)) {
			return endNode;
		}
		if (n.equals(endNode)) {
			return beginNode;
		}
		return null;
	}

	@Override
	public AttributeMap getAttributes() {
		return attributes;
	}

	@Override
	public Map changeAttributes(Map map) {
		attributes.putAll(map);
		return attributes;
	}

	@Override
	public void setAttributes(AttributeMap am) {
		changeAttributes(am);
	}

	public Object setParameter(String param, Object v) {
		return parameters.put(param, v);
	}

	public Object getParameter(String param) {
		return parameters.get(param);
	}

	public void clearParameters() {
		parameters.clear();
	}

	@Override
	public void setSource(Object o) {
		beginNode = Objects.requireNonNull((FeatureNode) o);
	}

	@Override
	public Object getSource() {
		return beginNode;
	}

	@Override
	public Object getTarget() {
		return endNode;
	}

	@Override
	public void setTarget(Object o) {
		endNode = Objects.requireNonNull((FeatureNode) o);
	}

	@Override
	public String toString() {
		return beginNode.toString() + " --> " + endNode.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null && obj instanceof Connection && obj.hashCode() == this.hashCode());
	}

	@Override
	public int hashCode() {
		int hash = 3;
		int n1 = this.beginNode.getID();
		int n2 = this.endNode.getID();
		if (n1 > n2) {
			hash = 89 * hash + Objects.hashCode(this.beginNode);
			hash = 89 * hash + Objects.hashCode(this.endNode);
		} else {
			hash = 89 * hash + Objects.hashCode(this.endNode);
			hash = 89 * hash + Objects.hashCode(this.beginNode);
		}

		return hash;
	}
}
