
package com.phm.ml.ds;

import com.phm.ml.FeatureEntity;
import com.phm.ml.ParametersContainer;
import com.phm.ml.ds.edge.NodeEdgesContainer;
import java.util.List;
import net.sf.javaml.core.Instance;

/**
 *
 * @author PARHAM
 */
public class FeatureNode extends FeatureEntity {
    
    public final NodeEdgesContainer edges = new NodeEdgesContainer();
    
    public FeatureNode () {
        super ();
    }
    public FeatureNode (int nch) {
        super (nch);
    }
    public FeatureNode (double [] chs) {
        super (chs);
    }
    public FeatureNode (Instance inst) {
        super (inst);
    }
    public FeatureNode (int nch, ParametersContainer pc) {
        super (nch, pc);
    }
    public FeatureNode (double [] chs, ParametersContainer pc) {
        super (chs, pc);
    }
    public FeatureNode (Instance chs, ParametersContainer pc) {
        super (chs, pc);
    }
    public List<FeatureNode> neighbors () {
        return edges.neighbors (this);
    }
}
