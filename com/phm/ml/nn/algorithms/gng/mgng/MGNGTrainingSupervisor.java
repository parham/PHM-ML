
package com.phm.ml.nn.algorithms.gng.mgng;

import com.phm.ml.ParametersContainer;
import com.phm.ml.ProcessOnParameter; 
import com.phm.ml.distance.DistanceInfo;
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronAnalyzer;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.NeuronDistanceMethod;
import com.phm.ml.nn.algorithms.gng.GNGTrainingSupervisor;
import java.util.List;
import java.util.Objects;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm 
 */
public class MGNGTrainingSupervisor extends GNGTrainingSupervisor {

    public static final String NEURON_LOCAL_CONTEXT = "neuron.lcontext";
    
    ///// Merging GNG parameters
    public static final String MGNG_GLOBAL_CONTEXT = "mgng.gcontext";
    public static final String MGNG_NI = GNG_ERRORRATE_B_KEY;
    public static final String MGNG_DELTA = GNG_ERRORRATE_A_KEY;
    public static final String MGNG_ALPHA = "mgng.alpha";
    public static final String MGNG_BETA = "mgng.beta";
    public static final String MGNG_THETA = GNG_LANDA_KEY;
    
    public MGNGTrainingSupervisor (NeuronDistanceMethod dm) {
        super (dm);
    }
    public MGNGTrainingSupervisor () {
        this (new NeuronDistanceMethod(new MGNGContextDistanceMethod()));
    }
    
    @Override
    public void initialize (Neuron ngroup) {
        super.initialize(ngroup);
        double [] df = new double [ngroup.noAttributes()];
        ngroup.setParameter(MGNG_GLOBAL_CONTEXT, df);
        ngroup.setParameter(MGNG_NI, 0.9995f);
        ngroup.setParameter(MGNG_DELTA, 0.5f);
        ngroup.setParameter(MGNG_ALPHA, 0.5f);
        ngroup.setParameter(MGNG_BETA, 0.75f);
        ngroup.setParameter(MGNG_THETA, 100);
        ngroup.analyzers.add(new GlobalContextUpdateAnalyzer());
    }
    
    protected Neuron initializeMGNGNeuron (NeuronGroup ngroup, Neuron neuron) {
        this.initializeGNGNeuron (ngroup, neuron);
        neuron.setParameter(NEURON_LOCAL_CONTEXT, new float [neuron.noAttributes()]);
        return neuron;
    }
    @Override
    protected void onDistanceCalculation (NeuronGroup ngroup, Instance signal) {
        List<Neuron> ns = ngroup.neurons.toList();
        ns.stream().forEach((Neuron x) -> {
            ngroup.setParameter(NEURON_LOCAL_CONTEXT, x.getParameter(NEURON_LOCAL_CONTEXT));
            DistanceInfo dd = distanceMethod.distance(ngroup, x, signal);
            x.setParameter(NEURON_DISTANCE, (float) dd.distance);
            x.setParameter(NEURON_DIS_DIMENSION, dd.distancedim);
        });
    }
    @Override
    protected Neuron onInitialPhase(NeuronGroup ngroup, Instance buffer) {
        Neuron nn = initializeMGNGNeuron(ngroup, new Neuron(buffer));
        ngroup.addInternalNeuron(nn);
        return nn;
    }
    
    // Update Winners stage /////////////////////////
    @Override
    protected void onWinnersUpdate (NeuronGroup ngroup, 
                                    List<Neuron> winners, 
                                    Instance signal) {
        //float epsilonB = (Float) ngroup.getParameter(GNG_EPSILON_B_KEY);
        float epsilonN = (Float) ngroup.getParameter(GNG_EPSILON_N_KEY);
        // float [] buffer = signal.cdata(0);
        // Create Connection between two nearest neurons
        Connection c = new Connection(winners.get(0), winners.get(1), 0);
        ngroup.updateInternalConnection(c);
        ////////////////////////////////////////////////////////
        // Add squared error of winner unit to a local error
        winners.get(0).processOnParameter(NEURON_LOCALERROR, new CalculateWinnerError());
        ////////////////////////////////////////////////////////
        // Update neuron weights
        final float [] gc = (float []) ngroup.getParameter (MGNG_GLOBAL_CONTEXT);
        winners.get(0).connections.neighbors().stream().parallel().forEach((Neuron tx) -> {
            Neuron x = tx;
            DenseInstance dimDist = (DenseInstance) x.getParameter (NEURON_DIS_DIMENSION);
	    float [] lc = (float []) x.getParameter (NEURON_LOCAL_CONTEXT);
            final int ndims = signal.noAttributes();
            double [] cs = new double [ndims];
            for (int dim = 0; dim < ndims; dim++) {
                cs [dim] = x.value(dim) + (dimDist.value (dim) * epsilonN);
                x.put(dim, cs [dim]);
                lc [dim] += epsilonN * (gc [dim] - lc [dim]);
            }
	    x.setParameter(NEURON_LOCAL_CONTEXT, lc);
        });
        // Update winner neuron's weight
        final int ndims = signal.noAttributes();
        DenseInstance dimDist = (DenseInstance) winners.get(0).getParameter(NEURON_DIS_DIMENSION);
	float [] lc = (float []) winners.get(0).getParameter(NEURON_LOCAL_CONTEXT);
        for (int dim = 0; dim < ndims; dim++) {
            double tmp = winners.get(0).value(dim) + (dimDist.value(dim) * epsilonN);
            winners.get(0).put(dim, tmp);
            lc [dim] += epsilonN * (gc [dim] - lc [dim]);
        }
	winners.get(0).setParameter(NEURON_LOCAL_CONTEXT, lc);
        ////////////////////////////////////////////////////////
        // Increase winner neuron's connection's age
        winners.get(0).connections.incerementConnectionsEdgeValue();
    }
    
    @Override
    protected void onNeuronInsertion (NeuronGroup ngroup, Instance signal) {
        float delta = (Float) ngroup.getParameter(MGNG_DELTA);
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
        Neuron newNeuron = initNewNeuron (ngroup, highestLE, hMaxLENEighbor);
        ngroup.addInternalNeuron(newNeuron);
        // Remove Edges between the selected neurons
        ngroup.removeInternalConnection(new Connection(highestLE, hMaxLENEighbor));
        // Add edge between new node and f and q
        ngroup.updateInternalConnection(new Connection(newNeuron, highestLE, 0));
        ngroup.updateInternalConnection(new Connection(newNeuron, hMaxLENEighbor, 0));
            // Prepare error vector for new node
        // Decrease Error value of neuron f and q
        highestLE.processOnParameter(NEURON_LOCALERROR, new UpdateErrorNeuron(1 - delta));
        hMaxLENEighbor.processOnParameter(NEURON_LOCALERROR, new UpdateErrorNeuron(1 - delta));
    }
    
    @Override
    protected void onGNGLastStep (NeuronGroup ngroup, Instance signal) {
        float ni = (Float) ngroup.getParameter(MGNG_NI);
        // Decrease error of all units
        float bErrorRate = (Float) ngroup.getParameter(GNG_ERRORRATE_B_KEY);
        ngroup.neurons.toList().stream().parallel().forEach((Neuron x) -> {
            x.processOnParameter(NEURON_LOCALERROR, new UpdateErrorNeuron(ni));
        });
    }
    
    @Override
    public String getName() {
        return "mgng.train";
    }
    
    @Override
    protected Neuron initNewNeuron (NeuronGroup ngroup, 
                                    Neuron firstn, 
                                    Neuron secondn) {
        final int ndims = firstn.noAttributes();
        double [] newWeight = new double [ndims];
        float [] newLContext = new float [ndims];
        float [] flcontext = (float []) firstn.getParameter(NEURON_LOCAL_CONTEXT);
        float [] slcontext = (float []) secondn.getParameter(NEURON_LOCAL_CONTEXT);
        float delta = (Float) ngroup.getParameter(MGNG_DELTA);
        for (int index = 0; index < newWeight.length; index++) {
            newWeight [index] = (firstn.value(index) + secondn.value(index)) / 2;
            newLContext [index] = (flcontext [index] + slcontext [index]) / 2;
        }
        Neuron newNeuron = initializeMGNGNeuron (ngroup, new Neuron(newWeight));
        newNeuron.setParameter(NEURON_LOCAL_CONTEXT, newLContext);
        float errorHighest = (Float) firstn.getParameter(NEURON_LOCALERROR);
        float errorMax = (Float) secondn.getParameter(NEURON_LOCALERROR);
        newNeuron.setParameter(NEURON_LOCALERROR, (errorHighest + errorMax) * delta);
        return newNeuron;
    }
    
    @Override
    protected NNResult prepareResult(Neuron neuron, Instance signal, List<Neuron> winners, NNResult resc) {
        super.prepareResult(neuron, signal, winners, resc);
        resc.setParameter(MGNGTrainingSupervisor.MGNG_ALPHA, neuron.getParameter(MGNGTrainingSupervisor.MGNG_ALPHA));
        resc.setParameter(MGNGTrainingSupervisor.MGNG_BETA, neuron.getParameter(MGNGTrainingSupervisor.MGNG_BETA));
        resc.setParameter(MGNGTrainingSupervisor.MGNG_DELTA, neuron.getParameter(MGNGTrainingSupervisor.MGNG_DELTA));
        resc.setParameter(MGNGTrainingSupervisor.MGNG_GLOBAL_CONTEXT, neuron.getParameter(MGNGTrainingSupervisor.MGNG_GLOBAL_CONTEXT));
        resc.setParameter(MGNGTrainingSupervisor.MGNG_NI, neuron.getParameter(MGNGTrainingSupervisor.MGNG_NI));
        resc.setParameter(MGNGTrainingSupervisor.MGNG_THETA, neuron.getParameter(MGNGTrainingSupervisor.MGNG_THETA));
        resc.setParameter(MGNGTrainingSupervisor.NEURON_LOCAL_CONTEXT, neuron.getParameter(MGNGTrainingSupervisor.NEURON_LOCAL_CONTEXT));
        return resc;
    }
    
    public class GlobalContextUpdateAnalyzer implements NeuronAnalyzer {
        @Override
        public void analysis(String state, Neuron n, 
                ParametersContainer param, Instance current, NNResultContainer result) {
            if (state.contentEquals(getName())) {
                Neuron nWinner = result.getRecent().winners.get(0);
                param.processOnParameter(MGNG_GLOBAL_CONTEXT, new GlobalContextUpdate((NeuronGroup) n, nWinner));
            }
        }
    }
    
    protected class GlobalContextUpdate implements ProcessOnParameter {
        Neuron nWinner;
        NeuronGroup parent;
        public GlobalContextUpdate (NeuronGroup ng, Neuron winner) {
            nWinner = Objects.requireNonNull(winner);
            parent = Objects.requireNonNull(ng);
        }

        @Override
        public Object process(Object data, ParametersContainer c) {
            float [] lc = (float []) nWinner.getParameter(NEURON_LOCAL_CONTEXT);
            float beta = (Float) parent.getParameter(MGNG_BETA);
            
            final int ndims = nWinner.noAttributes();
            //float [] wd = (float []) nWinner.centroid.cdata(ch);
            float [] gc = new float [ndims];
            for (int dim = 0; dim < ndims; dim++) {
                gc [dim] = (float) (((1 - beta) * nWinner.value(dim)) + (beta * lc [dim]));
            }
            return gc;
        }
    }
}