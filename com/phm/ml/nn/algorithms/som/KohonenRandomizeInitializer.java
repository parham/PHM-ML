
package com.phm.ml.nn.algorithms.som;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author phm
 */
public class KohonenRandomizeInitializer implements KohonenInitializer {
    
    protected final LinkedList<double []> bounds;
    
    public KohonenRandomizeInitializer (List<double []> bds) {
        bounds = new LinkedList<> (bds);
    }
    
    @Override
    public void initialize (NeuronGroup ngroup, KohonenTrainingSupervisor kohonen) {
        
        int width = (int) ngroup.getParameter(KohonenTrainingSupervisor.KOHONEN_WIDTH_DIMENSION);
        int height = (int) ngroup.getParameter(KohonenTrainingSupervisor.KOHONEN_HEIGHT_DIMENSION);
        int dim = ngroup.noAttributes();
        
        List<double []> initws = generateRandomWeight (ngroup, width, height);
        Neuron [][] neurons = new Neuron [height][width];
        int index = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                Neuron tmp = kohonen.initNewSOMNeuron(new Neuron (initws.get(index++)));
                tmp.setParameter (KohonenTrainingSupervisor.NEURON_GRID_X, i);
                tmp.setParameter (KohonenTrainingSupervisor.NEURON_GRID_Y, j);
                neurons [j][i] = tmp;
                ngroup.addInternalNeuron(tmp);
            }
        }
        ngroup.setParameter(KohonenTrainingSupervisor.KOHONEN_NEURONS_GRIDS, neurons);
        initConnections(ngroup, height, width, kohonen);
    }
    

    
    protected void initConnections (NeuronGroup ngroup, int height, int width, KohonenTrainingSupervisor kohonen) {
        Neuron [][] neurons = (Neuron [][]) ngroup.getParameter(KohonenTrainingSupervisor.KOHONEN_NEURONS_GRIDS);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (i < width - 1) {
                    kohonen.addInternalConnection(neurons[j][i], neurons[j][i + 1]);
                }
                if (j < height - 1) {
                    kohonen.addInternalConnection(neurons[j][i], neurons[j + 1][i]);
                }
            }
        }
    }
    
    protected List<double []> generateRandomWeight (NeuronGroup ngroup, int width, int height) {
        int nn = height * width;
        Random rand = new Random(System.currentTimeMillis());
        LinkedList<double []> randlist = new LinkedList<>();
        int dim = ngroup.noAttributes();
        for (int d = 0; d < dim; d++) {
            double [] db = bounds.get(d);
            randlist.add(rand.doubles(nn, db[0], db[1]).toArray());
        }
        
        LinkedList<double []> res = new LinkedList<>();
        for (int index = 0; index < nn; index++) {
            double [] tmp = new double [ngroup.noAttributes()];
            for (int d = 0; d < tmp.length; d++) {
                tmp [d] = randlist.get(d)[index];
            }
            res.add(tmp);
        }

        return res;
    }
}
