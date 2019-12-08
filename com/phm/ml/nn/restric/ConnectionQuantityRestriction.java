
package com.phm.ml.nn.restric;

import com.phm.ml.nn.Neuron;

/**
 *
 * @author PARHAM
 */
public class ConnectionQuantityRestriction implements Restriction {
    public final int limit;
    
    public ConnectionQuantityRestriction (int l) {
        limit = l;
    }
    @Override
    public boolean fulfil(Neuron net) {
        Object tmp = net.getParameter(Neuron.NUM_CONNECTIONS);
        return tmp != null && (Integer) tmp >= limit;
    }
}
