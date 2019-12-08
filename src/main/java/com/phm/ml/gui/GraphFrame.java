
package com.phm.ml.gui;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.phm.ml.ArraySet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import javax.swing.ImageIcon;
import net.sf.javaml.core.Instance;
import org.jgraph.graph.Edge;

/**
 *
 * @author phm
 * @param <V>
 * @param <E>
 */
public class GraphFrame<V extends Instance, E extends Edge> extends javax.swing.JFrame {
   
    Object parent;
    private mxGraphComponent adapter;
    LinkedList<GraphVisualComponent<V,E>> graphs = new LinkedList<>();
    
    public GraphFrame () {
        super ("Graph Viewer");
        adapter = new mxGraphComponent (new mxGraph ());
        adapter.setSize(getWidth(), getHeight());
        adapter.setAutoExtend(true);
        adapter.setAutoScroll(true);
        this.getContentPane().add(adapter);
        initComponents();
    }
    public void setDragable (boolean drag) {
        adapter.setDragEnabled(drag);
    }
    public void setBackgroundImage (ImageIcon img) {
        adapter.setBackgroundImage (img);
    }
    public ImageIcon getBackgroundImage () {
        return adapter.getBackgroundImage();
    }
    public void addGraph (GraphVisualComponent<V,E> ng) {
        graphs.add(ng);
        for (V n : ng.graph.vertexSet()) {
            double x = n.value(0);
            double y = n.value(1);
            if (x > ng.xMax) ng.xMax = x;
            if (y > ng.yMax) ng.yMax = y;
        }
        ng.xMax += (ng.xMax / 10);
        ng.yMax += (ng.yMax / 10);
        repaint ();
    }
    public void removeGraph (GraphVisualComponent<V,E> g) {
        graphs.remove(g);
    }
    public void clearGraph () {
        graphs.clear();
    }
    
    protected String generateRandomColor () {
        Random rand = new Random(System.currentTimeMillis());
        int r = rand.nextInt (255);
        int g = rand.nextInt (255);
        int b = rand.nextInt (255);
        String rs = Integer.toHexString(r);
        String gs = Integer.toHexString(g);
        String bs = Integer.toHexString(b);
        String cs = "#" + rs + gs + bs;
        return cs.toUpperCase();
    }
    
    protected String colorToString (Color c) {
        String cs = "#" + c.getRed() + c.getGreen() + c.getBlue();
        return cs.toUpperCase();
    }
    
    @Override
    public void paint (Graphics g) {
        super.paint(g);
        adapter.setSize(getSize());
        mxGraph grph = new mxGraph();
        grph.getModel().beginUpdate();
        try {
            for (GraphVisualComponent<V,E> gr : graphs) {
                HashMap<Instance, Object> map = new HashMap<>();
                int numv = gr.graph.vertexSet().size();
                Set<V> vs = new ArraySet<V> (gr.graph.vertexSet());
                String color = colorToString(gr.nodeColor);
                if (gr.autoColoring) {
                    color = generateRandomColor();
                }
                for (V nx : vs) {
                    double x = (nx.value(0) / gr.xMax) * getWidth();
                    double y = (nx.value(1) / gr.yMax) * getHeight();
                    Object o = grph.insertVertex(parent, null, nx.toString(), x, y, gr.xSize, gr.ySize, "fillColor=" + 
                                              color + ";" + mxConstants.STYLE_SHAPE + "=" + gr.nodeShape);
                    map.put(nx, o);
                }
                if (gr.drawConnection) {
                    Set<E> eds = gr.graph.edgeSet();
                    eds.stream().forEach((con) -> {
                        Object o1 = map.get(con.getSource());
                        Object o2 = map.get(con.getTarget());
                        grph.insertEdge (parent, null, con, o1, o2);
                    });
                }
            }
        } finally {
            grph.getModel().endUpdate();
        }
        adapter.setGraph(grph);
    }
//    
//    public void setGraph (List<Graph<V,E>> gs) {
//        graph.removeCells();
//        graph.getModel().beginUpdate();
//        HashMap<Instance, Object> map = new HashMap<>();
//        try {
//            for (Graph<V,E> ng : gs) {
//                int numv = ng.vertexSet().size();
//                double size = Math.max(getWidth(), getHeight()) / numv;
//                size = size < 30 ? size : 30;
//                final double fsize = size;
//                String color = generateRandomColor();
//                Set<V> vs = ng.vertexSet();
//                vs.stream().forEach((nx) -> {
//                    double x = (nx.value(0) * getWidth());
//                    double y = (nx.value(1) * getHeight());
//                    Object o = graph.insertVertex(parent, null, nx.toString(), 
//                                            x, y, fsize, fsize, 
//                                            "fillColor=" + color + ";" + 
//                                            mxConstants.STYLE_SHAPE + "=ellipse");
//                    map.put(nx, o);
//                });
//                if (drawConnection) {
//                    Set<E> eds = ng.edgeSet();
//                    eds.stream().forEach((con) -> {
//                        Object o1 = map.get(con.getSource());
//                        Object o2 = map.get(con.getTarget());
//                        graph.insertEdge (parent, null, con, o1, o2);
//                    });
//                }
//            }
//        } finally {
//            graph.getModel().endUpdate();
//        }
//        repaint ();
//    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 741, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 525, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_formAncestorResized
        adapter.setSize(getWidth(), getHeight());
        repaint();
    }//GEN-LAST:event_formAncestorResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
