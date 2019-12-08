
package com.phm.ml.nn.algorithms.som;

import com.phm.ml.ParametersContainer;
import com.phm.ml.distance.DistanceInfo;
import com.phm.ml.nn.DefaultNeuronComparePolicy;
import com.phm.ml.nn.Supervisor;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.NeuronDistanceMethod;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class KohonenTrainingSupervisor extends Supervisor {
    
    public static final String KOHONEN_WIDTH_DIMENSION = "kohonen.width.dimensions";
    public static final String KOHONEN_HEIGHT_DIMENSION = "kohonen.height.dimensions";
    public static final String KOHONEN_NEURONS_GRIDS = "kohonen.neurons.grids";
    
    public static final String KOHONEN_DEVIATION_INIT = "kohonen.deviation.init";
    public static final String KOHONEN_DEVIATION_FINAL = "kohonen.deviation.final";
    public static final String KOHONEN_TIME_CONSTANT = "kohonen.time.constant";
    
    public static final String KOHONEN_EPSILON_INIT = "kohonen.epsilon.init";
    public static final String KOHONEN_EPSILON_FINAL = "kohonen.epsilon.final";
    
    public static final String NEURON_DISTANCE = "neuron.distance";
    public static final String NEURON_UPDATE_DISTANCE = "neuron.update.distance";
    public static final String NEURON_DIS_DIMENSION = "neuron.local.distance";
    public static final String NEURON_GRID_X = "neuron.grid.x";
    public static final String NEURON_GRID_Y = "neuron.grid.y";
    
    protected final DefaultNeuronComparePolicy COMPARE_POLICY = new DefaultNeuronComparePolicy();
    
    protected final KohonenInitializer initializer;
    protected final NeuronDistanceMethod distanceMethod;
    protected final NeuronDistanceMethod updateDistanceMethod;
    protected final int numInWidth;
    protected final int numInHeight;
    protected final int maxSignals;
    
    public KohonenTrainingSupervisor (KohonenInitializer init, 
                                      NeuronDistanceMethod dm, 
                                      NeuronDistanceMethod udm, 
                                      int height, int width, int maxs) {
        initializer = Objects.requireNonNull(init);
        distanceMethod = Objects.requireNonNull(dm);
        updateDistanceMethod = Objects.requireNonNull(udm);
        numInWidth = width;
        numInHeight = height;
        maxSignals = maxs;
    }
    
    @Override
    public String getName() {
        return "kohonen.train";
    }

    @Override
    public void initialize(Neuron neuron) {
        NeuronGroup ngroup = (NeuronGroup) neuron;
        ngroup.setParameter(KOHONEN_WIDTH_DIMENSION, numInWidth);
        ngroup.setParameter(KOHONEN_HEIGHT_DIMENSION, numInHeight);
        ngroup.setParameter(KOHONEN_TIME_CONSTANT, maxSignals);
        ngroup.setParameter(KOHONEN_NEURONS_GRIDS, new float [numInHeight][numInWidth]);
        ngroup.setParameter(KOHONEN_DEVIATION_INIT, 3.0f);
        ngroup.setParameter(KOHONEN_DEVIATION_FINAL, 0.1f);
        ngroup.setParameter(KOHONEN_EPSILON_INIT, 0.5f);
        ngroup.setParameter(KOHONEN_EPSILON_FINAL, 0.005f);
        
        initializer.initialize(ngroup, this);
    }
    
    public Neuron initNewSOMNeuron (Neuron neuron) {
        neuron.setParameter(KohonenTrainingSupervisor.NEURON_GRID_X, 0);
        neuron.setParameter(KohonenTrainingSupervisor.NEURON_GRID_Y, 0);
        neuron.setParameter(KohonenTrainingSupervisor.NEURON_DISTANCE, 0.0f);
        neuron.setParameter(KohonenTrainingSupervisor.NEURON_UPDATE_DISTANCE, 0.0f);
        neuron.setParameter(KohonenTrainingSupervisor.NEURON_DIS_DIMENSION, new float [2]);
        return neuron;
    }
    
    public void addInternalConnection (Neuron n1, Neuron n2) {
        n1.connections.add(n1, n2);
        n2.connections.add(n2, n1);
    }
    
    protected void onDistanceCalculation (NeuronGroup ngroup, Instance s) {
        //final float [] signal = s.cdata(0);
        for (Neuron n : ngroup.neurons) {
            DistanceInfo dd = distanceMethod.distance(ngroup, n, s);
            n.setParameter(NEURON_DISTANCE, (float) dd.distance);
            n.setParameter(NEURON_DIS_DIMENSION, dd.distancedim);
        }
    }
    protected void afterDistanceCalculation (NeuronGroup ngroup, Instance s) {
        // Empty body
    }
    protected List<Neuron> onFindWinnerNeuron (NeuronGroup ngroup, Instance s) {
        Neuron winner = ngroup.neurons.getIndex(0);
        for (Neuron n : ngroup.neurons.toList()) {
            if (COMPARE_POLICY.compare(winner, n) > 0) {
                winner = n;
            }
        }
        LinkedList<Neuron> res = new LinkedList<>();
        res.add(winner);
        return res;
    }
    protected void onUpdateNeurons (NeuronGroup ngroup, Instance s, List<Neuron> winner) {
        final Neuron win = winner.get(0);
        //final float [] signal = s.cdata(0);
        final int time = (int) ngroup.getParameter(Neuron.RECIEVED_SIGNALS_NUM);
        final int timeConstant = (int) ngroup.getParameter(KOHONEN_TIME_CONSTANT);
        
        final float deviInit = (float) ngroup.getParameter(KOHONEN_DEVIATION_INIT);
        final float deviFinal = (float) ngroup.getParameter(KOHONEN_DEVIATION_FINAL);
        final float devi = (float) (deviInit * Math.pow((deviFinal / deviInit), time / timeConstant));
        
        final float epsilonInit = (float) ngroup.getParameter(KOHONEN_EPSILON_INIT);
        final float epsilonFinal = (float) ngroup.getParameter(KOHONEN_EPSILON_FINAL);
        final float epsilon = (float) (epsilonInit * Math.pow((epsilonFinal / epsilonInit), time / timeConstant));
        //final 
        List<Neuron> neighbors = win.connections.neighbors();
        neighbors.add(win);
        //List<Neuron> neighbors = ngroup.neurons.toList();
        for (Neuron nx : neighbors) {
            Neuron n = nx;
            DistanceInfo dd = updateDistanceMethod.distance(ngroup, win, n);
            double dis = dd.distance;
            double Hrs = Math.exp(-Math.pow(dis, 2) / (2 * Math.pow(devi, 2)));
            double [] dw = new double [n.noAttributes()];
            for (int index = 0; index < dw.length; index++) {
                double v = n.value(index);
                v += epsilon * Hrs * (s.value(index) - n.value (index));
                n.put(index, v);
            }
            n.setParameter(NEURON_UPDATE_DISTANCE, dis);
        }
    }
    protected void afterUpdateNeurons (NeuronGroup ngroup, Instance s, List<Neuron> winner) {
        // Empty body
    }
    
    @Override
    protected boolean superviseOperator(Neuron neuron, Instance s, List<Neuron> result) {
        final NeuronGroup ngroup = (NeuronGroup) neuron;
        // final float [] signal = s.asSignalArray();
        onDistanceCalculation(ngroup, s);
        afterDistanceCalculation(ngroup, s);
        List<Neuron> winner = onFindWinnerNeuron(ngroup, s);
        onUpdateNeurons(ngroup, s, winner);
        afterUpdateNeurons(ngroup, s, winner);
        result.addAll(winner);
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
        resc.setParameter(KohonenTrainingSupervisor.KOHONEN_DEVIATION_FINAL, neuron.getParameter(KohonenTrainingSupervisor.KOHONEN_DEVIATION_FINAL));
        resc.setParameter(KohonenTrainingSupervisor.KOHONEN_DEVIATION_INIT, neuron.getParameter(KohonenTrainingSupervisor.KOHONEN_DEVIATION_INIT));
        resc.setParameter(KohonenTrainingSupervisor.KOHONEN_EPSILON_FINAL, neuron.getParameter(KohonenTrainingSupervisor.KOHONEN_EPSILON_FINAL));
        resc.setParameter(KohonenTrainingSupervisor.KOHONEN_EPSILON_INIT, neuron.getParameter(KohonenTrainingSupervisor.KOHONEN_EPSILON_INIT));
        resc.setParameter(KohonenTrainingSupervisor.KOHONEN_HEIGHT_DIMENSION, neuron.getParameter(KohonenTrainingSupervisor.KOHONEN_HEIGHT_DIMENSION));
        resc.setParameter(KohonenTrainingSupervisor.KOHONEN_NEURONS_GRIDS, neuron.getParameter(KohonenTrainingSupervisor.KOHONEN_NEURONS_GRIDS));
        resc.setParameter(KohonenTrainingSupervisor.KOHONEN_TIME_CONSTANT, neuron.getParameter(KohonenTrainingSupervisor.KOHONEN_TIME_CONSTANT));
        resc.setParameter(KohonenTrainingSupervisor.KOHONEN_WIDTH_DIMENSION, neuron.getParameter(KohonenTrainingSupervisor.KOHONEN_WIDTH_DIMENSION));
        resc.setParameter(KohonenTrainingSupervisor.NEURON_DISTANCE, neuron.getParameter(KohonenTrainingSupervisor.NEURON_DISTANCE));
        resc.setParameter(KohonenTrainingSupervisor.NEURON_DIS_DIMENSION, neuron.getParameter(KohonenTrainingSupervisor.NEURON_DIS_DIMENSION));
        resc.setParameter(KohonenTrainingSupervisor.NEURON_GRID_X, neuron.getParameter(KohonenTrainingSupervisor.NEURON_GRID_X));
        resc.setParameter(KohonenTrainingSupervisor.NEURON_GRID_Y, neuron.getParameter(KohonenTrainingSupervisor.NEURON_GRID_Y));
        resc.setParameter(KohonenTrainingSupervisor.NEURON_UPDATE_DISTANCE, neuron.getParameter(KohonenTrainingSupervisor.NEURON_UPDATE_DISTANCE));
        return resc;
    }
}
