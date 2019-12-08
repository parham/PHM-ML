
package com.phm.ml.nn.predictionstrategy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.gng.ossgng.OnlineSemiSupervisedGNGSupervisor;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class CompleteLinkagePredictionStrategy extends PredictionStrategy {

	@Override
	public void predict(NeuronGroup net, List<Neuron> winners, Instance signal) {
		if (signal == null) {
			List<Neuron> ns = net.neurons.toList();
			HashMap<String, PNRecord> table = new HashMap<>();
			// Calculate essentials
			ns.stream().forEach((Neuron n) -> {
				String lt = (String) n.getParameter(NEURON_LABEL);
				float dis = (float) n.getParameter(OnlineSemiSupervisedGNGSupervisor.NEURON_DISTANCE);
				// FMCHDistanceInfo dd = disMethod.distance(net, n, signal);
				// float dis = dd.distance;
				if (!table.containsKey(lt)) {
					table.put(lt, new PNRecord(lt, dis));
				} else {
					PNRecord tmp = table.get(lt);
					tmp.distance = Math.max(tmp.distance, dis);
					table.put(lt, tmp);
				}
			});
			// Find the best
			LinkedList<PNRecord> list = new LinkedList<>(table.values());
			PNRecord min = list.get(0);
			for (PNRecord p : list) {
				if (p.distance < min.distance) {
					min = p;
				}
			}
			// Assign label
			signal.setClassValue(min.label);
		}
	}

	protected class PNRecord {
		String label;
		float distance = 0.0f;

		public PNRecord(String l, float dis) {
			label = l;
			distance = dis;
		}
	}
}
