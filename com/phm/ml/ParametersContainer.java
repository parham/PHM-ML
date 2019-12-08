
package com.phm.ml;

import java.util.HashMap;

/**
 *
 * @author Parham Nooralishahi - PHM!
 */
public class ParametersContainer extends HashMap<String, Object>
                                 implements Cloneable {
    
    public boolean processOnParameter (String key, ProcessOnParameter process) {
        Object value = get(key);
        if (value == null) {
            return false;
        }
        Object result = process.process(value, this);
        if (result == null) return false;
        put(key, result);
        return true;
    }
    
    @Override
    public Object clone () {
        return super.clone();
    }
}
