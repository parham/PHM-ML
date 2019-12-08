
package com.phm.ml.siggen;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 * @param <DFType>
 */
public class RepeatableListSignalGenerator extends ListedSignalGenerator {

    public RepeatableListSignalGenerator(Dataset ds, GeneratorPolicy gp) {
        super(ds, gp);
    }
    public RepeatableListSignalGenerator(Dataset ds) {
        super(ds, GeneratorPolicy.GP_RANDOM);
    }

    @Override
    public Instance nextElement (GeneratorPolicy gp) {
        if (dataset.size() > 0) {
            generatedSignal.incrementAndGet();
            switch (gp) {
                case GP_RANDOM :
                    int index = rindex.nextInt(dataset.size());
                    return dataset.get (index);
                case GP_SEQUENTIAL :
                    return dataset.get(0);
            }
        }
        return null;
    }
}
