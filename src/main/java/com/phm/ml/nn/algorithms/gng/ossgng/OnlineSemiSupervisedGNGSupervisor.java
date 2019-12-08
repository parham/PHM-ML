package com.phm.ml.nn.algorithms.gng.ossgng;

import java.util.List;
import java.util.Objects;

import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronDistanceMethod;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.gng.GNGTrainingSupervisor;
import com.phm.ml.nn.labelingstrategy.LabelingStrategy;
import com.phm.ml.nn.predictionstrategy.PredictionStrategy;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class OnlineSemiSupervisedGNGSupervisor extends GNGTrainingSupervisor {

	public static final String OSSNEURON_LABEL = "neuron.label";

	protected final PredictionStrategy predictStrategy;
	protected final LabelingStrategy labelingStrategy;
	protected final String defaultLabel;

	public OnlineSemiSupervisedGNGSupervisor(NeuronDistanceMethod dm, PredictionStrategy ps, LabelingStrategy ls,
			String deflabel) {
		super(dm);
		predictStrategy = Objects.requireNonNull(ps);
		labelingStrategy = Objects.requireNonNull(ls);
		defaultLabel = Objects.requireNonNull(deflabel);
	}

	@Override
	protected Neuron initializeGNGNeuron(NeuronGroup ngroup, Neuron neuron) {
		super.initializeGNGNeuron(ngroup, neuron);
		neuron.setParameter(OSSNEURON_LABEL, defaultLabel);
		return neuron;
	}

	public PredictionStrategy getPredictionStrategy() {
		return predictStrategy;
	}

	public LabelingStrategy getLabelingStrategy() {
		return labelingStrategy;
	}

	@Override
	protected void afterWinnersFinding(NeuronGroup ngroup, List<Neuron> winners, Instance s) {
		predictStrategy.predict(ngroup, winners, s);
		labelingStrategy.label(ngroup, winners, s);
	}

	@Override
	protected NNResult prepareResult(Neuron neuron, Instance signal, List<Neuron> winners, NNResult resc) {
		super.prepareResult(neuron, signal, winners, resc);
		resc.setParameter(OnlineSemiSupervisedGNGSupervisor.OSSNEURON_LABEL,
				neuron.getParameter(OnlineSemiSupervisedGNGSupervisor.OSSNEURON_LABEL));
		return resc;
	}
}
