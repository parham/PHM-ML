
package com.phm.ml.classification;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author phm
 */
public class ClassProbabilityDistribution extends HashMap<Object, Float> {
	public float getProbability(Object clss) {
		return getOrDefault(clss, 0.0f);
	}

	public Object getClassWithHighestProbability() {
		LinkedList<Object> keys = new LinkedList<>(this.keySet());
		Object maxl = keys.get(0);
		float maxv = getProbability(maxl);
		for (Object k : keys) {
			float tmp = getProbability(k);
			if (tmp > maxv) {
				maxl = k;
				maxv = tmp;
			}
		}
		return maxl;
	}

	public float getHighestProbability() {
		Object lbl = getClassWithHighestProbability();
		return getProbability(lbl);
	}
}
