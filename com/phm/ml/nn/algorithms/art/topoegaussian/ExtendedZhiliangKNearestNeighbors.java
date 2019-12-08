
package com.phm.ml.nn.algorithms.art.topoegaussian;

import com.phm.ml.classification.WeightedKNearestNeighborsClassifier;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.algorithms.art.gaussian.GaussianARTTrainingSupervisor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;
import org.apache.commons.math3.distribution.MultivariateNormalDistribution;

/**
 * Publication details: (Modified version by Parham Nooralishahi for Topological GARTMAP)
 * Authors: Zhiliang, L., Xiaomin, Z., Jianxiao, Z. & Hongbing, X.
 * Year: 2013
 * Title: A Semi-Supervised Approach Based on k-Nearest Neighbor.
 * Published In: Journal Of Software, VOL. 8, NO. 4
 * Page: 768 – 775
 * @author Parham Nooralishahi - PHM!
 * @email parham.nooralishahi@gmail.com
 */
public class ExtendedZhiliangKNearestNeighbors extends WeightedKNearestNeighborsClassifier {
    
    protected double cfMin = 0.0;
    
    public ExtendedZhiliangKNearestNeighbors (DistanceMeasure dm, int k, double cmin) {
        distanceMethod = Objects.requireNonNull(dm);
        knum = k;
        cfMin = cmin;
    }
    public ExtendedZhiliangKNearestNeighbors (DistanceMeasure dm, double cmin) {
        this (dm, 3, cmin);
    }
    public ExtendedZhiliangKNearestNeighbors () {
        this (new EuclideanDistance(), 3, 0.0);
    }
    @Override
    public synchronized Map<Object, Double> classDistribution(Instance df) {
        LinkedList<KNNRecord> knearest = new LinkedList<> (calculateDistances (df));
        HashMap<Instance, Double> wvf = new HashMap<>(weightedVotingFunction(knearest, df));
        HashMap<Object, Double> res = new HashMap<>();
        
        float totsig = 0.0f;
        for (KNNRecord tx : knearest) {
            Neuron n = (Neuron) tx.data;
            HashMap<Object, Double> cp = (HashMap<Object, Double>) n.getParameter(TopoEGARTTrainingSupervisor.NEURON_OCCURENCE_VECTOR);
            totsig += (float) n.getParameter (TopoEGARTTrainingSupervisor.NEURON_CODEDSIGNAL);
            for (Object c : classes) {
                double value = cp.getOrDefault(c, 0.0) * wvf.get(n);
                res.put (c, res.getOrDefault(c, 0.0) + value);
            }
        }
        // Normalization
        for (Object k : res.keySet()) {
            res.put(k, res.get(k) / totsig);
        }
        return res;
    }
    @Override
    protected Map<Instance, Double> weightedVotingFunction (LinkedList<KNNRecord> ds, Instance d) {
        HashMap<Instance, Double> vote = calculateNormalDistriution(ds, d);
        // Sum of all values;
        double sumv = 0.0;
        for (KNNRecord x : ds) {
            //Neuron n = (Neuron) x.data;
            double v = Math.exp(-1 * vote.get(x.data));
            sumv += v;
            vote.put(x.data, v);
        }
        // Calculate Weighted votes
        for (KNNRecord x : ds) {
            Neuron n = (Neuron) x.data;
            double v = vote.get(n);
            vote.put(n, v / sumv);
        }
        return vote;
    }
    protected HashMap<Instance, Double> calculateNormalDistriution (LinkedList<KNNRecord> ds, Instance d) {
        HashMap<Instance, Double> vote = new HashMap<>();
        for (KNNRecord x : ds) {
            Neuron n = (Neuron) x.data;
            double [] mean = new double [n.noAttributes()];
            for (int dim = 0; dim < mean.length; dim++) {
                mean [dim] = n.value(dim);
            }
            double [] cv = (double []) n.getParameter(GaussianARTTrainingSupervisor.NEURON_STD);
            double [][] cov = new double [n.noAttributes()][n.noAttributes()];
            for (int dim = 0; dim < n.noAttributes(); dim++) {
                cov[dim][dim] = cv[dim];
            }
            MultivariateNormalDistribution mnd = new MultivariateNormalDistribution(mean, cov);
            double [] dv = new double [d.noAttributes()];
            for (int dim = 0; dim < d.noAttributes(); dim++) {
                dv [dim] = d.value(dim);
            }
            double res = mnd.density(dv);
            vote.put(x.data, res);
        }
        return vote;
    }
    protected boolean validate (LinkedList<KNNRecord> ds, 
                                HashMap<Object, Double> cd, 
                                Object cls) {
        double res = 0.0;
        double factor = 0.0;
        double cv = cd.get(cls);
        ////////////////////////
        for (KNNRecord x : ds) {
            Neuron n = (Neuron) (x.data);
            HashMap<Object, Double> ncd = (HashMap<Object, Double>) n.getParameter(TopoEGARTTrainingSupervisor.NEURON_OCCURENCE_VECTOR);
            factor += x.distance;
            double v = 0;
            if (ncd.containsKey(cls)) {
                v = (double) ncd.get(cls);
            }
            res += x.distance * v;
        }
        res /= factor;
        return res >= cfMin;
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
        return maxl != null && validate(distances, res, maxl) ? res : null;
    }
}
