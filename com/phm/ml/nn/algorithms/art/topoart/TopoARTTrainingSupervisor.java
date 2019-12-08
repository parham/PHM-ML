
package com.phm.ml.nn.algorithms.art.topoart;
 
import com.phm.ml.nn.NNResult;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Supervisor;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.art.fuzzy.FuzzyARTInputStrategy;
import java.util.List;
import net.sf.javaml.core.Instance;

/**
 *
 * @author PARHAM
 */
public class TopoARTTrainingSupervisor extends Supervisor {

    public static final int NETA_INDEX = 0;
    public static final int NETB_INDEX = 1;
    
    protected TopoFuzzyARTTrainingSupervisor nsA;
    protected TopoFuzzyARTTrainingSupervisor nsB;
    
    protected NeuronGroup ngA;
    protected NeuronGroup ngB;
    
    @Override
    public String getName() {
        return "topoart.train";
    }

    @Override
    public void initialize(Neuron neuron) {
        NeuronGroup ngroup = (NeuronGroup) neuron;
        ngroup.setParameter(NeuronGroup.NEURON_REDIRECT_SIGNAL_FLAG, false);
        ngroup.setInputStrategy(new FuzzyARTInputStrategy ());
        
        nsA = new TopoFuzzyARTTrainingSupervisor(ngroup.noAttributes());
        nsB = new TopoFuzzyARTTrainingSupervisor(ngroup.noAttributes());
        
        ngA = new NeuronGroup (new double[ngroup.noAttributes()]);
        ngA.supervisors.add(nsA);
        nsA.initialize(ngA);
        ngA.setParameter(NeuronGroup.NEURON_REDIRECT_SIGNAL_FLAG, false);
        ngA.setParameter(TopoFuzzyARTTrainingSupervisor.ART_VIGILANCE_PARAMTER, 0.92f);
        ngA.setParameter(TopoFuzzyARTTrainingSupervisor.FUZZYART_LEARNING_RATE, 1.0f);
        ngA.setParameter(TopoFuzzyARTTrainingSupervisor.TOPOFUZZYART_LEARNING_RATE_B, 0.2f); // 0.6
        ngA.setParameter(TopoFuzzyARTTrainingSupervisor.TOPOFUZZYART_LEARNINGCYCLE, 100);
        ngA.setParameter(TopoFuzzyARTTrainingSupervisor.TOPOFUZZYART_NEURONSTABILITY_THRESH, 6);
        
        ngB = new NeuronGroup (new double [2]);
        ngB.supervisors.add(nsB);
        nsB.initialize(ngB);
        ngB.setParameter(NeuronGroup.NEURON_REDIRECT_SIGNAL_FLAG, false);
        ngB.setParameter(TopoFuzzyARTTrainingSupervisor.ART_VIGILANCE_PARAMTER, 0.96f);
        ngB.setParameter(TopoFuzzyARTTrainingSupervisor.FUZZYART_LEARNING_RATE, 1.0f);
        ngB.setParameter(TopoFuzzyARTTrainingSupervisor.TOPOFUZZYART_LEARNING_RATE_B, 0.2f); // 0.6
        ngB.setParameter(TopoFuzzyARTTrainingSupervisor.TOPOFUZZYART_LEARNINGCYCLE, 100);
        ngB.setParameter(TopoFuzzyARTTrainingSupervisor.TOPOFUZZYART_NEURONSTABILITY_THRESH, 6);
        
        ngroup.addInternalNeuron(ngA);
        ngroup.addInternalNeuron(ngB);  
    }

    @Override
    protected boolean superviseOperator(Neuron neuron, Instance signal, List<Neuron> result) {
        NeuronGroup ngroup = (NeuronGroup) neuron;
        NeuronGroup nA = (NeuronGroup) ngroup.neurons.getIndex(NETA_INDEX);
        NeuronGroup nB = (NeuronGroup) ngroup.neurons.getIndex(NETB_INDEX);
        result.add(nA);
        result.add(nB);
        NNResultContainer nnres = new NNResultContainer();
        if (nA.feed(nsA.getName(), signal, nnres)) {
            boolean status = (boolean) nA.getParameter(TopoFuzzyARTTrainingSupervisor.TOPOFUZZYART_TOPOB_CAN_ACTIVATE);
            if (status) {
                return nB.feed(nsB.getName(), signal, nnres);
            }
            return true;
        }
        return false;
    }

    @Override
    protected NNResult prepareResult(Neuron neuron, Instance signal, List<Neuron> winners, NNResult resc) {
        return new NNResult(System.currentTimeMillis(), neuron.getParametersContainer(), winners);
    }
    
}
