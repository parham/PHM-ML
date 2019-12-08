
package com.phm.ml;

import net.sf.javaml.core.DenseInstance;

/**
 *
 * @author phm
 */
public class TimeSeriesInstance extends DenseInstance {

	protected double timestamp = 0;

	public TimeSeriesInstance(double t, double[] d) {
		super(d);
		timestamp = t;
	}

	public TimeSeriesInstance(double[] d) {
		this(0, d);
	}

	public TimeSeriesInstance(double t, int dim) {
		super(dim);
		timestamp = t;
	}

	public TimeSeriesInstance(int dim) {
		super(dim);
	}

	public TimeSeriesInstance(double t, double[] d, Object cls) {
		super(d, cls);
		timestamp = t;
	}

	public TimeSeriesInstance(double[] d, Object cls) {
		this(0, d, cls);
	}

	public void setTime(double t) {
		timestamp = t;
	}

	public double getTime() {
		return timestamp;
	}
}
