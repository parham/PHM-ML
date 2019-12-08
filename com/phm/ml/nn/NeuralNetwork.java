
package com.phm.ml.nn;
 
import com.phm.ml.siggen.SignalGenerator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm 
 */
public abstract class NeuralNetwork {
    
    protected LinkedList<NeuronAnalyzer> neuronAnalyzer = new LinkedList<>();
    protected NeuronGroup ngroup;
    
    public NeuralNetwork () {
        
    }
    
    public void addAnalyzer (NeuronAnalyzer na) {
        neuronAnalyzer.add(Objects.requireNonNull(na));
    }
    public List<NeuronAnalyzer> analyzers () {
        return neuronAnalyzer;
    }
    public void clearAnalyzers () {
        neuronAnalyzer.clear();
    }
    public void initialize () {
        ngroup = initializeStep();
        ngroup.analyzers.addAll(neuronAnalyzer);
    }
    
    public abstract boolean feed (String state, final Instance signal, NNResultContainer result);
    public abstract boolean feed (String state, SignalGenerator sg, NNResultContainer result);
    public abstract NeuronGroup initializeStep ();
}