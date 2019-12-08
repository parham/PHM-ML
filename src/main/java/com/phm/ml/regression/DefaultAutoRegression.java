
package com.phm.ml.regression;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Jama.Matrix;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author Owner
 */
public class DefaultAutoRegression extends AutoRegression {

	protected int order = 5;

	public DefaultAutoRegression(int o) {
		order = o;
	}

	public DefaultAutoRegression() {
		// Empty Body
	}

	public void setOrder(int o) {
		order = o;
	}

	public int getOrder() {
		return order;
	}

	private double[] calculateLeastSquare(double[] inputseries, int order) throws Exception {

		int length = inputseries.length;

		double ar[] = null;
		double[] coef = new double[order];
		double[][] mat = new double[order][order];
		// create a symetric matrix of covariance values for the past timeseries
		// elements
		// and a vector with covariances between the past timeseries elements and the
		// timeseries element to estimate.
		// start at "degree"-th sampel and repeat this for the length of the timeseries
		for (int i = order - 1; i < length - 1; i++) {
			for (int j = 0; j < order; j++) {
				coef[j] += inputseries[i + 1] * inputseries[i - j];
				for (int k = j; k < order; k++) { // start with k=j due to symmetry of the matrix...
					mat[j][k] += inputseries[i - j] * inputseries[i - k];
				}
			}
		}
		// calculate the mean values for the matrix and the coefficients vector
		// according to the length of the timeseries
		for (int i = 0; i < order; i++) {
			coef[i] /= (length - order);
			for (int j = i; j < order; j++) {
				mat[i][j] /= (length - order);
				mat[j][i] = mat[i][j]; // use the symmetry of the matrix
			}
		}
		Matrix matrix = new Matrix(mat);
		Matrix coefficients = new Jama.Matrix(order, 1);
		for (int i = 0; i < order; i++) {
			coefficients.set(i, 0, coef[i]);
		}
		// solve the equation "matrix * X = coefficients", where x is the solution
		// vector with the AR-coeffcients
		try {
			ar = matrix.solve(coefficients).getRowPackedCopy();
		} catch (RuntimeException e) {
			System.out.println("Matrix is singular");
		}
		return ar;
	}

	public List<double[]> calculateCoefficients(List<Instance> inputs) {
		LinkedList<double[]> coeffs = new LinkedList<>();
		int numdim = inputs.get(0).noAttributes();
		for (int dim = 0; dim < numdim; dim++) {
			double[] ins = new double[inputs.size()];
			for (int index = 0; index < inputs.size(); index++) {
				ins[index] = inputs.get(index).value(dim);
			}
			// Apply Least Squares
			double[] ctemp = coefficientOpration(ins);
			coeffs.add(ctemp);
		}
		return coeffs;
	}

	protected double[] coefficientOpration(double[] inputs) {
		try {
			return calculateLeastSquare(inputs, order);
		} catch (Exception ex) {
			Logger.getLogger(DefaultAutoRegression.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@Override
	public Instance estimate(List<Instance> inputs) {
		try {
			List<double[]> coeffs = calculateCoefficients(inputs);
			return estimate(inputs, coeffs);
		} catch (Exception ex) {
			Logger.getLogger(DefaultAutoRegression.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	protected Instance estimate(List<Instance> inputs, List<double[]> coeffs) {
		Instance res = new DenseInstance(inputs.get(0).noAttributes());
		for (int dim = 0; dim < res.noAttributes(); dim++) {
			double[] coes = coeffs.get(dim);
			double estimate = 0.0;
			for (int index = 0; index < order; index++) {
				double data = inputs.get(inputs.size() - (index + 1)).value(dim);
				estimate += data * coes[index];
			}
			res.put(dim, estimate);
		}
		return res;
	}

	@Override
	public List<Instance> simulate(List<Instance> inputs) {
		try {
			LinkedList<Instance> res = new LinkedList<>();
			List<double[]> coeffs = calculateCoefficients(inputs);
			for (int index = 0; index < inputs.size() - order; index++) {
				List<Instance> data = inputs.subList(index, index + order);
				Instance resd = estimate(data, coeffs);
				res.add(resd);
			}
			return res;
		} catch (Exception ex) {
			Logger.getLogger(DefaultAutoRegression.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@Override
	public List<Instance> calculateError(List<Instance> inputs) {
		List<Instance> resd = simulate(inputs);
		int numdim = inputs.get(0).noAttributes();
		for (int index = 0; index < inputs.size(); index++) {
			for (int dim = 0; dim < numdim; dim++) {
				double res = resd.get(index).value(dim);
				double d = inputs.get(index + 1).value(dim);
				resd.get(index).put(dim, d - res);
			}
		}
		return resd;
	}

}
