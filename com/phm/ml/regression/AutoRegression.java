
package com.phm.ml.regression;

import java.util.List;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public abstract class AutoRegression {
    public abstract Instance estimate (List<Instance> inputs);
    public abstract List<Instance> simulate (List<Instance> inputs);
    public abstract List<Instance> calculateError (List<Instance> inputs);
}
