
package com.phm.ml.triangulation;

import com.phm.ml.nn.DefaultNeuronComparePolicy;
import com.phm.ml.nn.algorithms.gng.*;
import com.phm.ml.ParametersContainer;
import com.phm.ml.ProcessOnParameter;
import com.phm.ml.distance.DefaultDistanceMethod;
import com.phm.ml.distance.DistanceInfo;
import com.phm.ml.nn.Supervisor;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.NeuronDistanceMethod;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;

/**
 *
 * @author phm
 */
public class GNGNoLearnSupervisor extends Supervisor {

    public static final String NEURON_DISTANCE = "neuron.distance";
    public static final String NEURON_DIS_DIMENSION = "neuron.local.distance";
    public static final String NEURON_LOCALERROR = "neuron.local.error";

    public static final String GNG_EPSILON_B_KEY = "epsilon.b";
    public static final String GNG_EPSILON_N_KEY = "epsilon.n";
    // needed age for an edge to be removed
    public static final String GNG_MAX_EDGE_AGE_KEY = "max.edge.age";
    // number signal generations needed to insert new node
    public static final String GNG_LANDA_KEY = "landa.key";
    public static final String GNG_ERRORRATE_A_KEY = "error.a";
    public static final String GNG_ERRORRATE_B_KEY = "error.b";

    public static final DefaultNeuronComparePolicy COMPARE_POLICY = new DefaultNeuronComparePolicy();

    protected final NeuronDistanceMethod distanceMethod;

    public GNGNoLearnSupervisor(NeuronDistanceMethod dm) {
        distanceMethod = Objects.requireNonNull(dm);
    }
    public GNGNoLearnSupervisor () {
        this (new NeuronDistanceMethod (new DefaultDistanceMethod(new EuclideanDistance())));
    }
    @Override
    public void initialize(Neuron ngroup) {
        ngroup.setParameter(GNG_EPSILON_B_KEY, 0.05f);
        ngroup.setParameter(GNG_EPSILON_N_KEY, 0.0006f);
        ngroup.setParameter(GNG_MAX_EDGE_AGE_KEY, 23);
        ngroup.setParameter(GNG_LANDA_KEY, 1);
        ngroup.setParameter(GNG_ERRORRATE_A_KEY, 0.5f);
        ngroup.setParameter(GNG_ERRORRATE_B_KEY, 0.0005f);
    }
    public NeuronDistanceMethod getDistanceMethod() {
        return distanceMethod;
    }
    protected Neuron initializeGNGNeuron(NeuronGroup ngroup, Neuron neuron) {
        neuron.setParameter(NEURON_DISTANCE, 0.0f);
        neuron.setParameter(NEURON_DIS_DIMENSION, new DenseInstance(new double [neuron.noAttributes()]));
        neuron.setParameter(NEURON_LOCALERROR, 0.0f);
        return neuron;
    }
    protected void beforeInitialPhase(NeuronGroup ngroup, Instance signal) {
        // Empty body
    }
    protected Neuron onInitialPhase(NeuronGroup ngroup, Instance buffer) {
        Neuron nn = initializeGNGNeuron(ngroup, new Neuron (buffer));
        ngroup.addInternalNeuron(nn);
        return nn;
    }
    protected void afterInitialPhase(NeuronGroup ngroup, Instance signal, Neuron nn) {
        // Empty body
    }
    protected void onDistanceCalculation (NeuronGroup ngroup, Instance signal) {
        List<Neuron> ns = ngroup.neurons.toList();
        ns.stream().forEach((Neuron x) -> {
            DistanceInfo dd = distanceMethod.distance(ngroup, x, signal);
            x.setParameter(NEURON_DISTANCE, (float) dd.distance);
            x.setParameter(NEURON_DIS_DIMENSION, dd.distancedim);
        });
    }
    protected void afterDistanceCalculation (NeuronGroup ngroup, Instance signal) {
        // Empty body
    }
    protected List<Neuron> onWinnersFinding (NeuronGroup ngroup, Instance signal) {
        LinkedList<Neuron> ns = new LinkedList<>(ngroup.neurons.toList());
        Collections.sort(ns, COMPARE_POLICY);
        LinkedList<Neuron> result = new LinkedList<>();
        result.add(ns.get(0));
        result.add(ns.get(1));
        return result;
    }
    protected void afterWinnersFinding (NeuronGroup ngroup, 
                                        List<Neuron> winners, 
                                        Instance signal) {
        // Empty body
    }
    protected void onWinnersUpdate (NeuronGroup ngroup, 
                                    List<Neuron> winners, 
                                    Instance signal) {
        float epsilonB = (Float) ngroup.getParameter(GNG_EPSILON_B_KEY);
        float epsilonN = (Float) ngroup.getParameter(GNG_EPSILON_N_KEY);
        // Create Connection between two nearest neurons
        Connection c = new Connection(winners.get(0), winners.get(1), 0);
        ngroup.updateInternalConnection(c);
        ////////////////////////////////////////////////////////
        // Add squared error of winner unit to a local error
        winners.get(0).processOnParameter(NEURON_LOCALERROR, new CalculateWinnerError());
        ////////////////////////////////////////////////////////
        // Update neuron weights
        final int ndims = ngroup.noAttributes();
        // Update winner neuron's weight
        Neuron winner = winners.get(0);
        // Increase winner neuron's connection's age
        winner.connections.incerementConnectionsEdgeValue();
    }
    protected void afterWinnersUpdate (NeuronGroup ngroup, List<Neuron> winners, Instance signal) {
        // Empty body
    }
    // Update GNG /////////////////////////////////////
    protected void onGNGUpdate (NeuronGroup ngroup, List<Neuron> winners, Instance signal) {
        ////////////////////////////////////////////////////////
        // Remove neuron's connections which their ages are more than maximum
        removeConnectionsWithHighAge(ngroup);
        ////////////////////////////////////////////////////////
        // Remove neurons with no connections
        removeNeuronWithoutConnection(ngroup);
        ////////////////////////////////////////////////////////
    }
    protected boolean neuronInsertionConditions (NeuronGroup ngroup, Instance signal) {
        int sindex = (Integer) ngroup.getParameter(NeuronGroup.RECIEVED_SIGNALS_NUM);
        int landa = (Integer) ngroup.getParameter(GNG_LANDA_KEY);
        return (sindex + 1) % landa == 0;
    }
    protected void onNeuronInsertion (NeuronGroup ngroup, Instance signal, List<Neuron> winners) {
        float aErrorRate = (Float) ngroup.getParameter(GNG_ERRORRATE_A_KEY);
        // Find the neural unit with highest accumulated error
        List<Neuron> ns = ngroup.neurons.toList();
        Neuron highestLE = (Neuron) ns.get(0);
        for (int index = 1; index < ns.size(); index++) {
            float hn = (Float) highestLE.getParameter(NEURON_LOCALERROR);
            float tempn = (Float) ns.get(index).getParameter(NEURON_LOCALERROR);
            if (hn < tempn) {
                highestLE = ns.get(index);
            }
        }
        // Find the neighbor of q which has the highest accumulated error
        List<Neuron> neighbors = highestLE.connections.neighbors();
        Neuron hMaxLENEighbor = neighbors.get(0);
        for (int index = 1; index < neighbors.size(); index++) {
            Neuron temp2 = neighbors.get(index);
            float maxle = (Float) hMaxLENEighbor.getParameter(NEURON_LOCALERROR);
            float tle = (Float) temp2.getParameter(NEURON_LOCALERROR);
            if (tle > maxle) {
                hMaxLENEighbor = temp2;
            }
        }
        // Put new neuron in to the system
        Neuron newNeuron = initNewNeuron(ngroup, signal, winners.get(0), winners.get(1));
        ngroup.addInternalNeuron(newNeuron);
        // Remove Edges between the selected neurons
//        ngroup.removeInternalConnection(new Connection(winners.get(0), winners.get(1)));
        // Add edge between new node and f and q
        ngroup.updateInternalConnection(new Connection(newNeuron, winners.get(0), 0));
        ngroup.updateInternalConnection(new Connection(newNeuron, winners.get(1), 0));
        // Prepare error vector for new node
        // Decrease Error value of neuron f and q
        winners.get(0).processOnParameter(NEURON_LOCALERROR, new UpdateErrorNeuron(aErrorRate));
        winners.get(1).processOnParameter(NEURON_LOCALERROR, new UpdateErrorNeuron(aErrorRate));
    }
    protected void afterNeuronInsertion (NeuronGroup ngroup, Instance signal)  {
        // Empty body
    }
    protected void onGNGLastStep (NeuronGroup ngroup, Instance signal) {
        // Decrease error of all units
        float bErrorRate = (Float) ngroup.getParameter(GNG_ERRORRATE_B_KEY);
        ngroup.neurons.toList().stream().parallel().forEach((Neuron x) -> {
            x.processOnParameter(NEURON_LOCALERROR, new UpdateErrorNeuron(bErrorRate));
        });
    }
    protected void onGNGFinalization (NeuronGroup ngroup, Instance signal, List<Neuron> result) {
        // Empty body
    }

    @Override
    protected boolean superviseOperator (Neuron neuron, Instance signal, List<Neuron> result) {
        NeuronGroup ngroup = (NeuronGroup) neuron;
        if (ngroup.neurons.size() < 2) {
            // Before Initial Phase
            beforeInitialPhase(ngroup, signal);
            // On Initial Phase
            Neuron nn = onInitialPhase(ngroup, signal);
            result.add(nn);
            // After Initial Phase
            afterInitialPhase(ngroup, signal, nn);
        } else {
            // On Distance Calculation phase
            onDistanceCalculation (ngroup, signal);
            // After Distance Calculation phase
            afterDistanceCalculation(ngroup, signal);
            // Find out two best neurons
            List<Neuron> winners = onWinnersFinding(ngroup, signal);
            // After Winners finding stage
            afterWinnersFinding(ngroup, winners, signal);
            ////////////////////////////////////////////////////////
            onWinnersUpdate(ngroup, winners, signal);
            // After Winners update stage
            afterWinnersUpdate(ngroup, winners, signal);
            // GNG update
            onGNGUpdate(ngroup, winners, signal);
            // Insert new neuron
            if (neuronInsertionConditions(ngroup, signal)) {
                onNeuronInsertion(ngroup, signal, winners);
                afterNeuronInsertion(ngroup, signal);
            }
            onGNGLastStep(ngroup, signal);
            result.addAll(winners);
        }
        
        onGNGFinalization(ngroup, signal, result);
        return true;
    }

    @Override
    public String getName() {
        return "gng.train";
    }
    protected Neuron initNewNeuron(NeuronGroup ngroup,
                                   Instance signal,
                                   Neuron firstn, 
                                   Neuron secondn) {
        double [] newWeight = new double [signal.noAttributes()];
        for (int index = 0; index < newWeight.length; index++) {
            newWeight[index] = signal.value(index);
        }
        Neuron newNeuron = initializeGNGNeuron(ngroup, new Neuron(newWeight));
        float errorHighest = (Float) firstn.getParameter(NEURON_LOCALERROR);
        float errorMax = (Float) secondn.getParameter(NEURON_LOCALERROR);
        newNeuron.setParameter(NEURON_LOCALERROR, (errorHighest + errorMax) / 2);
        return newNeuron;
    }

    protected void removeConnectionsWithHighAge(NeuronGroup ngroup) {
        int maxAge = (Integer) ngroup.getParameter(GNG_MAX_EDGE_AGE_KEY);
        ngroup.neurons.toList().stream().parallel().forEach((Neuron x) -> {
            x.connections.removeConnectionWithHigherValue(maxAge);
        });
    }
    protected void removeNeuronWithoutConnection(NeuronGroup ngroup) {
        LinkedList<Neuron> ns = new LinkedList<>(ngroup.neurons.toList());
        ns.stream().filter((Neuron x) -> x.connections.size() <= 0).forEach((Neuron n) -> {
            ngroup.neurons.remove(n);
        });
    }

    @Override
    protected NNResult prepareResult(Neuron neuron, Instance signal, List<Neuron> winners, NNResult resc) {
        resc.parameters = new ParametersContainer ();
        resc.setParameter(Neuron.SYSTEM_STATUS, neuron.getParameter(Neuron.SYSTEM_STATUS));
        resc.setParameter(Neuron.NEURON_ID, neuron.getParameter(Neuron.NEURON_ID));
        resc.setParameter(Neuron.NEURON_CENTROID, neuron.getParameter(Neuron.NEURON_CENTROID));
        resc.setParameter(Neuron.NEURON_DIMENSION, neuron.getParameter(Neuron.NEURON_DIMENSION));
        resc.setParameter(Neuron.NEURON_NUM_CHANNEL, neuron.getParameter(Neuron.NEURON_NUM_CHANNEL));
        resc.setParameter(Neuron.NUM_CONNECTIONS, neuron.getParameter(Neuron.NUM_CONNECTIONS));
        resc.setParameter(Neuron.RECIEVED_SIGNALS_NUM, neuron.getParameter(Neuron.RECIEVED_SIGNALS_NUM));
        resc.setParameter(NeuronGroup.NUM_NEURONS, neuron.getParameter(NeuronGroup.NUM_NEURONS));
        resc.setParameter(GNG_EPSILON_B_KEY, neuron.getParameter(GNG_EPSILON_B_KEY));
        resc.setParameter(GNG_EPSILON_N_KEY, neuron.getParameter(GNG_EPSILON_N_KEY));
        resc.setParameter(GNG_ERRORRATE_A_KEY, neuron.getParameter(GNG_ERRORRATE_A_KEY));
        resc.setParameter(GNG_ERRORRATE_B_KEY, neuron.getParameter(GNG_ERRORRATE_B_KEY));
        resc.setParameter(GNG_LANDA_KEY, neuron.getParameter(GNG_LANDA_KEY));
        resc.setParameter(GNG_MAX_EDGE_AGE_KEY, neuron.getParameter(GNG_MAX_EDGE_AGE_KEY));
        resc.setParameter(NEURON_DISTANCE, neuron.getParameter(NEURON_DISTANCE));
        resc.setParameter(NEURON_DIS_DIMENSION, neuron.getParameter(NEURON_DIS_DIMENSION));
        resc.setParameter(NEURON_LOCALERROR, neuron.getParameter(NEURON_LOCALERROR));
        return resc;
    }

    public class CalculateWinnerError implements ProcessOnParameter {
        @Override
        public Object process(Object data, ParametersContainer c) {
            float dis = (Float) c.get(NEURON_DISTANCE);
            float lerror = (Float) data;
            return lerror + (dis * dis);
        }
    }

    public class UpdateErrorNeuron implements ProcessOnParameter {
        float factor = 0;
        
        public UpdateErrorNeuron(float f) {
            factor = f;
        }

        @Override
        public Object process(Object data, ParametersContainer c) {
            float error = (Float) data;
            return error - (error * factor);
        }
    }
}
