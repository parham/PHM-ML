
package com.phm.ml.nn.container;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.phm.ml.nn.Neuron;

import net.sf.javaml.core.Dataset;

/**
 *
 * @author PARHAM
 */
public class HashMapNeuronContainer extends NeuronsContainer {

	protected final HashMap<Integer, Neuron> neurons;

	public HashMapNeuronContainer() {
		neurons = new HashMap<>();
	}

	@Override
	public boolean add(Neuron n) {
		neurons.put(n.getID(), n);
		return true;
	}

	@Override
	public int size() {
		return neurons.size();
	}

	@Override
	public void clear() {
		neurons.clear();
	}

	@Override
	public Neuron getByID(int id) {
		return neurons.get(id);
	}

	@Override
	public boolean remove(Neuron n) {
		return neurons.remove(n.getID()) != null;
	}

	@Override
	public List<Neuron> toList() {
		return new LinkedList<>(neurons.values());
	}

	@Override
	public Iterator<Neuron> iterator() {
		return toList().iterator();
	}

	@Override
	public void asDataSet(Dataset ds) {
		toList().stream().forEach((Neuron n) -> ds.add(n));
	}

	@Override
	public boolean contains(Neuron n) {
		return neurons.containsKey(n.getID());
	}
}
