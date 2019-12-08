
package com.phm.ml.nn.container;
 
import com.phm.ml.nn.Neuron;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import net.sf.javaml.core.Dataset;

/**
 *
 * @author PARHAM 
 */
public class ListNeuronContainer extends NeuronsContainer {

    protected final LinkedList<Neuron> neurons = new LinkedList<>();
    
    @Override
    public Neuron getIndex (int index) {
        return neurons.get(index);
    }
    
    @Override
    public boolean add (Neuron n) {
        if (neurons.contains(n)) {
            neurons.remove(n);
        }
        neurons.add(n);
        return true;
    }

    @Override
    public boolean remove(Neuron n) {
        return neurons.remove(n);
    }

    @Override
    public List<Neuron> toList() {
        return neurons;
    }
    
    @Override
    public void clear() {
        neurons.clear();
    }

    @Override
    public Neuron getByID(int id) {
        for (Neuron x : neurons) {
            if (x.getID() == id) return x;
        }
        return null;
    }

    @Override
    public int size() {
        return neurons.size();
    }

    @Override
    public Iterator<Neuron> iterator() {
        return neurons.iterator();
    }

    @Override
    public void asDataSet(Dataset ds) {
        toList().stream().forEach((Neuron n) -> ds.add(n));
    }

    @Override
    public boolean contains(Neuron n) {
        return neurons.contains(n);
    }
}
