
package com.phm.ml;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicInteger;

import Jama.Matrix;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class FeatureEntity implements Serializable, Cloneable, Instance {

	public final static String ENTITY_LABEL = "entity.label";

	private static AtomicInteger IDGenerator = new AtomicInteger(0);

	private int id;
	private DenseInstance centroid;
	private ParametersContainer parameters;

	private FeatureEntity(int idt, int ch, ParametersContainer pc) {
		id = idt;
		centroid = new DenseInstance(ch);
		parameters = new ParametersContainer();
		pc.putAll(pc);
	}

	private FeatureEntity(int idt, double[] chs, ParametersContainer pc) {
		id = idt;
		centroid = new DenseInstance(chs);
		parameters = new ParametersContainer();
		pc.putAll(pc);
	}

	private FeatureEntity(int idt, Instance inst, ParametersContainer pc) {
		id = idt;
		centroid = new DenseInstance(inst.noAttributes());
		for (int index = 0; index < centroid.noAttributes(); index++) {
			centroid.put(index, inst.value(index));
		}
		parameters = new ParametersContainer();
		pc.putAll(pc);
	}

	public FeatureEntity() {
		this(IDGenerator.getAndIncrement(), 1, new ParametersContainer());
	}

	public FeatureEntity(int nch) {
		this(IDGenerator.getAndIncrement(), nch, new ParametersContainer());
	}

	public FeatureEntity(double[] chs) {
		this(IDGenerator.getAndIncrement(), chs, new ParametersContainer());
	}

	public FeatureEntity(Instance inst) {
		this(IDGenerator.getAndIncrement(), inst, new ParametersContainer());
	}

	public FeatureEntity(int nch, ParametersContainer pc) {
		this(nch);
		parameters.putAll(pc);
	}

	public FeatureEntity(double[] chs, ParametersContainer pc) {
		this(chs);
		parameters.putAll(pc);
	}

	public FeatureEntity(Instance chs, ParametersContainer pc) {
		this(chs);
		parameters.putAll(pc);
	}

	public Matrix asMatrix() {
		Matrix mat = new Matrix(noAttributes(), 1);
		for (int dim = 0; dim < noAttributes(); dim++) {
			mat.set(dim, 0, value(dim));
		}
		return mat;
	}

	protected void setID(int idv) {
		id = idv;
	}

	public void setCentroid(double[] data) {
		centroid = new DenseInstance(data);
	}

	public void setCentroid(Instance inst) {
		centroid = new DenseInstance(inst.noAttributes());
		for (int index = 0; index < centroid.noAttributes(); index++) {
			centroid.put(index, inst.value(index));
		}
	}

	public Instance getCentroid() {
		return centroid;
	}

	public void SetParametersContainer(ParametersContainer pc) {
		parameters = pc;
	}

	public ParametersContainer getParametersContainer() {
		return parameters;
	}

	public Object setParameter(String key, Object value) {
		return parameters.put(key, value);
	}

	public void setParameters(Map<? extends String, ? extends Object> mp) {
		parameters.putAll(mp);
	}

	public Object getParameter(String key) {
		return parameters.get(key);
	}

	public boolean containsParameter(String key) {
		return parameters.containsKey(key);
	}

	public Object removeParameter(String key) {
		return parameters.remove(key);
	}

	public void clearParameters() {
		parameters.clear();
	}

	public boolean processOnParameter(String key, ProcessOnParameter process) {
		return parameters.processOnParameter(key, process);
	}

	public double[] toArray() {
		double[] d = new double[centroid.noAttributes()];
		for (int index = 0; index < d.length; index++) {
			d[index] = centroid.value(id);
		}
		return d;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null && obj instanceof FeatureEntity && obj.hashCode() == hashCode());
	}

	@Override
	public int hashCode() {
		return this.id;
	}

	@Override
	public FeatureEntity clone() {
		return new FeatureEntity(id, toArray(), parameters);
	}

	@Override
	public String toString() {
		return "[" + this.id + "]";
	}

	@Override
	public Object classValue() {
		return this.parameters.get(ENTITY_LABEL);
	}

	@Override
	public void setClassValue(Object value) {
		this.parameters.put(ENTITY_LABEL, value);
	}

	@Override
	public int noAttributes() {
		return centroid.noAttributes();
	}

	@Override
	public int size() {
		return centroid.size();
	}

	@Override
	public double value(int pos) {
		return centroid.value(pos);
	}

	@Override
	public Instance minus(Instance min) {
		return centroid.minus(min);
	}

	@Override
	public SortedSet<Integer> keySet() {
		return centroid.keySet();
	}

	@Override
	public Instance minus(double value) {
		return centroid.minus(value);
	}

	@Override
	public Instance add(Instance max) {
		return centroid.add(max);
	}

	@Override
	public Instance divide(double value) {
		return centroid.divide(value);
	}

	@Override
	public Instance divide(Instance currentRange) {
		return centroid.divide(currentRange);
	}

	@Override
	public Instance add(double value) {
		return centroid.add(value);
	}

	@Override
	public Instance multiply(double value) {
		return centroid.multiply(value);
	}

	@Override
	public Instance multiply(Instance value) {
		return centroid.multiply(value);
	}

	@Override
	public void removeAttribute(int i) {
		centroid.removeAttribute(i);
	}

	@Override
	public Instance sqrt() {
		return centroid.sqrt();
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public Instance copy() {
		return this.clone();
	}

	@Override
	public void removeAttributes(Set<Integer> indices) {
		centroid.removeAttributes(indices);
	}

	@Override
	public boolean isEmpty() {
		return centroid.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return centroid.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return centroid.containsValue(value);
	}

	@Override
	public Double get(Object key) {
		return centroid.get(key);
	}

	@Override
	public Double put(Integer key, Double value) {
		return centroid.put(key, value);
	}

	@Override
	public Double remove(Object key) {
		return centroid.remove(key);
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Double> m) {
		centroid.putAll(m);
	}

	@Override
	public void clear() {
		centroid.clear();
	}

	@Override
	public Collection<Double> values() {
		return centroid.values();
	}

	@Override
	public Set<Entry<Integer, Double>> entrySet() {
		return centroid.entrySet();
	}

	@Override
	public Iterator<Double> iterator() {
		return centroid.iterator();
	}
}
