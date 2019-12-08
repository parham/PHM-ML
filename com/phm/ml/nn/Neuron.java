
package com.phm.ml.nn;

import com.phm.ml.FeatureEntity;
import com.phm.ml.ParametersContainer;
import com.phm.ml.ProcessOnParameter;
import com.phm.ml.nn.connection.NeuronConnectionsContainer;
import java.util.LinkedList;
import java.util.List;
import net.sf.javaml.core.Instance;

/**
 *
 * @author Parham Nooralishahi - PHM!
 */
public class Neuron extends FeatureEntity {
    
    public final static String SYSTEM_STATUS = "system.status";
    
    public final static String NEURON_LABEL = ENTITY_LABEL;
    public final static String NEURON_ID = "neuron.id";
    public final static String NEURON_DIMENSION = "neuron.dimension";
    public final static String NEURON_NUM_CHANNEL = "neuron.channel";
    public final static String NEURON_CENTROID = "neuron.centroid";
    public final static String NEURON_CONNECTIONS = "neuron.connections";
    
    public final static String RECIEVED_SIGNALS_FLAG = "signal.store.flag";
    public final static String RECIEVED_SIGNALS = "recieved.signals";
    public final static String RECIEVED_SIGNALS_NUM = "num.signals.feed";
    
    public final static String NUM_CONNECTIONS_FLAG = "num.connections.flag";
    public final static String NUM_CONNECTIONS = "num.connections";
    
    public static final ProcessOnParameter INCREASE_QUANTITY = new ChangeQuantityByValue(1);
    public static final ProcessOnParameter DECREASE_QUANTITY = new ChangeQuantityByValue(-1);
    
    public final NeuronConnectionsContainer connections;
    public final SupervisorsContainer supervisors = new SupervisorsContainer ();
    public final NeuronAnalyzersContianer analyzers = new NeuronAnalyzersContianer();
    protected InputStrategy inputStrategy = new IneffectiveInputStrategy ();
   
    public Neuron (ParametersContainer param,
                   NeuronConnectionsContainer cc,
                   Instance c) {
        super (c, param);
        connections = (NeuronConnectionsContainer) cc;
        initialize();
    }
    public Neuron (ParametersContainer param,
                   NeuronConnectionsContainer cc,
                   double [] c) {
        super (c, param);
        connections = (NeuronConnectionsContainer) cc;
        initialize();
    }
    public Neuron (ParametersContainer param,
                   NeuronConnectionsContainer cc,
                   int c) {
        super (c, param);
        connections = (NeuronConnectionsContainer) cc;
        initialize();
    }
    public Neuron (ParametersContainer param,
                   Instance c) {
        super (c, param);
        connections = new NeuronConnectionsContainer(this);
        initialize();
    }
    public Neuron (ParametersContainer param,
                   double [] c) {
        super (c, param);
        connections = new NeuronConnectionsContainer(this);
        initialize();
    }
    public Neuron (ParametersContainer param,
                   int c) {
        super (c, param);
        connections = new NeuronConnectionsContainer(this);
        initialize();
    }
    public Neuron (double [] c) {
        this (new ParametersContainer(), c);
    }
    public Neuron (Instance d) {
        this (new ParametersContainer(), d);
    }
    public Neuron (int nch) {
        this (new ParametersContainer(), nch);
    }
    public Neuron () {
        this (1);
    }
    
    private void initialize () {
        // Initialize parameters 
        setParameter (NEURON_ID, getID());
        setParameter (NEURON_NUM_CHANNEL, noAttributes());
        setParameter (SYSTEM_STATUS, "");
        // Initialize Neuron Analyzer
        analyzers.add(new Neuron.InternalAnalayzer(this));
        analyzers.add(new Neuron.RecievedSignalAnalyzer(this));
        analyzers.add(new Neuron.ConnectionsAnalyzer(this));
    }
    public String setStatus (String status) {
        return (String) getParameter (SYSTEM_STATUS);
    }    
    public String getStatus () {
        return (String) getParameter (SYSTEM_STATUS);
    }
    
    public void setInputStrategy (InputStrategy is) {
        inputStrategy = is;
    }
    public InputStrategy getInputStrategy () {
        return inputStrategy;
    }
    public boolean feed (String state, final Instance signal, NNResultContainer result) {
        setStatus(state);
        if (result == null) {
            result = new NNResultContainer ();
        }
        
        Instance temp = inputStrategy.input(signal);
        if (supervisors.supervise(state, this, temp, result)) {
            analyzers.analysis(state, this, getParametersContainer(), temp, result);
            return true;
        }
        return false;
    }
    public boolean feed (final Instance signal, NNResultContainer result) {
        String state = (String) getParameter (SYSTEM_STATUS);
        return feed (state, signal, result);
    }
    
    @Override
    public boolean equals (Object obj) {
        return (obj != null && 
                obj instanceof Neuron &&
                obj.hashCode() == hashCode());
    }
    @Override
    public int hashCode() {
        return this.getID();
    }
    @Override
    public Neuron clone () {
        Neuron tmp = new Neuron (getParametersContainer(), connections, getCentroid());
        tmp.setID(getID());
        tmp.inputStrategy = inputStrategy;
        tmp.supervisors.addAll(supervisors);
        tmp.analyzers.addAll(analyzers);
        return tmp;
    }

    @Override
    public Instance copy() {
        return this.clone();
    }
    
    public static class ChangeQuantityByValue implements ProcessOnParameter {
        
        int extraValue = 0;
        
        public ChangeQuantityByValue (int ev) {
            extraValue = ev;
        }
        
        @Override
        public Object process(Object data, ParametersContainer c) {
            int temp = (int) data;
            return temp + extraValue;
        }
    }
    
    public static class AddValueTo implements ProcessOnParameter {
        float extraValue = 0;
        
        public AddValueTo (float ev) {
            extraValue = ev;
        }
        
        @Override
        public Object process(Object data, ParametersContainer c) {
            float temp = (float) data;
            return temp + extraValue;
        }
    }
    
    public static class InternalAnalayzer implements NeuronAnalyzer {
        public InternalAnalayzer (Neuron n) {
            n.setParameter (NEURON_CENTROID, n.getCentroid());
        }
        @Override
        public void analysis(String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) {
            n.setParameter (NEURON_CENTROID, n.getCentroid());
        }
    }
    
    public static class ConnectionsAnalyzer implements NeuronAnalyzer {
        public ConnectionsAnalyzer (Neuron n) {
            n.setParameter(NEURON_CONNECTIONS, n.connections);
            n.setParameter(NUM_CONNECTIONS, 0);
        }
        
        @Override
        public void analysis(String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) {
            n.setParameter(NUM_CONNECTIONS, n.connections.size());
        }
    }
    
    public static class RecievedSignalAnalyzer implements NeuronAnalyzer {
        public RecievedSignalAnalyzer (Neuron n) {
            n.setParameter (RECIEVED_SIGNALS, new LinkedList<Instance>());
            n.setParameter (RECIEVED_SIGNALS_FLAG, false);
            n.setParameter (RECIEVED_SIGNALS_NUM, 0);
        }
        @Override
        public void analysis(String state, Neuron n, ParametersContainer param, Instance current, NNResultContainer result) {
            param.processOnParameter(Neuron.RECIEVED_SIGNALS_NUM, Neuron.INCREASE_QUANTITY);
            if ((boolean) param.get(Neuron.RECIEVED_SIGNALS_FLAG)) {
                ((List<Instance>) param.get(Neuron.RECIEVED_SIGNALS)).add(current);
            }
        }
    }
}
