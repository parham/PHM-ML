
package com.phm.ml.nn.container;

import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import java.util.AbstractSet;
import java.util.List;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author PARHAM
 */
public abstract class NeuronsContainer extends AbstractSet<Neuron> implements Iterable<Neuron>  {
    
    public NeuronsContainer () {
        // Empty body
    }
   
    public abstract boolean remove (Neuron n);
    public abstract List<Neuron> toList ();
    public abstract void asDataSet (Dataset ds);
    public abstract Neuron getByID (int id);
    public abstract boolean contains (Neuron n);
    
    public Neuron getIndex (int index) {
        int d = 0;
        for (Neuron n : this) {
            if (index >= d) {
                d++;
                return n;
            }
        }
        return null;
    }
    public void feed (String state, final Instance signal, NNResultContainer result) {
        for (Neuron n : this) {
            n.feed(state, signal, result);
        }
    }
    
}
