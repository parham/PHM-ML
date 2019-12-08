
package com.phm.ml.ica;

import java.util.LinkedList;

import com.phm.ml.Datasets;

import Jama.Matrix;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class RecursiveICA extends IndependentComponentAnalysis {

	@Override
	public Dataset analysis(Dataset ds) {
		int nch = ds.get(0).noAttributes();
		Matrix data = Datasets.matrix(ds);
		Matrix w = Matrix.random(nch, nch).minus(Matrix.random(nch, nch));
		double lmdinit = 0.995;
		double iterate_num = 0;
		int[] row_indexes = { 0, 1, 2 };
		for (int f = 0; f < data.getColumnDimension(); f++) {
			iterate_num++;
			Matrix buffer = data.getMatrix(row_indexes, f, f);
			Matrix y = w.times(buffer);
			Matrix nonlin = mTanh(y);
			Matrix gn = w.transpose().times(nonlin);
			double lmd = lmdinit / Math.pow(iterate_num, 0.7);
			double t = 1 + lmd * (nonlin.transpose().times(y).get(0, 0) - 1);
			w.plusEquals(w.minus(y.times(gn.transpose()).times(1 / t)).times(lmd / (1 - lmd)));
		}
		//////////////////
		Matrix y = w.times(data);
		LinkedList<double[]> dims = new LinkedList<>();
		for (int ch = 0; ch < y.getRowDimension(); ch++) {
			Matrix ye = y.getMatrix(ch, ch, 0, y.getColumnDimension() - 1);
			ye.timesEquals(1 / ye.norm1());
			double[] dimd = new double[ye.getColumnDimension()];
			for (int dim = 0; dim < dimd.length; dim++) {
				dimd[dim] = ye.get(0, dim);
			}
			dims.add(dimd);
		}

		int lens = dims.get(0).length;
		for (int index = 0; index < lens; index++) {
			Instance inst = new DenseInstance(dims.size());
			for (int ch = 0; ch < dims.size(); ch++) {
				inst.put(ch, dims.get(ch)[index]);
			}
		}

		DefaultDataset dres = new DefaultDataset();
		for (int index = 0; index < y.getColumnDimension(); index++) {
			DenseInstance inst = new DenseInstance(y.getRowDimension());
			for (int ch = 0; ch < y.getRowDimension(); ch++) {
				inst.put(ch, y.get(ch, index));
			}
			dres.add(inst);
		}
		return dres;
	}

	protected Matrix mTanh(Matrix d) {
		Matrix res = d.copy();
		for (int row = 0; row < res.getRowDimension(); row++) {
			for (int col = 0; col < res.getColumnDimension(); col++) {
				double temp = res.get(row, col);
				res.set(row, col, Math.tanh(temp));
			}
		}
		return res;
	}
}
