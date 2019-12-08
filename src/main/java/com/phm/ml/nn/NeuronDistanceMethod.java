
package com.phm.ml.nn;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.phm.ml.distance.DistanceInfo;
import com.phm.ml.distance.DistanceMethod;

import net.sf.javaml.core.Instance;

/**
 *
 * @author PARHAM
 */
public class NeuronDistanceMethod {

	public DistanceMethod distanceMethod;

	public NeuronDistanceMethod(DistanceMethod dm) {
		distanceMethod = Objects.requireNonNull(dm);
	}

	public DistanceInfo distance(NeuronGroup parent, Neuron neuron, Instance signal) {
		return (DistanceInfo) distanceMethod.distance(neuron.getCentroid(), signal, parent.getParametersContainer());
	}

	public DistanceInfo distance(NeuronGroup parent, Neuron nOne, Neuron nTwo) {
		return (DistanceInfo) distanceMethod.distance(nOne.getCentroid(), nTwo.getCentroid(),
				parent.getParametersContainer());
	}

	public List<DistanceInfo> distance(NeuronGroup parent, List<Neuron> arr, Instance signal) {
		LinkedList<DistanceInfo> res = new LinkedList<>();
		arr.stream().forEach((arr1) -> {
			res.add(distance(parent, arr1, signal));
		});
		return res;
	}
}