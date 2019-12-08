
package com.phm.ml.ds.tree;

import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import com.phm.ml.ArraySet;
import com.phm.ml.ParametersContainer;
import com.phm.ml.ds.FeatureNode;

import net.sf.javaml.core.Instance;

/**
 *
 * @author PARHAM
 */
public class TreeFeatureNode extends FeatureNode {

	protected FeatureNode root = new FeatureNode();

	public TreeFeatureNode() {
		super();
	}

	public TreeFeatureNode(FeatureNode r) {
		super();
		root = Objects.requireNonNull(r);
	}

	public TreeFeatureNode(FeatureNode r, int nch) {
		super(nch);
		root = Objects.requireNonNull(r);
	}

	public TreeFeatureNode(FeatureNode r, double[] chs) {
		super(chs);
		root = Objects.requireNonNull(r);
	}

	public TreeFeatureNode(FeatureNode r, Instance inst) {
		super(inst);
		root = Objects.requireNonNull(r);
	}

	public TreeFeatureNode(FeatureNode r, int nch, ParametersContainer pc) {
		super(nch, pc);
		root = Objects.requireNonNull(r);
	}

	public TreeFeatureNode(FeatureNode r, double[] chs, ParametersContainer pc) {
		super(chs, pc);
		root = Objects.requireNonNull(r);
	}

	public TreeFeatureNode(FeatureNode r, Instance chs, ParametersContainer pc) {
		super(chs, pc);
		root = Objects.requireNonNull(r);
	}

	public TreeFeatureNode(int nch) {
		super(nch);
	}

	public TreeFeatureNode(double[] chs) {
		super(chs);
	}

	public TreeFeatureNode(Instance inst) {
		super(inst);
	}

	public TreeFeatureNode(int nch, ParametersContainer pc) {
		super(nch, pc);
	}

	public TreeFeatureNode(double[] chs, ParametersContainer pc) {
		super(chs, pc);
	}

	public TreeFeatureNode(Instance chs, ParametersContainer pc) {
		super(chs, pc);
	}

	public void setRoot(FeatureNode r) {
		root = Objects.requireNonNull(r);
	}

	public FeatureNode getRoot() {
		return root;
	}

	public Set<FeatureNode> nodes() {
		if (root == null)
			return null;
		ArraySet<FeatureNode> list = new ArraySet<>();
		Stack<FeatureNode> stack = new Stack<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			FeatureNode tmp = stack.pop();
			tmp.neighbors().stream().forEach(stack::push);
			list.add(tmp);
		}
		return list;
	}
}
