
package com.phm.ml.clusterer.graphbased;

import java.util.List;
import net.sf.javaml.core.Instance;
import org.jgraph.graph.Edge;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;

/**
 *
 * @author phm
 * @param <V>
 * @param <E>
 */
public abstract class EnhancedConnectivityBasedGraphClusterer <V extends Instance, E extends Edge>  
    extends ConnectivityBasedGraphClusterer<V, E> {

    public EnhancedConnectivityBasedGraphClusterer(EdgeFactory<V, E> ef) {
        super (ef);
    }
    
    @Override
    public List<Graph<V, E>> clusterGraph(Graph<V, E> graph) {
        if (enhance (graph)) {
            List<Graph<V,E>> gs = super.clusterGraph(graph);
            normalize(gs);
            return gs;
        }
        return null;
    }
    
    public abstract boolean enhance (Graph<V, E> g);
    public abstract boolean normalize (List<Graph<V,E>> gs);
}
