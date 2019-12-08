
package com.phm.ml.nn;

import com.phm.ml.ArraySet;
import com.phm.ml.nn.container.NeuronsContainer;
import com.phm.ml.ParametersContainer;
import com.phm.ml.event.EventListenersManager;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.connection.ConnectionFactory;
import com.phm.ml.nn.connection.ConnectionsContainer;
import com.phm.ml.nn.connection.NeuronConnectionsContainer;
import com.phm.ml.nn.container.ListNeuronContainer;
import com.phm.ml.nn.event.ConnectionAddedEvent;
import com.phm.ml.nn.event.ConnectionRemovedEvent;
import com.phm.ml.nn.event.NGStopedTrainingEvent;
import com.phm.ml.nn.event.NGUpdatedEvent;
import com.phm.ml.nn.event.NeuronAddedEvent;
import com.phm.ml.nn.event.NeuronRemovedEvent;
import com.phm.ml.nn.restric.RestrictionsContainer;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.sf.javaml.core.Instance;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;

/**
 *
 * @author PARHAM
 */
public class NeuronGroup extends Neuron 
                         implements Graph<Neuron, Connection> {
    
    public final static String NUM_NEURONS = "num.neurons";
    public final static String NET_NEURONS = "net.neurons";
    public final static String NEURON_REDIRECT_SIGNAL_FLAG = "neuron.redirect.signal.flag";
    
    protected static final ConnectionFactory conFactory = new ConnectionFactory();
    public final NeuronsContainer neurons;
    public final RestrictionsContainer restrictions = new RestrictionsContainer();
    public final EventListenersManager listeners = new EventListenersManager();
    protected final ConcurrentLinkedQueue<Instance> signalBuffer = new ConcurrentLinkedQueue<>();
    
    public NeuronGroup (NeuronsContainer nc,
                        ParametersContainer param,
                        NeuronConnectionsContainer ncc,
                        double [] dims) {
        super (param, ncc, dims);
        neurons = Objects.requireNonNull(nc);
        initialize();
    }
    public NeuronGroup (NeuronsContainer nc,
                        ParametersContainer param,
                        NeuronConnectionsContainer ncc,
                        Instance dims) {
        super (param, ncc, dims);
        neurons = Objects.requireNonNull(nc);
        initialize();
    }
    public NeuronGroup (ParametersContainer param,
                        NeuronConnectionsContainer cc,
                        Instance chns) {
        this (new ListNeuronContainer (), param, cc, chns);
    }
    public NeuronGroup (ParametersContainer param,
                        NeuronConnectionsContainer cc,
                        double [] chns) {
        this (new ListNeuronContainer (), param, cc, chns);
    }
    public NeuronGroup (double [] chns) {
        super (chns);
        neurons = new ListNeuronContainer ();
        initialize ();
    }
    public NeuronGroup (Instance chns) {
        super (chns);
        neurons = new ListNeuronContainer ();
        initialize ();
    }
    
    private void initialize () {
        analyzers.add(new NeuronsAnalayzer(this));
        setParameter(NEURON_REDIRECT_SIGNAL_FLAG, true);
    }
    @Override
    public boolean feed (String state, final Instance s, NNResultContainer result) {
        boolean status = false;
        
        signalBuffer.add(s);
        while (signalBuffer.size() > 0) {
            Instance signal = signalBuffer.poll ();
            Instance temp = inputStrategy.input(signal);
            // Check Restrictions and Apply Supervisor algorithms
            if (supervisors.supervise (state, this, temp, result)) {
                analyzers.analysis (state, this, getParametersContainer(), temp, result);
                // Release update event
                listeners.event(new NGUpdatedEvent(this));
                boolean redirect = (boolean) getParameter(NEURON_REDIRECT_SIGNAL_FLAG);
                if (redirect) {
                    NNResult tmp = result.getRecent();
                    tmp.winners.stream().forEach((Neuron n) -> {
                        NNResultContainer rtmp = new NNResultContainer();
                        n.feed(state, signal, rtmp);
                    });
                }
                status = true;
            } else {
                status = false;
                break;
            }
        }
        
        listeners.event(new NGStopedTrainingEvent(this));
        return status;
    }
    public void addSignalInternally (Instance signal) {
        signalBuffer.add(signal);
    }
    public boolean addInternalNeuron (Neuron neuron) {
        if (neuron != null && neurons.add(neuron)) {
            processOnParameter(NeuronGroup.NUM_NEURONS, INCREASE_QUANTITY);
            listeners.event(new NeuronAddedEvent(neuron));
            return true;
        }
        return false;
    }
    public boolean removeInternalNeuron (Neuron neuron) {
        if (neuron != null && neurons.remove(neuron)) {
            processOnParameter(NUM_NEURONS, DECREASE_QUANTITY);
            listeners.event(new NeuronRemovedEvent(neuron));
            return true;
        }
        return false;
    }
    public void clearInternalNeurons () {
        setParameter (NUM_NEURONS, 0);
        neurons.clear();
    }
    public int countInternalNeurons () {
        return neurons.size();
    }
    public boolean updateInternalConnection (Connection c) {
        if (c == null) return false;
        if (c.neuronOne.connections.add(c) &&
            c.neuronTwo.connections.add(c)) {
            processOnParameter(NUM_CONNECTIONS, INCREASE_QUANTITY);
            listeners.event(new ConnectionAddedEvent(c));
        } else {
            // Remove the previous connection
            c.neuronOne.connections.remove(c);
            c.neuronTwo.connections.remove(c);
            listeners.event(new ConnectionRemovedEvent(c));
            // Add the updated connection
            c.neuronOne.connections.add(c);
            c.neuronTwo.connections.add(c);
            listeners.event(new ConnectionAddedEvent(c));
        }
        return true;
    }
    public boolean removeInternalConnection (Connection c) {
        if (c == null) {
            return false;
        }
        if (c.neuronOne.connections.remove(c) &&
            c.neuronTwo.connections.remove(c)) {
            processOnParameter(NUM_CONNECTIONS, DECREASE_QUANTITY);
            listeners.event(new ConnectionRemovedEvent(c));
            return true;
        }
        return false;
    }
    public ConnectionsContainer getConnections () {
        ConnectionsContainer cc = new ConnectionsContainer();
        this.neurons.toList().stream().parallel().forEach((Neuron x) -> {
            cc.addAll(x.connections);
        });
        return cc;
    }
    @Override
    public Set<Connection> getAllEdges(Neuron v, Neuron v1) {
        ArraySet<Connection> edges = new ArraySet<>();
        edges.addAll(v.connections.getAllConnections(v, v1));
        edges.addAll(v1.connections.getAllConnections(v, v1));
        return edges;
    }
    @Override
    public Connection getEdge(Neuron v, Neuron v1) {
        Connection c = v.connections.getConnection (v, v1);
        if (c != null) {
            return c;
        }
        c = v1.connections.getConnection(v, v1);
        if (c != null) {
            return c;
        }
        return null;
    }
    @Override
    public EdgeFactory<Neuron, Connection> getEdgeFactory() {
        return conFactory;
    }
    @Override
    public Connection addEdge(Neuron v, Neuron v1) {
        Connection prev = getEdge(v, v1);
        updateInternalConnection(conFactory.createEdge (v, v1));
        return prev;
    }
    @Override
    public boolean addEdge(Neuron v, Neuron v1, Connection e) {
        addEdge(v, v1);
        return true;
    }
    @Override
    public boolean addVertex(Neuron v) {
        return addInternalNeuron(v);
    }
    @Override
    public boolean containsEdge(Neuron v, Neuron v1) {
        Connection c = conFactory.createEdge(v, v1);
        return containsEdge(c);
    }
    @Override
    public boolean containsEdge(Connection c) {
        if (c.neuronOne.connections.contains(c) &&
            c.neuronTwo.connections.contains(c)) {
            return true;
        }
        return false;        
    }
    @Override
    public boolean containsVertex(Neuron v) {
        return this.neurons.contains(v);
    }
    @Override
    public Set<Connection> edgeSet() {
        return getConnections();
    }
    @Override
    public Set<Connection> edgesOf(Neuron v) {
        return v.connections;
    }
    @Override
    public boolean removeAllEdges(Collection<? extends Connection> cons) {
        for (Connection x : cons) {
            removeEdge(x);
        }
        return true;
    }
    @Override
    public Set<Connection> removeAllEdges (Neuron v, Neuron v1) {
        ArraySet<Connection> cons = new ArraySet<>();
        cons.addAll (v.connections.getAllConnections(v, v1));
        cons.addAll (v1.connections.getAllConnections(v, v1));
        Connection c = conFactory.createEdge(v, v1);
        for (Connection x : cons) {
            removeEdge(c);
        }
        return cons;
    }
    public Set<Connection> removeAllEdges (Connection c) {
        return removeAllEdges(c.neuronOne, c.neuronTwo);
    }
    @Override
    public boolean removeAllVertices(Collection<? extends Neuron> cs) {
        for (Neuron n : cs) {
            removeVertex(n);
        }
        return true;
    }
    @Override
    public Connection removeEdge(Neuron v, Neuron v1) {
        Connection c = conFactory.createEdge(v, v1);
        removeInternalConnection(c);
        return c;
    }
    @Override
    public boolean removeEdge(Connection c) {
        return removeInternalConnection(c);
    }
    @Override
    public boolean removeVertex(Neuron v) {
        return removeInternalNeuron(v);
    }
    @Override
    public Set<Neuron> vertexSet() {
        return neurons;
    }
    @Override
    public Neuron getEdgeSource(Connection e) {
        return e.neuronOne;
    }
    @Override
    public Neuron getEdgeTarget(Connection e) {
        return e.neuronTwo;
    }
    @Override
    public double getEdgeWeight(Connection e) {
        float v = (float) e.parameters.get(Connection.CONNECTION_VALUE);
        return v;
    }
    public boolean initialize (Graph<Neuron, Connection> graph) {
        // Add Verteces
        Set<Neuron> ns = graph.vertexSet();
        for (Neuron x : ns) {
            this.addInternalNeuron (x);
        }
        // Add Connections
        Set<Connection> cs = graph.edgeSet();
        for (Connection x : cs) {
            this.updateInternalConnection(x);
        }
        return true;
    }
//    public boolean initialize (NeuronGroup group, Graph<Neuron, Connection> graph) {
//        // Add Verteces
//        Set<Neuron> ns = graph.vertexSet();
//        for (Neuron x : ns) {
//            //Neuron tmp = 
//            this.addInternalNeuron (x);
//        }
//        // Add Connections
//        Set<Connection> cs = graph.edgeSet();
//        for (Connection x : cs) {
//            this.updateInternalConnection(x);
//        }
//        return true;
//    }

    public static class NeuronsAnalayzer implements NeuronAnalyzer {
        public NeuronsAnalayzer (NeuronGroup n) {
            n.setParameter(NUM_NEURONS, n.neurons.size());
            n.setParameter(NET_NEURONS, n.neurons);
        }
        @Override
        public void analysis(String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) {
            NeuronGroup ng = (NeuronGroup) n;
            ng.setParameter(NUM_NEURONS, ng.neurons.size());
            ng.setParameter(NET_NEURONS, ng.neurons);
        }
    }
}
