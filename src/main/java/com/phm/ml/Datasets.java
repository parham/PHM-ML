
package com.phm.ml;

import java.util.LinkedList;
import java.util.List;

import Jama.Matrix;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class Datasets {

	public static Matrix matrix(Dataset ds) {
		int nch = ds.get(0).noAttributes();
		int ninst = ds.size();
		Matrix data = new Matrix(nch, ninst);
		for (int index = 0; index < ninst; index++) {
			Instance d = ds.get(index);
			for (int ch = 0; ch < nch; ch++) {
				data.set(ch, index, d.value(ch));
			}
		}

		return data;
	}

	public static List<double[]> channels(Dataset ds) {
		int nch = ds.get(0).noAttributes();
		int ninst = ds.size();
		LinkedList<double[]> res = new LinkedList<>();
		for (int ch = 0; ch < nch; ch++) {
			double[] cd = new double[ninst];
			for (int index = 0; index < cd.length; index++) {
				cd[index] = ds.get(index).value(ch);
			}
			res.add(cd);
		}
		return res;
	}
}
