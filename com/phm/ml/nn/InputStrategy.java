
package com.phm.ml.nn;

import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public abstract class InputStrategy {
    public abstract Instance input (Instance signal);
}
