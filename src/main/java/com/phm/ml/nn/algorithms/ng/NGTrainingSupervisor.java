
package com.phm.ml.nn.algorithms.ng;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.phm.ml.ParametersContainer;
import com.phm.ml.distance.DefaultDistanceMethod;
import com.phm.ml.distance.DistanceInfo;
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronComparePolicy;
import com.phm.ml.nn.NeuronDistanceMethod;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.Supervisor;
import com.phm.ml.nn.container.NeuronsContainer;

import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;

/**
 *
 * @author phm
 */
public class NGTrainingSupervisor extends Supervisor {

	public static final String NEURON_DISTANCE = "neuron.distance";
	public static final String NEURON_DIS_DIMENSION = "neuron.local.distance";
	public static final String NEURON_ASSIGNED_VALUE = "assigned.value";

	public static final String NEURON_NUMBER = "neuron.num";
	public static final String SIGNALS_NUMBER = "signals.num";

	public static final String NG_EI = "ei";
	public static final String NG_EF = "ef";
	public static final String NG_LI = "li";
	public static final String NG_LF = "lf";

	public static final NeuronComparePolicy COMPARE_POLICY = new NeuronComparePolicy(NEURON_DISTANCE);

	protected final NeuronDistanceMethod distanceMethod;
	protected final int netDimension;
	protected final int numNeurons;
	protected final int numMaxSignals;
	// protected boolean init = false;

	public NGTrainingSupervisor(NeuronDistanceMethod dm, int dim, int nn, int ns) {
		netDimension = dim;
		numNeurons = nn;
		numMaxSignals = ns;
		distanceMethod = Objects.requireNonNull(dm);
	}

	public NGTrainingSupervisor(int dim, int nn, int ns) {
		this(new NeuronDistanceMethod(new DefaultDistanceMethod(new EuclideanDistance())), dim, nn, ns);
	}

	@Override
	public void initialize(Neuron ngroup) {
		ngroup.setParameter(NG_EI, 0.3f);
		ngroup.setParameter(NG_EF, 0.05f);
		ngroup.setParameter(NG_LI, 30.0f);
		ngroup.setParameter(NG_LF, 0.01f);

		ngroup.setParameter(SIGNALS_NUMBER, numMaxSignals);
		ngroup.setParameter(NEURON_NUMBER, numNeurons);
		ngroup.setParameter(Neuron.NEURON_DIMENSION, netDimension);
	}

	private void onDistanceCalculation(NeuronGroup ngroup, Instance signal) {
		List<Neuron> ns = ngroup.neurons.toList();
		ns.stream().parallel().forEach((Neuron x) -> {
			DistanceInfo dd = distanceMethod.distance(ngroup, x, signal);
			x.setParameter(NEURON_DISTANCE, (float) dd.distance);
			x.setParameter(NEURON_DIS_DIMENSION, dd.distancedim);
		});
	}

	private List<Neuron> sortAndUpdateNeurons(NeuronsContainer nc) {
		LinkedList<Neuron> ns = new LinkedList<>(nc.toList());
		Collections.sort(ns, COMPARE_POLICY);
		// Update Assigned Value in each neuron
		for (int index = 0; index < ns.size(); index++) {
			ns.get(index).setParameter(NEURON_ASSIGNED_VALUE, (float) index);
		}
		return ns;
	}

	public NeuronDistanceMethod getDistanceMethod() {
		return distanceMethod;
	}

	protected Neuron initializeNewNeuron(Neuron neuron) {
		neuron.setParameter(NEURON_DISTANCE, 0.0f);
		neuron.setParameter(NEURON_DIS_DIMENSION, new float[netDimension]);
		neuron.setParameter(NEURON_DISTANCE, 0.0f);
		neuron.setParameter(NEURON_ASSIGNED_VALUE, 0.0f);

		return neuron;
	}

	protected void beforeInitialPhase(NeuronGroup ngroup, Instance signal) {
		// Empty body
	}

	protected Neuron onInitialPhase(NeuronGroup ngroup, Instance signal) {
		Neuron n = initializeNewNeuron(new Neuron(signal));
		ngroup.addInternalNeuron(n);
		return n;
	}

	protected void afterInitialPhase(NeuronGroup ngroup, Instance signal, Neuron nn) {
		// Empty body
	}

	protected void afterDistanceCalculation(NeuronGroup ngroup, Instance signal) {
		// Empty body
	}

	protected List<Neuron> onNGUpdate(NeuronGroup ngroup, Instance signal) {
		final int numMaxSignal = (int) ngroup.getParameter(SIGNALS_NUMBER);
		final int numSignal = (int) ngroup.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
		final float ei = (float) ngroup.getParameter(NG_EI);
		final float ef = (float) ngroup.getParameter(NG_EF);
		final float li = (float) ngroup.getParameter(NG_LI);
		final float lf = (float) ngroup.getParameter(NG_LF);

		List<Neuron> sortedNeuron = sortAndUpdateNeurons(ngroup.neurons);
		// Update Neuron weights
		sortedNeuron.stream().parallel().forEach((Neuron n) -> {
			float k = (Float) n.getParameter(NEURON_ASSIGNED_VALUE);
			float e = (ei * ((float) Math.pow((ef / ei), (numSignal / numMaxSignal))));
			float l = li * ((float) Math.pow((lf / li), (numSignal / numMaxSignal)));
			float h = (float) Math.exp(-k / l);
			final int ndims = n.noAttributes();
			for (int index = 0; index < ndims; index++) {
				double value = n.value(index) + ((e * h) * (signal.value(index) - n.value(index)));
				n.put(index, value);
			}
		});
		return sortedNeuron;
	}

	protected void onNGLastStep(NeuronGroup ngroup, Instance signal, List<Neuron> result) {
		//
	}

	protected void onNGFinalization(NeuronGroup ngroup, Instance signal, List<Neuron> result) {
		// Empty body
	}

	@Override
	protected boolean superviseOperator(Neuron neuron, Instance s, List<Neuron> result) {
		// Initialize needed neurons in case there isn't enough
		// neuron available inside the network
		NeuronGroup ngroup = (NeuronGroup) neuron;
		// final int numNeurons = (int) ngroup.getParameter(NEURON_NUMBER);
		if (ngroup.neurons.size() <= numNeurons) {
			// Before Initial Phase
			beforeInitialPhase(ngroup, s);
			// On Initial Phase
			Neuron n = onInitialPhase(ngroup, s);
			result.add(n);
			// After Initial Phase
			afterInitialPhase(ngroup, s, n);
		} else {
			// Order all elements of A, according to their distance to e
			// i.e. find the sequence of indices (i_0,i_1,…,i_(N-1) ) such
			// that w_i0 is the reference vector closest to ?, w_i1 is the reference
			// Finding Distance
			onDistanceCalculation(ngroup, s);
			// After Distance Calculation
			afterDistanceCalculation(ngroup, s);
			// NG Update
			List<Neuron> sn = onNGUpdate(ngroup, s);
			result.addAll(sn);
			// After NG Update
			onNGLastStep(ngroup, s, sn);
		}
		onNGFinalization(ngroup, s, result);
		return true;
	}

	@Override
	public String getName() {
		return "ng.train";
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
		resc.setParameter(NGTrainingSupervisor.NEURON_DISTANCE,
				neuron.getParameter(NGTrainingSupervisor.NEURON_DISTANCE));
		resc.setParameter(NGTrainingSupervisor.NEURON_ASSIGNED_VALUE,
				neuron.getParameter(NGTrainingSupervisor.NEURON_ASSIGNED_VALUE));
		resc.setParameter(NGTrainingSupervisor.NEURON_DIS_DIMENSION,
				neuron.getParameter(NGTrainingSupervisor.NEURON_DIS_DIMENSION));
		resc.setParameter(NGTrainingSupervisor.NEURON_NUMBER, neuron.getParameter(NGTrainingSupervisor.NEURON_NUMBER));
		resc.setParameter(NGTrainingSupervisor.NG_EF, neuron.getParameter(NGTrainingSupervisor.NG_EF));
		resc.setParameter(NGTrainingSupervisor.NG_EI, neuron.getParameter(NGTrainingSupervisor.NG_EI));
		resc.setParameter(NGTrainingSupervisor.NG_LF, neuron.getParameter(NGTrainingSupervisor.NG_LF));
		resc.setParameter(NGTrainingSupervisor.NG_LI, neuron.getParameter(NGTrainingSupervisor.NG_LI));
		resc.setParameter(NGTrainingSupervisor.SIGNALS_NUMBER,
				neuron.getParameter(NGTrainingSupervisor.SIGNALS_NUMBER));

		return resc;
	}
}
