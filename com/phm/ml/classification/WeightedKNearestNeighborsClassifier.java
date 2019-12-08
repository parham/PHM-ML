
package com.phm.ml.classification;

import com.phm.ml.ArraySet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;

/**
 * @author Parham Nooralishahi - PHM!
 * @email parham.nooralishahi@gmail.com
 */
public abstract class WeightedKNearestNeighborsClassifier implements Classifier {
    
    protected Dataset dataspace;
    protected DistanceMeasure distanceMethod;
    protected int knum = 3;
    protected LinkedList<KNNRecord> distances = new LinkedList<>();
    protected ArraySet<Object> classes = new ArraySet<>();
    
    public WeightedKNearestNeighborsClassifier (DistanceMeasure dm, int k) {
        distanceMethod = Objects.requireNonNull(dm);
        knum = k;
    }
    public WeightedKNearestNeighborsClassifier (DistanceMeasure dm) {
        this (dm, 3);
    }
    public WeightedKNearestNeighborsClassifier () {
        this (new EuclideanDistance(), 3);
    }
    
    public DistanceMeasure getDistanceMeasure () {
        return distanceMethod;
    }
    public int getK () {
        return knum;
    }
    public Set<Object> getClasses () {
        return classes;
    }
    
    protected void initClassDistribution () {
        classes = new ArraySet<>();
        // Extract classes
        dataspace.stream().forEach((x) -> {
            classes.add(x.classValue());
        });
        // Measure instance's class probability
        for (int index = 0; index < dataspace.size(); index++) {
            Instance d = dataspace.get(index);
            HashMap<Object, Double> cs = new HashMap<>();
            classes.stream().forEach((c) -> {
                cs.put(c, 0.0);
            });
            cs.put(d.classValue(), 1.0);
            d.setClassValue(cs);
        }
    }
    protected LinkedList<KNNRecord> calculateDistances (Instance df) {
        LinkedList<KNNRecord> list = new LinkedList<>();
        dataspace.stream().forEach((Instance d) -> {
            double dis = distanceMethod.measure (d, df);
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
    public synchronized Map<Object, Double> classDistribution (Instance df) {
        LinkedList<KNNRecord> knearest = new LinkedList<> (calculateDistances(df));
        HashMap<Instance, Double> wvf = new HashMap<>(weightedVotingFunction(knearest, df));
        // Create Classification Distribution
        HashMap<Object, Double> res = new HashMap<>();
        knearest.stream().forEach ((KNNRecord x) -> {
            HashMap<Object, Double> cp = (HashMap<Object, Double>) x.data.classValue();
            for (Object c : classes) {
                // Extract value of class
                double value = 0;
                if (cp.containsKey(c)) {
                    value = res.get(c);
                }
                value *= wvf.get(x.data);
                // Add value of classes
                double resv = 0;
                if (res.containsKey(c)) {
                    resv = res.get(c);
                }
                resv += value;
                res.put (c, resv);
            }
        });
        return res;
    }
    @Override
    public void buildClassifier(Dataset dtst) {
        dataspace = dtst;
        initClassDistribution();
    }
    @Override
    public synchronized Object classify (Instance df) {
        HashMap<Object, Double> res = new HashMap<>(classDistribution (df));
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
    
    protected abstract Map<Instance, Double> weightedVotingFunction (LinkedList<KNNRecord> ds,
                                                                     Instance d);
    
    protected class KNNRecord {
        public double distance = 0;
        public Instance data;
        
        public KNNRecord (double dis, Instance d) {
            distance = dis;
            data = d;
        }
    }
    protected class KNNComparitor implements Comparator<KNNRecord>  {
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
