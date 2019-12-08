package com.phm.ml.nn.algorithms.gng;

import com.phm.ml.nn.DefaultNeuronComparePolicy;
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
public class GNGTrainingSupervisor extends Supervisor {

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

    public GNGTrainingSupervisor(NeuronDistanceMethod dm) {
        distanceMethod = Objects.requireNonNull(dm);
        //netDimension = dim;
    }
    public GNGTrainingSupervisor () {
        this (new NeuronDistanceMethod (new DefaultDistanceMethod(new EuclideanDistance())));
    }

    @Override
    public void initialize(Neuron ngroup) {
        ngroup.setParameter(GNG_EPSILON_B_KEY, 0.05f);
        ngroup.setParameter(GNG_EPSILON_N_KEY, 0.0006f);
        ngroup.setParameter(GNG_MAX_EDGE_AGE_KEY, 88);
        ngroup.setParameter(GNG_LANDA_KEY, 400);
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

    // Initial Phase Stages ////////////////////////////////
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
    // Distance Calculation Stages //////////////////////////
    protected void onDistanceCalculation (NeuronGroup ngroup, Instance signal) {
        List<Neuron> ns = ngroup.neurons.toList();
        ns.stream().forEach((Neuron x) -> {
            DistanceInfo dd = distanceMethod.distance(ngroup, x, signal);
            x.setParameter(NEURON_DISTANCE, (float) dd.distance);
            x.setParameter(NEURON_DIS_DIMENSION, dd.distancedim);
            //x.setParameter(NEURON_DIS_CHANNELS, dd.channeldim);
        });
    }
    protected void afterDistanceCalculation (NeuronGroup ngroup, Instance signal) {
        // Empty body
    }
    // Find Winner Stages //////////////////////////////
    protected List<Neuron> onWinnersFinding (NeuronGroup ngroup, Instance signal) {
        LinkedList<Neuron> ns = new LinkedList<>(ngroup.neurons.toList());
//        Collections.sort(ns, COMPARE_POLICY);
        LinkedList<Neuron> result = new LinkedList<>();
//        result.add(ns.get(0));
//        result.add(ns.get(1));
        Neuron s0 = Collections.min(ns, COMPARE_POLICY);
        ns.remove(s0);
        Neuron s1 = Collections.min(ns, COMPARE_POLICY);
        result.add(s0);
        result.add(s1);
        return result;
    }
    protected void afterWinnersFinding (NeuronGroup ngroup, 
                                        List<Neuron> winners, 
                                        Instance signal) {
        // Empty body
    }
    // Update Winners stage /////////////////////////
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
        winners.get(0).connections.neighbors().stream().parallel().forEach((Neuron x) -> {
            DenseInstance dimDist = (DenseInstance) x.getParameter(NEURON_DIS_DIMENSION);
            for (int dim = 0; dim < ndims; dim++) {
                x.put(dim, x.value (dim) + (dimDist.value (dim) * epsilonN));
            }
        });
        // Update winner neuron's weight
        Neuron winner = winners.get(0);
        DenseInstance dimDist = (DenseInstance) winner.getParameter(NEURON_DIS_DIMENSION);
        final int ndim = winner.noAttributes();
        for (int dim = 0; dim < ndim; dim++) {
            winner.put (dim, winner.value(dim) + (dimDist.value(dim) * epsilonB));
        }
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
    protected void onNeuronInsertion (NeuronGroup ngroup, Instance signal) {
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
        Neuron newNeuron = initNewNeuron(ngroup, highestLE, hMaxLENEighbor);
        ngroup.addInternalNeuron(newNeuron);
        // Remove Edges between the selected neurons
        ngroup.removeInternalConnection(new Connection(highestLE, hMaxLENEighbor));
        // Add edge between new node and f and q
        ngroup.updateInternalConnection(new Connection(newNeuron, highestLE, 0));
        ngroup.updateInternalConnection(new Connection(newNeuron, hMaxLENEighbor, 0));
        // Prepare error vector for new node
        // Decrease Error value of neuron f and q
        highestLE.processOnParameter(NEURON_LOCALERROR, new UpdateErrorNeuron(aErrorRate));
        hMaxLENEighbor.processOnParameter(NEURON_LOCALERROR, new UpdateErrorNeuron(aErrorRate));
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
                onNeuronInsertion(ngroup, signal);
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
                                   Neuron firstn, 
                                   Neuron secondn) {
        double [] newWeight = new double [Math.min(firstn.noAttributes(),
                                                 secondn.noAttributes())];
        for (int index = 0; index < newWeight.length; index++) {
            newWeight[index] = (firstn.value (index) + 
                                secondn.value (index)) / 2;
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
        resc.setParameter(GNGTrainingSupervisor.GNG_EPSILON_B_KEY, neuron.getParameter(GNGTrainingSupervisor.GNG_EPSILON_B_KEY));
        resc.setParameter(GNGTrainingSupervisor.GNG_EPSILON_N_KEY, neuron.getParameter(GNGTrainingSupervisor.GNG_EPSILON_N_KEY));
        resc.setParameter(GNGTrainingSupervisor.GNG_ERRORRATE_A_KEY, neuron.getParameter(GNGTrainingSupervisor.GNG_ERRORRATE_A_KEY));
        resc.setParameter(GNGTrainingSupervisor.GNG_ERRORRATE_B_KEY, neuron.getParameter(GNGTrainingSupervisor.GNG_ERRORRATE_B_KEY));
        resc.setParameter(GNGTrainingSupervisor.GNG_LANDA_KEY, neuron.getParameter(GNGTrainingSupervisor.GNG_LANDA_KEY));
        resc.setParameter(GNGTrainingSupervisor.GNG_MAX_EDGE_AGE_KEY, neuron.getParameter(GNGTrainingSupervisor.GNG_MAX_EDGE_AGE_KEY));
        resc.setParameter(GNGTrainingSupervisor.NEURON_DISTANCE, neuron.getParameter(GNGTrainingSupervisor.NEURON_DISTANCE));
        resc.setParameter(GNGTrainingSupervisor.NEURON_DIS_DIMENSION, neuron.getParameter(GNGTrainingSupervisor.NEURON_DIS_DIMENSION));
        resc.setParameter(GNGTrainingSupervisor.NEURON_LOCALERROR, neuron.getParameter(GNGTrainingSupervisor.NEURON_LOCALERROR));
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
