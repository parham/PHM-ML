
package com.phm.ml.nn.algorithms.gng;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.phm.ml.ParametersContainer;
import com.phm.ml.distance.DistanceInfo;
import com.phm.ml.nn.DefaultNeuronComparePolicy;
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronDistanceMethod;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.Supervisor;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class GNGSimulateSupervisor extends Supervisor {

	public static final String NEURON_DISTANCE = "neuron.distance";
	public static final String NEURON_DIS_DIMENSION = "neuron.local.distance";
	public static final String NEURON_LOCALERROR = "neuron.local.error";

	protected NeuronDistanceMethod distanceMethod;

	public GNGSimulateSupervisor(NeuronDistanceMethod dm) {
		distanceMethod = Objects.requireNonNull(dm);
	}

	@Override
	public void initialize(Neuron neuron) {
		// Empty body
	}

	@Override
	protected boolean superviseOperator(Neuron neuron, Instance signal, List<Neuron> result) {
		NeuronGroup ngroup = (NeuronGroup) neuron;
		List<Neuron> neurons = ngroup.neurons.toList();
		onDistanceCalculation(ngroup, signal);
		LinkedList<Neuron> list = new LinkedList<>(ngroup.neurons.toList());
		Collections.sort(list, new DefaultNeuronComparePolicy());
		result.addAll(list);
		return true;
	}

	protected void onDistanceCalculation(NeuronGroup ngroup, Instance signal) {
		List<Neuron> ns = ngroup.neurons.toList();
		ns.stream().forEach((Neuron x) -> {
			DistanceInfo dd = distanceMethod.distance(ngroup, x, signal);
			x.setParameter(NEURON_DISTANCE, dd.distance);
			x.setParameter(NEURON_DIS_DIMENSION, dd.distancedim);
			// x.setParameter(NEURON_DIS_CHANNELS, dd.channeldim);
		});
	}

	@Override
	public String getName() {
		return "gng.simulate";
	}

	@Override
	protected NNResult prepareResult(Neuron neuron, Instance signal, List<Neuron> winners, NNResult resc) {
		resc.parameters = new ParametersContainer();
		resc.setParameter(Neuron.SYSTEM_STATUS, neuron.getParameter(Neuron.SYSTEM_STATUS));
		resc.setParameter(Neuron.NEURON_ID, neuron.getParameter(Neuron.NEURON_ID));
		resc.setParameter(Neuron.NEURON_CENTROID, neuron.getParameter(Neuron.NEURON_CENTROID));
		resc.setParameter(Neuron.NEURON_DIMENSION, neuron.getParameter(Neuron.NEURON_DIMENSION));
		resc.setParameter(Neuron.NEURON_NUM_CHANNEL, neuron.getParameter(Neuron.NEURON_NUM_CHANNEL));
		resc.setParameter(Neuron.NUM_CONNECTIONS, neuron.getParameter(Neuron.NUM_CONNECTIONS));
		resc.setParameter(Neuron.RECIEVED_SIGNALS_NUM, neuron.getParameter(Neuron.RECIEVED_SIGNALS_NUM));
		resc.setParameter(NeuronGroup.NUM_NEURONS, neuron.getParameter(NeuronGroup.NUM_NEURONS));
		resc.setParameter(GNGSimulateSupervisor.NEURON_DISTANCE,
				neuron.getParameter(GNGSimulateSupervisor.NEURON_DISTANCE));
		resc.setParameter(GNGSimulateSupervisor.NEURON_DIS_DIMENSION,
				neuron.getParameter(GNGSimulateSupervisor.NEURON_DIS_DIMENSION));
		resc.setParameter(GNGSimulateSupervisor.NEURON_LOCALERROR,
				neuron.getParameter(GNGSimulateSupervisor.NEURON_LOCALERROR));
		return resc;
	}
}
