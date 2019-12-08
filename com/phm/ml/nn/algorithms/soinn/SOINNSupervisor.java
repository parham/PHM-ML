
package com.phm.ml.nn.algorithms.soinn;

import com.phm.ml.distance.DefaultDistanceMethod;
import com.phm.ml.distance.DistanceInfo;
import com.phm.ml.nn.DefaultNeuronComparePolicy;
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronDistanceMethod;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.Supervisor;
import com.phm.ml.nn.connection.Connection;
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
public class SOINNSupervisor extends Supervisor {
    
    public static final String NEURON_CODEDSIGNAL = "neuron.coded.signals";
    
    public static final String NEURON_DISTANCE = "neuron.distance";
    public static final String NEURON_DIS_DIMENSION = "neuron.local.distance";
    public static final String NEURON_LOCALERROR = "neuron.local.error";
    
    public static final String SOINN_MAX_EDGE_AGE_KEY = "max.edge.age";
    // number signal generations needed to insert new node
    public static final String SOINN_LANDA_KEY = "landa.key";
    
    public static final DefaultNeuronComparePolicy COMPARE_POLICY = new DefaultNeuronComparePolicy();
    protected final NeuronDistanceMethod distanceMethod;
    
    public SOINNSupervisor (NeuronDistanceMethod dm) {
        distanceMethod = Objects.requireNonNull(dm);
        //netDimension = dim;
    }
    public SOINNSupervisor () {
        this (new NeuronDistanceMethod (new DefaultDistanceMethod(new EuclideanDistance())));
    }
    
    @Override
    public void initialize(Neuron ngroup) {
        ngroup.setParameter(SOINN_MAX_EDGE_AGE_KEY, 88);
        ngroup.setParameter(SOINN_LANDA_KEY, 800);
    }
    
    public NeuronDistanceMethod getDistanceMethod() {
        return distanceMethod;
    }
    
    protected void beforeInitialPhase(NeuronGroup ngroup, Instance signal) {
        // Empty body
    }
    
    protected Neuron onInitialPhase(NeuronGroup ngroup, Instance buffer) {
        Neuron nn = initializeSOINNNeuron (ngroup, new Neuron (buffer));
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
        LinkedList<Neuron> result = new LinkedList<>();
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
    protected void afterWinnersUpdate (NeuronGroup ngroup, List<Neuron> winners, Instance signal) {
        // Empty body
    }
    protected void onSOINNUpdate (NeuronGroup ngroup, List<Neuron> winners, Instance signal) {

    }
    protected void removeConnectionsWithHighAge(NeuronGroup ngroup) {
        int maxAge = (Integer) ngroup.getParameter(SOINN_MAX_EDGE_AGE_KEY);
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
    
    protected void onSOINNLastStep (NeuronGroup ngroup, Instance signal) {
        int sindex = (Integer) ngroup.getParameter(NeuronGroup.RECIEVED_SIGNALS_NUM);
        int landa = (Integer) ngroup.getParameter(SOINN_LANDA_KEY);
        if ((sindex + 1) % landa == 0) {
            LinkedList<Neuron> ns = new LinkedList<>(ngroup.neurons.toList());
            ns.stream().forEach((Neuron x) -> {
                if (x.connections.size() <= 1) {
                    ngroup.removeInternalNeuron(x);
                }
            });
        }
    }
    protected void onSOINNFinalization (NeuronGroup ngroup, Instance signal, List<Neuron> result) {
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
            onSOINNUpdate(ngroup, winners, signal);
            onSOINNLastStep(ngroup, signal);
            result.addAll(winners);
        }
        
        onSOINNFinalization(ngroup, signal, result);
        return true;
    }
    
    @Override
    public String getName() {
        return "soinn.train";
    }
    
    protected Neuron initializeSOINNNeuron(NeuronGroup ngroup, Neuron neuron) {
        neuron.setParameter(NEURON_DISTANCE, 0.0f);
        neuron.setParameter(NEURON_DIS_DIMENSION, new DenseInstance(new double [neuron.noAttributes()]));
        neuron.setParameter(NEURON_LOCALERROR, 0.0f);
        neuron.setParameter(NEURON_CODEDSIGNAL, 1.0f);
        return neuron;
    }
    
    protected void updateNeuron (NeuronGroup ngroup, Neuron n, float weight, float codedsignals) {
        double epsilon = 1.0f / codedsignals;
        final int ndim = n.noAttributes();
        DenseInstance dimDist = (DenseInstance) n.getParameter(NEURON_DIS_DIMENSION);
        for (int dim = 0; dim < ndim; dim++) {
            n.put (dim, n.value(dim) + (dimDist.value(dim) * epsilon * weight));
        }
    }
    
    protected void onWinnersUpdate (NeuronGroup ngroup, 
                                    List<Neuron> winners, 
                                    Instance signal) {
        
        double fsimular = getSimularThreshold(ngroup, winners.get(0));
        double ssimular = getSimularThreshold(ngroup, winners.get(1));
        double fdis = (float) winners.get(0).getParameter(NEURON_DISTANCE);
        double sdis = (float) winners.get(1).getParameter(NEURON_DISTANCE);
        
        if (fdis > fsimular || sdis > ssimular) {
            Neuron nnew = new Neuron(signal);
            ngroup.addInternalNeuron(initializeSOINNNeuron(ngroup, nnew));
        }
        
        if (fdis <= fsimular &&
            sdis <= ssimular) {
            // Create Connection between two nearest neurons
            Connection c = new Connection(winners.get(0), winners.get(1), 0);
            ngroup.updateInternalConnection(c);
            
            Neuron winner = winners.get(0);
            float codedsigs = (float) winner.getParameter(NEURON_CODEDSIGNAL);
            winner.processOnParameter(NEURON_CODEDSIGNAL, new Neuron.AddValueTo(1.0f));
            // Increase winner neuron's connection's age
            winner.connections.incerementConnectionsEdgeValue();
            // Update winner neuron's weight
            updateNeuron(ngroup, winner, 1.0f, codedsigs);

            final int ndims = ngroup.noAttributes();
            winners.get(0).connections.neighbors().stream().parallel().forEach((Neuron x) -> {
                float tcoded = (float) x.getParameter(NEURON_CODEDSIGNAL);
                updateNeuron(ngroup, x, 0.01f, tcoded);
            });
            
            ////////////////////////////////////////////////////////
            // Remove neuron's connections which their ages are more than maximum
            removeConnectionsWithHighAge(ngroup);
            ////////////////////////////////////////////////////////
            // Remove neurons with no connections
            removeNeuronWithoutConnection(ngroup);
            ////////////////////////////////////////////////////////
        }
    }
    
    protected double getSimularThreshold (NeuronGroup ng, Neuron target) {
        if (target.connections.size() != 0) {
            LinkedList<Neuron> related = new LinkedList<>(target.connections.neighbors());
            if (related.size() == 1) {
                Neuron rn = related.get(0);
                DistanceInfo dinfo = distanceMethod.distance(ng, rn, target);
                return dinfo.distance;
            } else {
                Neuron farn = related.get(0);
                DistanceInfo dinfo = distanceMethod.distance(ng, farn, target);
                double farv = dinfo.distance;
                for (Neuron x : related) {
                    dinfo = distanceMethod.distance(ng, x, target);
                    if (farv < dinfo.distance) {
                        farn = x;
                        farv = dinfo.distance;
                    }
                }
                return farv;
            }
        } else {
            LinkedList<Neuron> related = new LinkedList<>(ng.neurons.toList());
            related.remove(target);
            Neuron farn = related.get(0);
            DistanceInfo dinfo = distanceMethod.distance(ng, farn, target);
            double farv = dinfo.distance;
            for (Neuron x : related) {
                dinfo = distanceMethod.distance(ng, x, target);
                if (farv > dinfo.distance) {
                    farn = x;
                    farv = dinfo.distance;
                }
            }
            return farv;
        }
    }

    @Override
    protected NNResult prepareResult(Neuron neuron, Instance signal, List<Neuron> winners, NNResult resc) {
        return resc;
    }
}
