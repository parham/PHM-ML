
package com.phm.ml.nn.connection;

import com.phm.ml.ParametersContainer;
import com.phm.ml.nn.Neuron;
import org.jgrapht.EdgeFactory;

/**
 *
 * @author phm
 */
public class ConnectionFactory implements EdgeFactory<Neuron, Connection> {
    @Override
    public Connection createEdge(Neuron source, Neuron target) {
        return new Connection(source, target);
    }
    public Connection createEdge (Neuron source, Neuron target, ParametersContainer pc) {
        return new Connection (source, target, pc);
    }
}
