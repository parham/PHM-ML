
package com.phm.ml.siggen;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author PARHAM
 */
public class ListedSignalGenerator extends SignalGenerator {
    
    protected AtomicInteger generatedSignal = new AtomicInteger(0);
    protected GeneratorPolicy generatePolicy;
    protected final Random rindex = new Random(System.currentTimeMillis());
    
    public ListedSignalGenerator (Dataset ds, GeneratorPolicy gp) {
        super (ds);
        generatePolicy = gp;
    }
    public ListedSignalGenerator (Dataset ds) {
        this (ds, GeneratorPolicy.GP_SEQUENTIAL);
    }

    @Override
    public int countGeneratedSignals() {
        return generatedSignal.get();
    }
    @Override
    public Instance nextElement() {
        return nextElement(generatePolicy);
    }
    public Instance nextElement (GeneratorPolicy gp) {
        if (dataset.size() > 0) {
            generatedSignal.incrementAndGet();
            switch (gp) {
                case GP_RANDOM :
                    int index = rindex.nextInt(dataset.size());
                    return dataset.remove(index);
                case GP_SEQUENTIAL :
                    return dataset.remove(0);
            }
        }
        return null;
    }
    @Override
    public int countRemainSignals() {
        return dataset.size();
    }
    @Override
    public boolean hasMoreElements() {
        return dataset.size() > 0;
    }

    public static enum GeneratorPolicy {
        GP_SEQUENTIAL,
        GP_RANDOM;
    }
}