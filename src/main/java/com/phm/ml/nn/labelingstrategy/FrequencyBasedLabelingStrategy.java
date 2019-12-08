
package com.phm.ml.nn.labelingstrategy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class FrequencyBasedLabelingStrategy extends LabelingStrategy {

	public static final String NEURON_FREQLIST = "neuron.freqlist";

	@Override
	public void label(NeuronGroup parent, List<Neuron> winners, Instance current) {
		if (winners.size() > 0) {
			Object obj = winners.get(0).getParameter(NEURON_FREQLIST);
			HashMap<String, Integer> list;
			if (obj == null) {
				list = new HashMap<String, Integer>();
			} else {
				list = (HashMap<String, Integer>) obj;
			}
			String lblcurrent = (String) current.classValue();
			if (list.containsKey(lblcurrent)) {
				int tmp = list.get(lblcurrent);
				list.put(lblcurrent, tmp + 1);
			} else {
				list.put(lblcurrent, 1);
			}
			winners.get(0).setParameter(NEURON_FREQLIST, list);
			// Find the suitable label
			int max = -1;
			String maxl = null;
			LinkedList<String> tmp = new LinkedList<>(list.keySet());
			for (String l : tmp) {
				int tfreq = list.get(l);
				if (tfreq > max) {
					maxl = l;
					max = tfreq;
				}
			}
			if (maxl != null) {
				winners.get(0).setParameter(NEURON_LABEL, maxl);
			}
		}
	}

}
