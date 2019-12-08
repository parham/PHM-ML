
package com.phm.ml;

import java.util.Collection;

import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class TimeSeries extends DefaultDataset {
	protected double timestamp = 0;

	public TimeSeries() {
		super();
	}

	public TimeSeries(double t, Collection<Instance> insts) {
		super(insts);
		timestamp = t;
	}

	public TimeSeries(double t) {
		timestamp = t;
	}

	public TimeSeries(Collection<Instance> insts) {
		this(0, insts);
	}

	public void setTime(double t) {
		timestamp = t;
	}

	public double getTime() {
		return timestamp;
	}
}
