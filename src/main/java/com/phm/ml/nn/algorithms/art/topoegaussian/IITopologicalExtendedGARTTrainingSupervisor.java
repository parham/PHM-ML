package com.phm.ml.nn.algorithms.art.topoegaussian;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.linear.SingularMatrixException;
import org.jgrapht.Graph;

import com.phm.ml.ArraySet;
import com.phm.ml.clusterer.graphbased.GraphBasedClusterer;
import com.phm.ml.clusterer.graphbased.Xia2008Clusterer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.art.egaussian.DefaultLRC;
import com.phm.ml.nn.algorithms.art.egaussian.EGARTLearningRateCalculator;
import com.phm.ml.nn.algorithms.art.egaussian.ExtendedGARTTrainingSupervisor;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.connection.ConnectionFactory;
import com.phm.ml.nn.container.ListNeuronContainer;
import com.phm.ml.triangulation.HebbianCompetitiveTriangulator;

import Jama.Matrix;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;

/**
 *
 * @author phm
 */
public class IITopologicalExtendedGARTTrainingSupervisor extends ExtendedGARTTrainingSupervisor {

	public static final String DEFAULT_CLASS = "null";

	public static final String TGART_CLASS_TEMPLATES = "tgart.class.template";
	public static final String TGART_CLUSTERS = "tgart.clusters";
	public static final String TGART_INITIAL_SIGNALCOUNT = "tgart.initial.signalcount";
	public static final String TGART_CF_MIN = "tgart.cf.min";
	public static final String TGART_CORRESPONDENT_THRESH = "tgart.corres.thresh";
	public static final String TGART_DELTA = "tgart.delta";

	public static final String NEURON_OCCURENCE_VECTOR = "neuron.occurence.vector";
	public static final String NEURON_POSSIBLE_CLASS = "neuron.possible.class";
	public static final String NEURON_P_CAT_AND_SIGNAL = "neuron.pcatsig";
	public static final String NEURON_CORRESPONDENT_VALUE = "neuron.corres.value";

	protected DistanceMeasure disMethod;
	protected GraphBasedClusterer<Neuron, Connection> clusterer;
	private boolean doesSignalHaveLabel = false;

	public IITopologicalExtendedGARTTrainingSupervisor(EGARTLearningRateCalculator lp, DistanceMeasure dm,
			GraphBasedClusterer<Neuron, Connection> clust) {
		super(lp);
		disMethod = Objects.requireNonNull(dm);
		clusterer = Objects.requireNonNull(clust);
	}

	public IITopologicalExtendedGARTTrainingSupervisor(EGARTLearningRateCalculator lp, DistanceMeasure dm) {
		this(lp, dm,
				new GraphBasedClusterer<Neuron, Connection>(
						new HebbianCompetitiveTriangulator(new ConnectionFactory(), new EuclideanDistance()),
						new Xia2008Clusterer(new ConnectionFactory())));
	}

	public IITopologicalExtendedGARTTrainingSupervisor(EGARTLearningRateCalculator lp) {
		this(lp, new EuclideanDistance());

	}

	public IITopologicalExtendedGARTTrainingSupervisor() {
		this(new DefaultLRC());
	}

	@Override
	public String getName() {
		return "topoegart.train";
	}

	@Override
	public void initialize(Neuron ngroup) {
		super.initialize(ngroup);
		ngroup.setParameter(TGART_CLASS_TEMPLATES, new ArraySet<String>());
		ngroup.setParameter(TGART_CLUSTERS, new ListNeuronContainer());
		ngroup.setParameter(TGART_INITIAL_SIGNALCOUNT, 5);
		ngroup.setParameter(TGART_CF_MIN, 0.00001f);
		ngroup.setParameter(TGART_CORRESPONDENT_THRESH, 0.000001f);
		ngroup.setParameter(TGART_DELTA, 30);
	}

	@Override
	protected Neuron initializeGaussianARTNeuron(NeuronGroup ngroup, Neuron n) {
		super.initializeGaussianARTNeuron(ngroup, n);
		n.setParameter(NEURON_POSSIBLE_CLASS, DEFAULT_CLASS);
		n.setParameter(NEURON_OCCURENCE_VECTOR, new HashMap<String, Double>());
		n.setParameter(NEURON_P_CAT_AND_SIGNAL, 0.0f);
		n.setParameter(NEURON_CORRESPONDENT_VALUE, 0.0f);
		return n;
	}

	@Override
	protected boolean superviseOperator(Neuron neuron, Instance signal, List<Neuron> result) {
		NeuronGroup ngroup = (NeuronGroup) neuron;
		List<Neuron> neurons = ngroup.neurons.toList();
		boolean insertFlag = true;

		if (initialClassPrediction(ngroup, signal)) {
			// Calculation Activation value for all templates
			beforeCalculateActivationValues(ngroup, signal);
			onCalculateActivationValues(ngroup, signal);
			afterCalculateActivationValues(ngroup, signal);

			for (Neuron neuron1 : neurons) {
				List<Neuron> highActives = findHighestActivatedNeurons(ngroup);
				if (doesResonanceOccured(ngroup, highActives, signal)) {
					beforeResonanceOccured(ngroup, highActives, signal);
					onResonanceOccured(ngroup, highActives, signal);
					afterResonanceOccured(ngroup, highActives, signal);
					result.addAll(highActives);
					insertFlag = false;
					break;
				} else {
					onResonanceDoesntOccured(ngroup, highActives, signal);
				}
			}
			// Insertation procedure
			if (insertFlag) {
				Neuron nn = insert(ngroup, signal);
				result.add(nn);
			}

			onLastStep(ngroup, signal);
			onFinalization(ngroup, signal);
			return true;
		}

		return false;
	}

//    @Override
//    protected void onFinalization (NeuronGroup ngroup, Instance signal) {
//        HashMap<Object, Double> c = (HashMap<Object, Double>) signal.classValue();
//        for (Object k : c.keySet()) {
//            System.out.print (k.toString() + " " + c.get(k) + " : ");
//        }
//        System.out.print ("\n");
//        
//    }
	protected void removeNotCorrespondentNeurons(NeuronGroup ngroup, Instance signal) {
		int numsig = (int) ngroup.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
		float corthresh = (float) ngroup.getParameter(TGART_CORRESPONDENT_THRESH);
		LinkedList<Neuron> ns = new LinkedList<>(ngroup.neurons);
		// Determine correspondent neurons
		for (Neuron n : ns) {
			float ncoded = (float) n.getParameter(NEURON_CODEDSIGNAL);
			float corm = (float) ncoded / (float) numsig;
			n.setParameter(NEURON_CORRESPONDENT_VALUE, corm);
			if (corthresh > corm) {
				ngroup.removeInternalNeuron(n);
			}
		}
	}

	protected void updateClusteringLayer(NeuronGroup ngroup, Instance signal) {
		ListNeuronContainer ns = (ListNeuronContainer) ngroup.getParameter(TGART_CLUSTERS);
		ns.clear();

		DefaultDataset ds = new DefaultDataset();
		ngroup.neurons.asDataSet(ds);
		List<Graph<Neuron, Connection>> gs = clusterer.clusterAsGraph(ds);
		for (Graph<Neuron, Connection> g : gs) {
			NeuronGroup ng = new NeuronGroup(new double[signal.noAttributes()]);
			ng.initialize(g);
			int nnum = g.vertexSet().size();
			// Calculate mean
			for (Neuron n : ng.neurons) {
				for (int dim = 0; dim < n.noAttributes(); dim++) {
					ng.put(dim, ng.value(dim) + (n.value(dim) / nnum));
				}
			}
			// Calculate std
			Matrix sigma = new Matrix(signal.noAttributes(), signal.noAttributes());
			for (Neuron n : ng.neurons) {
				Matrix nm = n.asMatrix();
				Matrix sm = asMatrix(signal);
				Matrix res = sm.minus(nm);
				res = res.times(res.transpose());
				res = res.arrayTimes(Matrix.identity(signal.noAttributes(), signal.noAttributes()));
				sigma.plusEquals(res);
			}
			sigma.timesEquals(1 / (float) nnum);
			double[] sig = new double[signal.noAttributes()];
			for (int dim = 0; dim < sig.length; dim++) {
				sig[dim] = sigma.get(dim, dim);
			}
			ng.setParameter(NEURON_STD, sig);
			// Calculate number of encoded signals
			int totnum = 0;
			for (Neuron n : ng.neurons) {
				float num = (float) n.getParameter(NEURON_CODEDSIGNAL);
				totnum += num;
			}
			ng.setParameter(NEURON_CODEDSIGNAL, totnum);
			ns.add(ng);
		}
		ngroup.setParameter(TGART_CLUSTERS, ns);
	}

	public static Matrix asMatrix(Instance m) {
		Matrix mat = new Matrix(m.noAttributes(), 1);
		for (int dim = 0; dim < m.noAttributes(); dim++) {
			mat.set(dim, 0, m.value(dim));
		}
		return mat;
	}

	protected HashMap<Object, Double> finalClassPrediction(NeuronGroup ngroup, Instance signal) {
		ArraySet<Object> as = (ArraySet<Object>) ngroup.getParameter(TGART_CLASS_TEMPLATES);
		HashMap<Object, Double> cd = new HashMap<>();
		double totavg = 0;
		// double cavg = 0;
		for (Object c : as) {
			double sumavg = 0.0;
			for (Neuron n : ngroup.neurons) {
				double vpcw = pcw(ngroup, n, c);
				double vpxw = pxw(ngroup, n, signal);
				double vpw = (float) n.getParameter(NEURON_CODEDSIGNAL)
						/ (int) ngroup.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
				sumavg += (vpcw * vpxw * vpw);
			}
			totavg += sumavg;
			cd.put(c, sumavg);
		}
		for (Object c : as) {
			double sumavg = cd.get(c);
			cd.put(c, sumavg / totavg);
		}
		return cd;
	}

	protected double pcw(NeuronGroup ngroup, Neuron n, Object c) {
		HashMap<Object, Double> cv = (HashMap<Object, Double>) n.getParameter(NEURON_OCCURENCE_VECTOR);
		float numsig = (float) n.getParameter(NEURON_CODEDSIGNAL);
		double cvv = cv.containsKey(c) ? cv.get(c) : 0;
		return numsig != 0 ? cvv / numsig : 0;
	}

	protected double pxw(NeuronGroup ngroup, Neuron n, Instance signal) {
		double[] mean = new double[n.noAttributes()];
		for (int dim = 0; dim < n.noAttributes(); dim++) {
			mean[dim] = n.value(dim);
		}
		double[][] covar = new double[n.noAttributes()][n.noAttributes()];
		double[] std = (double[]) n.getParameter(NEURON_STD);
		for (int dim = 0; dim < n.noAttributes(); dim++) {
			covar[dim][dim] = std[dim];
		}
		MultivariateNormalDistribution mnd = new MultivariateNormalDistribution(mean, covar);
		double[] nsig = new double[n.noAttributes()];
		for (int dim = 0; dim < n.noAttributes(); dim++) {
			nsig[dim] = signal.value(dim);
		}
		return mnd.density(nsig);
	}

	@Override
	protected void onLastStep(NeuronGroup ngroup, Instance signal) {
		// Remove not correspondent neurons
		removeNotCorrespondentNeurons(ngroup, signal);
		// Update clustering layer
		updateClusteringLayer(ngroup, signal);
		// Final class prediction
		if (!doesSignalHaveLabel) {
			HashMap<Object, Double> fcp = (HashMap<Object, Double>) finalClassPrediction(ngroup, signal);
			signal.setClassValue(fcp);
		}
	}

	@Override
	protected void update(NeuronGroup ngroup, List<Neuron> neuron, Instance signal) {
		super.update(ngroup, neuron, signal);
		// Update occurence rate vector
		HashMap<Object, Double> clss = (HashMap<Object, Double>) signal.classValue();
		for (Neuron n : neuron) {
			HashMap<Object, Double> nclss = (HashMap<Object, Double>) n.getParameter(NEURON_OCCURENCE_VECTOR);
			for (Object c : clss.keySet()) {
				double vsig = clss.get(c);
				double nsig = 0;
				if (nclss.containsKey(c)) {
					nsig = nclss.get(c);
				}
				nclss.put(c, nsig + vsig);
			}
			n.setParameter(NEURON_OCCURENCE_VECTOR, nclss);
		}
	}

	protected boolean initialClassPrediction(NeuronGroup ngroup, Instance signal) {
		ArraySet<Object> clss = (ArraySet<Object>) ngroup.getParameter(TGART_CLASS_TEMPLATES);
		int numsig = (int) ngroup.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
		int initsigThresh = (int) ngroup.getParameter(TGART_INITIAL_SIGNALCOUNT);

		if (signal.classValue() == null || signal.classValue().equals(DEFAULT_CLASS)) {
			doesSignalHaveLabel = false;
			if (numsig > initsigThresh) {
				ListNeuronContainer nclusts = (ListNeuronContainer) ngroup.getParameter(TGART_CLUSTERS);
				// Measure sum of neurons
				double sumpxz = 0;
				for (Neuron n : nclusts) {
					double vnd = normDistribution(n, signal);
					float nsignum = (int) n.getParameter(NEURON_CODEDSIGNAL);
					double vw = nsignum / (float) numsig;
					n.setParameter(NEURON_P_CAT_AND_SIGNAL, vnd);
					n.setParameter(NEURON_PROBABILITY, vw);
					sumpxz += (vnd * vw);
				}
				Neuron winner = null;
				double wvalue = -1000000000;
				for (Neuron n : nclusts) {
					double vnd = (double) n.getParameter(NEURON_P_CAT_AND_SIGNAL);
					double vw = (double) n.getParameter(NEURON_PROBABILITY);
					double tmp = (vnd * vw) / sumpxz;
					if (tmp > wvalue) {
						wvalue = tmp;
						winner = n;
					}
				}
				System.out.println(winner.classValue());
				NeuronGroup clust = (NeuronGroup) winner;
				float cfmin = (float) ngroup.getParameter(TGART_CF_MIN);
				PHMEZKNN predictor = new PHMEZKNN(disMethod, clust.neurons.size(), cfmin, clss);
				DefaultDataset ds = new DefaultDataset();
				clust.neurons.asDataSet(ds);
				predictor.buildClassifier(ds);
				Object resobj = predictor.classify(signal);
				if (resobj == null)
					return false;
//                // Find possible class
//                Object maxc = null;
//                double maxv = 0.0;
//                HashMap<Object, Double> cdis = (HashMap<Object, Double>) resobj;
//                for (Object c : clss) {
//                    if (maxv < cdis.get(c)) {
//                        maxv = cdis.get(c);
//                        maxc = c;
//                    }
//                }
				signal.setClassValue(resobj);
				return true;
			} else {
				return false;
			}
		} else {
			doesSignalHaveLabel = true;
			String lbl = (String) signal.classValue();
			clss.add(lbl);
			ngroup.setParameter(TGART_CLASS_TEMPLATES, clss);
			HashMap<Object, Double> cdist = new HashMap<>();
			for (Object c : clss) {
				cdist.put(c, 0.0);
			}
			cdist.put(lbl, 1.0);
			signal.setClassValue(cdist);
			return true;
		}
	}

	protected double normDistribution(Neuron n, Instance d) {
		double[] mean = new double[n.noAttributes()];
		for (int dim = 0; dim < mean.length; dim++) {
			mean[dim] = n.value(dim);
		}
		double[] cv = (double[]) n.getParameter(IITopologicalExtendedGARTTrainingSupervisor.NEURON_STD);
		double[][] cov = new double[n.noAttributes()][n.noAttributes()];
		for (int dim = 0; dim < n.noAttributes(); dim++) {
			cov[dim][dim] = cv[dim];
		}
		try {
			MultivariateNormalDistribution mnd = new MultivariateNormalDistribution(mean, cov);
			double[] dv = new double[d.noAttributes()];
			for (int dim = 0; dim < d.noAttributes(); dim++) {
				dv[dim] = d.value(dim);
			}
			return mnd.density(dv);
		} catch (SingularMatrixException ex) {
			System.out.println("ERROR");
		}
		return 0.0;
	}
}
