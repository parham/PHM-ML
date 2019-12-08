
package com.phm.ml.nn.labelingstrategy;

import java.util.List;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.gng.ossgng.OnlineSemiSupervisedGNGSupervisor;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 * @param <DFType>
 */
public class LimitedDistanceLabelingStrategy extends LabelingStrategy {

	public static final String NEURON_BESTDIS = "neuron.bestdis";

	@Override
	public void label(NeuronGroup parent, List<Neuron> winners, Instance current) {
		if (winners.size() > 0) {
			Object obj = winners.get(0).getParameter(NEURON_BESTDIS);
			float bestdis;
//            FMCHDistanceInfo dd = distanceMethod.distance(parent, winners[0], current);
//            float dis = dd.distance;
			float dis = (float) winners.get(0).getParameter(OnlineSemiSupervisedGNGSupervisor.NEURON_DISTANCE);
			String lblcurrent = (String) current.classValue();
			if (obj == null) {
				winners.get(0).setParameter(NEURON_BESTDIS, dis);
				winners.get(0).setParameter(NEURON_LABEL, lblcurrent);
			} else {
				bestdis = (Float) obj;
				if (bestdis > dis) {
					winners.get(0).setParameter(NEURON_BESTDIS, dis);
					winners.get(0).setParameter(NEURON_LABEL, lblcurrent);
				}
			}
		}
	}

}
