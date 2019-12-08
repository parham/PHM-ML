
package com.phm.ml.nn.algorithms.art.bayesian;

import java.util.LinkedList;
import java.util.List;

import com.phm.ml.nn.IneffectiveInputStrategy;
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.art.ARTTrainingSupervisor;
import com.phm.ml.nn.event.NeuronAddedEvent;

import Jama.Matrix;
import net.sf.javaml.core.Instance;

/**
 * <p>
 * <b>Publication details:<br>
 * </b> <b>Authors:</b> Boaz Vigdor and Boaz Lerner <br>
 * <b>Year:</b> 2007 <br>
 * <b>Title:</b> The Bayesian ARTMAP <br>
 * <b>Published In:</b> IEEE Transactions on Neural Networks 18(6) <br>
 * <b>Page:</b> 1628 - 1644 <br>
 * <b>DOI:</b> 10.1109/TNN.2007.900234 <br>
 * </p>
 * 
 * @author Parham Nooralishahi - PHM!
 * @email parham.nooralishahi@gmail.com
 */
public class BayesianARTTrainingSupervisor extends ARTTrainingSupervisor {

	public static final String BAYESIANART_MAX_HYPERVOLUME = "bayesianart.max.hypervolume";
	public static final String BAYESIANART_VARIANCE_CONST_INITIALIZER = "bayesianart.variance.const.initializer";
	public static final String BAYESIANART_P_WJ_X = "bayesianart.p.wj.x";

	public static final String NEURON_VARIANCE = "neuron.variance";
	public static final String NEURON_P_X_WJ = "neuron.p.x.wj";
	public static final String NEURON_P_WJ = "neuron.p.wj";
	public static final String NEURON_CODEDSIGNAL = "neuron.coded.signals";

	protected final int netDimension;

	public BayesianARTTrainingSupervisor(int dim) {
		netDimension = dim;
	}

	@Override
	public String getName() {
		return "bayesianart.train";
	}

	@Override
	public void initialize(Neuron ngroup) {
		ngroup.setParameter(BAYESIANART_MAX_HYPERVOLUME, 1.0f);
		ngroup.setParameter(BAYESIANART_VARIANCE_CONST_INITIALIZER, 0.001f);
		ngroup.setInputStrategy(new IneffectiveInputStrategy());
	}

	protected Neuron initializeBayesianARTNeuron(NeuronGroup ngroup, Neuron n) {
		n.setParameter(NEURON_VARIANCE, new Matrix(netDimension, netDimension));
		n.setParameter(NEURON_P_WJ, 0.0f);
		n.setParameter(NEURON_P_X_WJ, 0.0f);
		n.setParameter(NEURON_CODEDSIGNAL, 1.0f);
		n.setParameter(NEURON_ACTIVATION_VALUE, 0.0f);
		n.setParameter(NEURON_MATCHVALUE, 0.0f);

		return n;
	}

	@Override
	protected void beforeCalculateActivationValues(NeuronGroup ngroup, Instance signal) {
		LinkedList<Neuron> ns = new LinkedList<>(ngroup.neurons.toList());
		float p_tot = 0.0f;
		for (Neuron x : ns) {
			float p_x_wj = P_x_wj(ngroup, x, signal);
			float p_wj = P_wj(ngroup, x);
			x.setParameter(NEURON_P_X_WJ, p_x_wj);
			x.setParameter(NEURON_P_WJ, p_wj);
			p_tot += (p_x_wj * p_wj);
		}
		ngroup.setParameter(BAYESIANART_P_WJ_X, (float) p_tot);
	}

	@Override
	protected boolean doesResonanceOccured(NeuronGroup ngroup, List<Neuron> neuron, Instance signal) {
		float sMAX = (Float) ngroup.getParameter(BAYESIANART_MAX_HYPERVOLUME);
		double sj = match(ngroup, neuron.get(0), signal);
		boolean res = sj < sMAX;
		ngroup.setParameter(ART_RESONANCE_OCCURED, res);
		return res;
	}

	@Override
	protected Neuron insert(NeuronGroup ngroup, Instance signal) {
		// Initialize new neuron
		// mu
		// variance
		float sMAX = (float) ngroup.getParameter(BAYESIANART_MAX_HYPERVOLUME);
		float lambda = (float) ngroup.getParameter(BAYESIANART_VARIANCE_CONST_INITIALIZER);
		int M = signal.noAttributes();
		Matrix variance = Matrix.identity(M, M);
		variance.timesEquals(lambda * Math.pow(sMAX, (1 / (float) M)));
		/////////////////////////
		Neuron newNeuron = initializeBayesianARTNeuron(ngroup, new Neuron(signal));
		newNeuron.setParameter(NEURON_VARIANCE, variance);

		ngroup.addInternalNeuron(newNeuron);
		ngroup.listeners.event(new NeuronAddedEvent(newNeuron));
		return newNeuron;
	}

	@Override
	protected NNResult prepareResult(Neuron neuron, Instance signal, List<Neuron> winners, NNResult resc) {
		return new NNResult(System.currentTimeMillis(), neuron.getParametersContainer(), winners);
	}

	protected float P_x_wj(NeuronGroup parent, Neuron n, Instance signal) {
		final int dim = signal.noAttributes();
		final Matrix var = (Matrix) n.getParameter(NEURON_VARIANCE);
		// debuging
		double vdet = var.det();
		double tmpv1 = Math.sqrt(vdet);
		double tmpv2 = (tmpv1 * Math.pow(Math.sqrt(2 * Math.PI), dim));
		double part1 = 1.0f / tmpv2;
		/////////////////
		// float [] centroid = (float []) n.centroid.cdata(0);
		double[] dis = new double[n.noAttributes()];
		for (int index = 0; index < dis.length; index++) {
			dis[index] = signal.value(index) - n.value(index);
		}
		Matrix dism = new Matrix(dis, 1);
		Matrix invm = var.inverse();
		invm = invm.times(dism.transpose());
		Matrix tmp = dism.times(invm);
		Matrix tmp2 = tmp.times(-0.5f);
		double part2 = Math.exp(tmp2.get(0, 0));
		float res = (float) (part1 * part2);
		return res;
	}

	protected float P_wj(NeuronGroup parent, Neuron n) {
		float nj = (float) n.getParameter(NEURON_CODEDSIGNAL);
		int ntot = (int) parent.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
		float res = nj / ntot;
		return res;
	}

	protected float P_wj_x(NeuronGroup parent, Neuron n, Instance signal) {
		float p_x_wj = (float) n.getParameter(NEURON_P_X_WJ);
		float p_wj = (float) n.getParameter(NEURON_P_WJ);
		float part1 = p_x_wj * p_wj;
		float part2 = (float) parent.getParameter(BAYESIANART_P_WJ_X);
		float res = part1 / part2;
		return res;
	}

	protected double calculatePwjbyx(NeuronGroup parent, Neuron n, Instance signal) {
		double p_x_wj = P_x_wj(parent, n, signal);
		float p_wj = P_wj(parent, n);
		double part1 = p_x_wj * p_wj;
		LinkedList<Neuron> ns = new LinkedList<>(parent.neurons.toList());
		double part2 = 0;
		for (Neuron nx : ns) {
			double tp_xwj = P_x_wj(parent, nx, signal);
			float tp_wj = P_wj(parent, nx);
			part2 += (tp_wj * tp_xwj);
		}
		double res = part1 / part2;
		return res;
	}

	@Override
	protected double activate(NeuronGroup ngroup, Neuron neuron, Instance signal) {
		double tmp = P_wj_x(ngroup, neuron, signal);
		return (float) tmp;
	}

	@Override
	protected double match(NeuronGroup ngroup, Neuron neuron, Instance signal) {
		Matrix covar = (Matrix) neuron.getParameter(NEURON_VARIANCE);
		float mv = (float) (covar.det());
		// System.out.println (mv);
		neuron.setParameter(NEURON_MATCHVALUE, mv);
		return mv;
	}

	@Override
	protected void update(NeuronGroup ngroup, List<Neuron> neuron, Instance signal) {
		Neuron n = neuron.get(0);
		float nj = (Float) n.getParameter(NEURON_CODEDSIGNAL);
		Matrix var = (Matrix) n.getParameter(NEURON_VARIANCE);
		float ca = 1 / (nj + 1);
		float ca2 = nj * ca;
		final int ndims = signal.noAttributes();
		// Update mean vector
		double[] vx_mu = new double[ndims];
		double[] disdim = new double[ndims];
		for (int dim = 0; dim < ndims; dim++) {
			vx_mu[dim] = (ca2 * signal.value(dim)) - (ca * n.value(dim));
			disdim[dim] = signal.value(dim) - n.value(dim);
			n.put(dim, vx_mu[dim]);
		}
		// Update variance matrix
		Matrix x_mu = new Matrix(disdim, 1);
		x_mu = x_mu.transpose().times(x_mu);
		x_mu = x_mu.arrayTimes(Matrix.identity(x_mu.getRowDimension(), x_mu.getColumnDimension()));
		x_mu.times(ca);
		Matrix dvar = var.times(ca2);
		// var.plusEquals(var.minus(x_mu.times(x_mu.transpose())).times(ca));
//            x_mu = x_mu.transpose().times(x_mu);
//            x_mu = x_mu.arrayTimes(Matrix.identity(x_mu.getRowDimension(), x_mu.getColumnDimension()));
//            Matrix dvar = var.minus(x_mu).times(ca);
		var = dvar.plus(x_mu);
		n.setParameter(NEURON_VARIANCE, var);
		n.setParameter(NEURON_CODEDSIGNAL, nj + 1);
	}
}
