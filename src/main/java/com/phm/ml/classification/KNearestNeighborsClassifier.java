
package com.phm.ml.classification;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;

/**
 * <p>
 * <b>Publication details:<br>
 * </b> <b>Authors:</b> Fix, F., Hodges, J. L.<br>
 * <b>Year:</b> 1951 <br>
 * <b>Title:</b> Discriminatory analysis, nonparametric discrimination:
 * Consistency properties <br>
 * <b>Published In:</b> USAF School of Aviation Medicine, Randolph Field, Texas
 * (Technical Report) <br>
 * <b>Abstract:</b> The discrimination problem (two population case) may be
 * defined as follows: e random variable Z, of observed value z, is distributed
 * over some space (say, p-dimensional) either according to distribution F, or
 * according to distribution G. The problem is to decide, on the basis of z,
 * which of the two distributions Z has. <br>
 * </p>
 * 
 * @author Parham Nooralishahi - PHM!
 * @email parham.nooralishahi@gmail.com
 */
public class KNearestNeighborsClassifier implements Classifier {

	protected Dataset dataspace;
	protected DistanceMeasure distanceMethod;
	protected int knum = 3;
	protected LinkedList<KNNRecord> distances = new LinkedList<>();

	public KNearestNeighborsClassifier(DistanceMeasure dm, int k) {
		distanceMethod = Objects.requireNonNull(dm);
		knum = k;
	}

	public KNearestNeighborsClassifier(DistanceMeasure dm) {
		this(dm, 3);
	}

	public KNearestNeighborsClassifier() {
		this(new EuclideanDistance(), 3);
	}

	public DistanceMeasure getDistanceMeasure() {
		return distanceMethod;
	}

	public int getK() {
		return knum;
	}

	@Override
	public void buildClassifier(Dataset dtst) {
		dataspace = dtst;
	}

	protected LinkedList<KNNRecord> calculateDistances(Instance df) {
		LinkedList<KNNRecord> list = new LinkedList<>();
		dataspace.stream().forEach((Instance d) -> {
			double dis = distanceMethod.measure(d, df);
			list.add(new KNNRecord(Math.abs(dis), d));
		});
		// Find the K nearest elements
		Collections.sort(list, new KNNComparitor());
		distances = new LinkedList<>();
		for (int k = 0; k < knum && k < list.size(); k++) {
			distances.add(list.removeFirst());
		}
		return distances;
	}

	@Override
	public synchronized Object classify(Instance df) {
		HashMap<Object, Double> res = new HashMap<>(classDistribution(df));
		Object maxl = null;
		double maxv = -1;
		LinkedList<Object> vs = new LinkedList<>(res.keySet());
		for (Object x : vs) {
			Double v = res.get(x);
			if (maxv < v) {
				maxl = x;
				maxv = v;
			}
		}
		return maxl;
	}

	@Override
	public synchronized Map<Object, Double> classDistribution(Instance df) {
		LinkedList<KNNRecord> knearest = new LinkedList<>(calculateDistances(df));
		// Create Classification Distribution
		HashMap<Object, Double> res = new HashMap<>();
		knearest.stream().forEach((KNNRecord x) -> {
			double value = 0;
			if (res.containsKey(x.data.classValue())) {
				value = res.get(x.data.classValue());
			}
			res.put(x.data.classValue(), value + 1);
		});
		return res;
	}

	protected class KNNRecord {
		public double distance = 0;
		public Instance data;

		public KNNRecord(double dis, Instance d) {
			distance = dis;
			data = d;
		}
	}

	protected class KNNComparitor implements Comparator<KNNRecord> {
		@Override
		public int compare(KNNRecord o1, KNNRecord o2) {
			double dis1 = o1.distance;
			double dis2 = o2.distance;
			double temp = dis1 - dis2;
			if (temp > 0) {
				return 1;
			} else if (temp < 0) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
