
package com.phm.ml.clusterer.graphbased;

import com.phm.ml.triangulation.Triangulator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import org.jgraph.graph.Edge; 
import org.jgrapht.Graph;

/**
 *
 * @author phm
 * @param <V>
 * @param <E>
 */
public class GraphBasedClusterer<V extends Instance, E extends Edge>
    implements Clusterer {
    
    protected Triangulator<V,E> triangMethod;
    protected GraphClusterer<V,E> clusterer;
    
    public GraphBasedClusterer (Triangulator<V,E> tm,
                                GraphClusterer<V,E> ef) {
        triangMethod = Objects.requireNonNull (tm);
        clusterer = Objects.requireNonNull(ef);
    }
    
    public Triangulator<V,E> getTriangulator () {
        return triangMethod;
    }
    public GraphClusterer<V,E> getGraphClusterer () {
        return clusterer;
    }
    
    public List<Graph<V,E>> clusterAsGraph (Dataset ds) {
        Graph<V,E> graph = triangMethod.triangulate(ds);
        if (graph != null) {
            return clusterer.clusterGraph (graph);
        }
        return null;
    }

    @Override
    public Dataset [] cluster (Dataset ds) {
        Graph<V,E> graph = triangMethod.triangulate(ds);
        if (graph != null) {
            List<Graph<V,E>> gs = clusterer.clusterGraph (graph);
            Dataset [] res = new Dataset [gs.size()];
            for (int index = 0; index < gs.size(); index++) {
                Graph<V,E> g = gs.get(index);
                DefaultDataset dd = new DefaultDataset();
                Set<V> vrts = g.vertexSet();
                for (Instance n : vrts) {
                    dd.add(n);
                }
                res [index] = dd;
            }
            return res;
        }
        return null;
    }
}