
package com.phm.ml.nn.algorithms.gcs;

import com.phm.ml.ParametersContainer;
import com.phm.ml.distance.DefaultDistanceMethod;
import com.phm.ml.distance.DistanceInfo;
import com.phm.ml.nn.DefaultNeuronComparePolicy;
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.Supervisor;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronDistanceMethod;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.connection.Connection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;

/**
 *
 * @author phm
 */
public class GCSTrainingSupervisor extends Supervisor {

    public static final String GCS_LAMBA = "gcs.lamba";
    public static final String GCS_EPSILON_B = "gcs.epsilon.b";
    public static final String GCS_EPSILON_N = "gcs.epsilon.n";
    public static final String GCS_ALPHA = "gcs.alpha";
    public static final String GCS_BETA = "gcs.beta";
    
    public static final String NEURON_DISTANCE = "neuron.distance";
    public static final String NEURON_DIS_DIMENSION = "neuron.local.distance";
    public static final String NEURON_LOCALERROR = "neuron.local.error";
    
    protected final DefaultNeuronComparePolicy COMPARE_POLICY = new DefaultNeuronComparePolicy ();
    protected final NeuronDistanceMethod distanceMethod;
    
    public GCSTrainingSupervisor () {
        this (new NeuronDistanceMethod(new DefaultDistanceMethod(new EuclideanDistance())));
    }
    public GCSTrainingSupervisor (NeuronDistanceMethod dm) {
        distanceMethod = Objects.requireNonNull(dm);
    }
    
    @Override
    public String getName() {
        return "gcs.train";
    }

    @Override
    public void initialize(Neuron neuron) {
        NeuronGroup ngroup = (NeuronGroup) neuron;
        ngroup.setParameter(GCS_EPSILON_B, 0.06f);
        ngroup.setParameter(GCS_EPSILON_N, 0.002f);
        ngroup.setParameter(GCS_LAMBA, 200);
        ngroup.setParameter(GCS_ALPHA, 1.0f);
        ngroup.setParameter(GCS_BETA, 0.0005f);
    }
    
    protected Neuron initNewGCSNeuron (Neuron neuron) {
        neuron.setParameter(NEURON_DISTANCE, 0.0f);
        neuron.setParameter(NEURON_DIS_DIMENSION, 0.0f);
        neuron.setParameter(NEURON_LOCALERROR, 0.0f);
        return neuron;
    }
    
    protected void initConnections (NeuronGroup ngroup) {
        List<Neuron> ns = ngroup.neurons.toList();
        for (int index = 0; index < ns.size() - 1; index++) {
            Neuron n = ns.get(index);
            for (int rin = index + 1; rin < ns.size(); rin++) {
                Neuron rn = ns.get(rin);
                addConnection(n, rn);
            }
        }
    }
    
    protected void onDistanceCalculation (NeuronGroup ngroup, Instance s) {
        ngroup.neurons.stream().forEach((n) -> {
            DistanceInfo dd = distanceMethod.distance (ngroup, n, s);
            n.setParameter(NEURON_DISTANCE, (float) dd.distance);
            n.setParameter(NEURON_DIS_DIMENSION, dd.distancedim);
        });
    }
    protected void afterDistanceCalculation (NeuronGroup ngroup, Instance s) {
        // Empty body
    }
    
    protected List<Neuron> onFindWinnerNeurons (NeuronGroup ngroup, Instance s) {
//        Neuron winner = ngroup.neurons.getIndex(0);
        LinkedList<Neuron> list = new LinkedList<>(ngroup.neurons.toList());
        Neuron winner = Collections.min (list, COMPARE_POLICY);
//        for (Neuron n : ngroup.neurons.toList()) {
//            if (COMPARE_POLICY.compare(winner, n) > 0) {
//                winner = n;
//            }
//        }
        LinkedList<Neuron> res = new LinkedList<>();
        res.add(winner);
        return res;
    }
    
    protected void afterFindWinnerNeurons (NeuronGroup ngroup, Instance s, List<Neuron> result) {
        // Empty body
    }
    
    protected void addConnection (Neuron n1, Neuron n2) {
        n1.connections.add(n2);
        n2.connections.add(n1);
    }
    protected void removeConnection (Neuron n1, Neuron n2) {
        n1.connections.remove(new Connection(n1, n2));
        n2.connections.remove(new Connection(n2, n1));
    }
    
    protected List<Neuron> onFindHighestErrorNeurons (NeuronGroup ngroup, Instance s, List<Neuron> result) {
        List<Neuron> list = ngroup.neurons.toList();
        // Find highest error neuron
        Neuron q = list.get(0);
        float qerr = (float) q.getParameter(NEURON_LOCALERROR);
        for (Neuron n : list) {
            float err = (float) n.getParameter(NEURON_LOCALERROR);
            if (err > qerr) {
                q = n;
                qerr = err;
            }
        }
        // Find second highest error neuron
        List<Neuron> neighbors = q.connections.neighbors();
        Neuron f = neighbors.get(0);
        float longest = 0;
        for (Neuron ns : neighbors) {
            Neuron nst = ns;
            float dist = 0;
            for (int index = 0; index < nst.noAttributes(); index++) {
                dist += ((nst.value (index) - q.value (index)) * 
                         (nst.value (index) - q.value (index)));
            }
            dist = (float) Math.sqrt(dist);
            if (longest < dist) {
                f = ns;
                longest = dist;
            }
        }
        
        LinkedList<Neuron> res = new LinkedList<>();
        res.add(q);
        res.add(f);
        return res;
    }
    protected void solveNewNeuronNeighbors (NeuronGroup ngroup, 
                                            Neuron q, 
                                            Neuron f, 
                                            Neuron newn) {
        List<Neuron> neighbors = q.connections.neighbors();
        for (Neuron n : neighbors) {
            if (f.connections.contains(f, n)) {
                addConnection(newn, n);
            }
        }
    }
    
    protected void onUpdateWinnerNeurons (NeuronGroup ngroup, Instance s, List<Neuron> result) {
        final int lamba = (int) ngroup.getParameter(GCS_LAMBA);
        final int numSignal = (int) ngroup.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
        final float alpha = (float) ngroup.getParameter(GCS_ALPHA);
        final float beta = (float) ngroup.getParameter(GCS_BETA);
        // Update Winner Local Error
        float dis = (float) result.get(0).getParameter(GCSTrainingSupervisor.NEURON_DISTANCE);
        float de = (float) dis * dis;
        result.get(0).processOnParameter(NEURON_LOCALERROR, new Neuron.AddValueTo(de));
        ///////////////////////
        final float episB = (float) ngroup.getParameter(GCS_EPSILON_B);
        final float episN = (float) ngroup.getParameter(GCS_EPSILON_N);
        // Update Winner
        //final float [] signal = s.cdata(0);
        Neuron win = result.get(0);
        //final float [] disdWin = (float []) win.getParameter(NEURON_DIS_DIMENSION);
        for (int index = 0; index < win.noAttributes(); index++) {
            double v = win.value(index);
            v += (s.value(index) - win.value (index)) * episB;
            win.put(index, v);
        }
        // Update Neighbors
        List<Neuron> neighbors = win.connections.neighbors();
        for (Neuron n : neighbors) {
            for (int index = 0; index < n.noAttributes(); index++) {
                double v = n.value(index);
                v += (s.value(index) - n.value(index)) * episN;
                n.put(index, v);
            }
        }
        
        if (numSignal % lamba == 0) {
            List<Neuron> nes = onFindHighestErrorNeurons(ngroup, s, result);
            Neuron q = nes.get(0);
            Neuron f = nes.get(1);
            // Insert new neuron
            double [] cnew = new double [ngroup.noAttributes()];
            for (int index = 0; index < cnew.length; index++) {
                cnew [index] = (q.value (index) + f.value (index)) / 2;
            }
            Neuron newNeuron = initNewGCSNeuron(new Neuron (cnew));
            ngroup.addInternalNeuron(newNeuron);
            // new Connection 
            addConnection(q, newNeuron);
            addConnection(newNeuron, f);
            // Solve New Neuron Neighbors
            solveNewNeuronNeighbors(ngroup, q, f, newNeuron);
            LinkedList<Neuron> alln = new LinkedList<>(ngroup.neurons.toList());
            List<Neuron> nnewn = newNeuron.connections.neighbors();
            float avgerr = 0;
            for (Neuron n : nnewn) {
                float err = (float) n.getParameter(NEURON_LOCALERROR);
                err += (-alpha / (float) nnewn.size()) * err;
                n.setParameter(NEURON_LOCALERROR, err);
                alln.remove(n);
                avgerr += err;
            }
            avgerr /= (float) nnewn.size();
            newNeuron.setParameter(NEURON_LOCALERROR, avgerr);
            // Update all units local error
            alln = new LinkedList<>(ngroup.neurons.toList());
            for (Neuron n : alln) {
                float err = (float) n.getParameter(NEURON_LOCALERROR);
                err += -beta * err;
                n.setParameter(NEURON_LOCALERROR, err);
            }
        }
    }

    @Override
    protected boolean superviseOperator(Neuron neuron, Instance signal, List<Neuron> result) {
        NeuronGroup ngroup = (NeuronGroup) neuron;
        if (ngroup.neurons.size() < (neuron.noAttributes() + 1)) {
            Neuron newNeuron = initNewGCSNeuron(new Neuron (signal));
            ngroup.addInternalNeuron(newNeuron);
            result.add(newNeuron);
            if (ngroup.neurons.size() == (ngroup.noAttributes() + 1)) {
                initConnections (ngroup);
            }
        } else {
            onDistanceCalculation(ngroup, signal);
            afterDistanceCalculation(ngroup, signal);
            List<Neuron> winners = onFindWinnerNeurons(ngroup, signal);
            result.addAll(winners);
            afterFindWinnerNeurons(ngroup, signal, winners);
            onUpdateWinnerNeurons(ngroup, signal, winners);
        }
        
        return true;
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
        resc.setParameter(GCSTrainingSupervisor.GCS_ALPHA, neuron.getParameter(GCSTrainingSupervisor.GCS_ALPHA));
        resc.setParameter(GCSTrainingSupervisor.GCS_BETA, neuron.getParameter(GCSTrainingSupervisor.GCS_BETA));
        resc.setParameter(GCSTrainingSupervisor.GCS_EPSILON_B, neuron.getParameter(GCSTrainingSupervisor.GCS_EPSILON_B));
        resc.setParameter(GCSTrainingSupervisor.GCS_EPSILON_N, neuron.getParameter(GCSTrainingSupervisor.GCS_EPSILON_N));
        resc.setParameter(GCSTrainingSupervisor.GCS_LAMBA, neuron.getParameter(GCSTrainingSupervisor.GCS_LAMBA));
        resc.setParameter(GCSTrainingSupervisor.NEURON_DISTANCE, neuron.getParameter(GCSTrainingSupervisor.NEURON_DISTANCE));
        resc.setParameter(GCSTrainingSupervisor.NEURON_DIS_DIMENSION, neuron.getParameter(GCSTrainingSupervisor.NEURON_DIS_DIMENSION));
        resc.setParameter(GCSTrainingSupervisor.NEURON_LOCALERROR, neuron.getParameter(GCSTrainingSupervisor.NEURON_LOCALERROR));
        return resc;
    }
}
