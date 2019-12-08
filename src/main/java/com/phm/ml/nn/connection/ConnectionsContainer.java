
package com.phm.ml.nn.connection;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.phm.ml.ArraySet;
import com.phm.ml.nn.Neuron;

/**
 *
 * @author Parham Nooralishahi - PHM!
 */
public class ConnectionsContainer extends CopyOnWriteArraySet<Connection> {

	protected ArraySet<Neuron> neurons = new ArraySet<Neuron>();

	@Override
	public boolean add(Connection c) {
		if (c != null && !this.contains(c)) {
			super.add(c);
			return true;
		}
		return false;
	}

	public boolean add(Neuron n1, Neuron n2, float edge) {
		return add(new Connection(n1, n2, edge));
	}

	public boolean add(Neuron n1, Neuron n2) {
		return add(n1, n2, 0);
	}

	public boolean remove(Neuron n1, Neuron n2) {
		return remove(new Connection(n1, n2));
	}

	public boolean contains(Neuron n1, Neuron n2) {
		return contains(new Connection(n1, n2));
	}

	public List<Neuron> neighbors(Neuron n) {
		LinkedList<Neuron> neighbors = new LinkedList<>();
		this.stream().map((x) -> x.connectTo(n)).filter((temp) -> (temp != null)).forEach((temp) -> {
			neighbors.add(temp);
		});

		return neighbors;
	}

	public float setConnectionEdgeValue(Connection c, float edge) {
		float preValue;
		for (Connection x : this) {
			if (c.equals(x)) {
				preValue = (float) c.getParameter(Connection.CONNECTION_VALUE);
				c.setParameter(Connection.CONNECTION_VALUE, edge);
				return preValue;
			}
		}
		return -1;
	}

	public float incrementConnectionEdgeValue(Connection c) {
		for (Connection x : this) {
			if (c.equals(x)) {
				c.parameters.processOnParameter(Connection.CONNECTION_VALUE, new Connection.IncrementEdgeValue());
				return (float) c.parameters.get(Connection.CONNECTION_VALUE);
			}
		}
		return -1;
	}

	public void incerementConnectionsEdgeValue() {
		this.stream().parallel().forEach((Connection x) -> {
			incrementConnectionEdgeValue(x);
		});
	}

	public void removeConnectionWithValue(float value) {
		LinkedList<Connection> del = new LinkedList<>();
		this.stream().forEach((Connection x) -> {
			float v = (float) x.getParameter(Connection.CONNECTION_VALUE);
			if (v == value) {
				del.add(x);
			}
		});
		del.stream().forEach((x) -> {
			remove(x);
		});
	}

	public void removeConnectionWithHigherValue(float value) {
		LinkedList<Connection> del = new LinkedList<>();
		this.stream().forEach((Connection x) -> {
			float v = (float) x.getParameter(Connection.CONNECTION_VALUE);
			if (v >= value) {
				del.add(x);
			}
		});
		del.stream().forEach((x) -> {
			remove(x);
		});
	}

	public Set<Connection> getAllConnections(Neuron source, Neuron target) {
		Set<Connection> s = new ArraySet<>();
		Connection c = new Connection(source, target);
		for (Connection x : this) {
			if (c.equals(x)) {
				s.add(x);
			}
		}
		return s;
	}

	public Connection getConnection(Neuron source, Neuron target) {
		Connection c = new Connection(source, target);
		for (Connection x : this) {
			if (c.equals(x)) {
				return x;
			}
		}
		return null;
	}
//    @Override
//    public EdgeFactory<Neuron, Connection> getEdgeFactory() {
//        return new ConnectionFactory();
//    }
//    @Override
//    public Connection addEdge(Neuron source, Neuron target) {
//        Connection c = new Connection(source, target);
//        this.add(c);
//        return c;
//    }
//    @Override
//    public boolean addEdge(Neuron source, Neuron target, Connection e) {
//        e = addEdge(source, target);
//        return true;
//    }
//    @Override
//    public boolean addVertex(Neuron v) {
//        return neurons.add(v);
//    }
//    @Override
//    public boolean containsEdge (Neuron source, Neuron target) {
//        return this.contains(source, target);
//    }
//    @Override
//    public boolean containsEdge(Connection e) {
//        return this.contains(e);
//    }
//    @Override
//    public boolean containsVertex(Neuron v) {
//        return neurons.contains(v);
//    }
//    @Override
//    public Set<Connection> edgeSet() {
//        return new ArraySet<>(this);
//    }
//    @Override
//    public Set<Connection> edgesOf(Neuron v) {
//        ArraySet<Connection> arr = new ArraySet<>();
//        for (Connection x : this) {
//            if (x.neuronOne.equals(v)) {
//                arr.add(x);
//            }
//        }
//        return arr;
//    }
//    @Override
//    public boolean removeAllEdges(Collection<? extends Connection> clctn) {
//        this.clear();
//        return true;
//    }
//    @Override
//    public Set<Connection> removeAllEdges(Neuron source, Neuron target) {
//        Set<Connection> arr = getAllEdges(source, target);
//        if (arr.size() > 0) {
//            this.removeAll(arr);
//        }
//        return arr;
//    }
//    @Override
//    public boolean removeAllVertices(Collection<? extends Neuron> vs) {
//        return neurons.removeAll(vs);
//    }
//    @Override
//    public Connection removeEdge (Neuron source, Neuron target) {
//        if (this.remove(source, target)) {
//            return new Connection(source, target);
//        }
//        return null;
//    }
//    @Override
//    public boolean removeEdge(Connection e) {
//        return this.remove(e);
//    }
//    @Override
//    public boolean removeVertex(Neuron v) {
//        return neurons.remove(v);
//    }
//    @Override
//    public Set<Neuron> vertexSet() {
//        return new ArraySet<>(neurons);
//    }
//    @Override
//    public Neuron getEdgeSource(Connection e) {
//        return e.neuronOne;
//    }
//    @Override
//    public Neuron getEdgeTarget(Connection e) {
//        return e.neuronTwo;
//    }
//    @Override
//    public double getEdgeWeight(Connection e) {
//        return (float) e.getParameter(Connection.CONNECTION_VALUE);
//    }
}
