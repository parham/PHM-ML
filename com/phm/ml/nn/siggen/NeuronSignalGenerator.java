
package com.phm.ml.nn.siggen;

import com.phm.ml.event.EventListener;
import com.phm.ml.nn.NNResultContainer;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.event.NGStopedTrainingEvent;
import com.phm.ml.siggen.SignalGenerator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.javaml.core.Instance;

/**
 *
 * @author PARHAM
 */
public class NeuronSignalGenerator
        implements EventListener<NGStopedTrainingEvent>, Iterator<Instance> {
    
    protected final AtomicBoolean isFinished = new AtomicBoolean(false);
    protected final SignalGenerator siggen;
    
    public NeuronSignalGenerator (SignalGenerator sig) {
        siggen = Objects.requireNonNull(sig);
    }
    
    public int countGeneratedSignals () {
        return siggen.countGeneratedSignals ();
    }
    public int countRemainSignals () {
        return siggen.countRemainSignals ();
    }
    
    public boolean signal (NeuronGroup ng, String state, NNResultContainer result) {
        return hasNext() && ng.feed (state, next(), result);
    }
    public boolean signalAll (NeuronGroup ng, String state, NNResultContainer result) {
        LinkedList<Neuron> list = new LinkedList<>();
        while (signal(ng, state, result)) {
//            result.add(list);
//            list = new LinkedList<>();
        }
        return true;
    }
    public void signalAllInNewThread (NeuronGroup ng, String state, NNResultContainer result) {
        new Thread(() -> {
            signalAll(ng, state, result);
        }).start();
    }
    
    @Override
    public void event(NGStopedTrainingEvent event) {
        isFinished.set(true);
    }

    @Override
    public boolean hasNext() {
        return !isFinished.get() && siggen.hasMoreElements();
    }

    @Override
    public Instance next() {
        return siggen.nextElement();
    }
}
