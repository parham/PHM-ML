
package com.phm.ml.gui;

import com.mxgraph.util.mxConstants;
import java.awt.Color;
import net.sf.javaml.core.Instance;
import org.jgraph.graph.Edge;
import org.jgrapht.Graph;

/**
 *
 * @author phm
 * @param <V>
 * @param <E>
 */
public class GraphVisualComponent<V extends Instance, E extends Edge> {
    public boolean autoColoring = false;
    public Color nodeColor = Color.BLUE;
    public Color connectionColor = Color.BLACK;
    public String nodeShape = mxConstants.SHAPE_ELLIPSE;
    public boolean drawConnection = true;
    public float xSize = 10.0f;
    public float ySize = 10.0f;
    public boolean dynamicNodeSize = false;
    public double xMax = 1.0f;
    public double yMax = 1.0f;
    public Graph<V,E> graph;
    
    public GraphVisualComponent () {
        
    }
    public GraphVisualComponent (Graph<V,E> g) {
        graph = g;
    }
}
