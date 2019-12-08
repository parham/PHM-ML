
package com.phm.ml.triangulation;

import java.util.Objects;

import org.jgrapht.Graph;

import com.phm.ml.ParametersContainer;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.Supervisor;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.siggen.NeuronSignalGenerator;
import com.phm.ml.siggen.ListedSignalGenerator;

import net.sf.javaml.core.Dataset;

/**
 *
 * @author phm
 */
public class NeuralNetworkTriangulator implements Triangulator<Neuron, Connection> {

	protected Supervisor nnOperator;
	protected ParametersContainer params;

	public NeuralNetworkTriangulator(Supervisor s, ParametersContainer pc) {
		nnOperator = Objects.requireNonNull(s);
		params = Objects.requireNonNull(pc);
	}

	public NeuralNetworkTriangulator(Supervisor s) {
		this(s, new ParametersContainer());
	}

	@Override
	public Graph<Neuron, Connection> triangulate(Dataset ds) {
		int ndims = ds.get(0).noAttributes();
		NeuronGroup group = new NeuronGroup(new double[ndims]);
		group.supervisors.add(nnOperator);
//        group.analyzers.add((NeuronAnalyzer) (String state, Neuron n,
//                        ParametersContainer param, Instance current,
//                        NNResultContainer result) -> {
//            System.out.println (param.get(Neuron.RECIEVED_SIGNALS_NUM) + " --> " + group.neurons.size());
//        });
		nnOperator.initialize(group);
		group.setParameters(params);
		NeuronSignalGenerator sg = new NeuronSignalGenerator(
				new ListedSignalGenerator(ds, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM));
		NNResultContainer result = new NNResultContainer();
		sg.signalAll(group, nnOperator.getName(), result);
		return group;
	}
}