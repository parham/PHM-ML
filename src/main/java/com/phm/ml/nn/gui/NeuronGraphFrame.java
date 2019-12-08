
package com.phm.ml.nn.gui;

import java.awt.Dimension;
import java.util.HashMap;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.connection.ConnectionsContainer;

/**
 *
 * @author phm
 */
public class NeuronGraphFrame extends javax.swing.JFrame {

	private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

	private mxGraph graph;
	Object parent;
	private mxGraphComponent adapter;
	NeuronGroup ngroup;

	/**
	 * Creates new form NeuronGraphFrame
	 * 
	 * @param ng
	 */
	public NeuronGraphFrame(NeuronGroup ng) {
		super("Neuron Viewer");
		initialize(ng);
//        ngroup = ng;
//        setNeuron(ng);
	}

	protected final void initialize(NeuronGroup ng) {
		graph = new mxGraph();
		parent = graph.getDefaultParent();
		setSize(1200, 1000);
		ngroup = ng;
		setNeuron(ng);
		adapter = new mxGraphComponent(graph);
		adapter.setAutoExtend(true);
		this.getContentPane().add(adapter);
		repaint();
	}

	public void setNeuron(NeuronGroup ng) {
//        graph = new mxGraph();
//        parent = graph.getDefaultParent();
		double size = Math.max(getWidth(), getHeight()) / ng.neurons.size();
		size = size < 20 ? 20 : size;
		graph.removeCells();
		////////////////////////
		graph.getModel().beginUpdate();
		HashMap<Neuron, Object> map = new HashMap<>();
		try {
			for (int index = 0; index < ng.neurons.size(); index++) {
				Neuron nx = ng.neurons.getIndex(index);
				double x = (nx.value(0) * getWidth());
				double y = (nx.value(1) * getHeight());
				Object o = graph.insertVertex(parent, null, nx.toString(), x, y, size, size);
				map.put(nx, o);
			}
			ConnectionsContainer cc = ng.getConnections();
			for (Connection con : cc) {
				Object o1 = map.get(con.neuronOne);
				Object o2 = map.get(con.neuronTwo);
				graph.insertEdge(parent, null, con, o1, o2);
			}
		} finally {
			graph.getModel().endUpdate();
		}
		////////////////////////
//        adapter = new mxGraphComponent (graph);
//        adapter.setAutoExtend(true);
//        this.getContentPane().add(adapter);
		repaint();
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
			public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
			}

			public void ancestorResized(java.awt.event.HierarchyEvent evt) {
				formAncestorResized(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 741, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 525, Short.MAX_VALUE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void formAncestorResized(java.awt.event.HierarchyEvent evt) {// GEN-FIRST:event_formAncestorResized
		this.getContentPane().remove(adapter);
		adapter.setSize(getWidth(), getHeight());
		this.setNeuron(ngroup);
	}// GEN-LAST:event_formAncestorResized

	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables
}
