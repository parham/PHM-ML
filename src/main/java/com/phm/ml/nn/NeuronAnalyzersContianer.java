
package com.phm.ml.nn;

import java.util.LinkedList;

import com.phm.ml.ParametersContainer;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class NeuronAnalyzersContianer extends LinkedList<NeuronAnalyzer> {
	public void analysis(String state, Neuron n, ParametersContainer param, Instance current,
			NNResultContainer result) {
		this.stream().forEach((NeuronAnalyzer x) -> x.analysis(state, n, param, current, result));
	}
}
