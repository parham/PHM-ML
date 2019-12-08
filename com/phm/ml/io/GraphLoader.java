
package com.phm.ml.io;

import org.jgrapht.Graph;
/**
 *
 * @author phm
 * @param <V>
 * @param <E>
 */
public interface GraphLoader<V,E> {
    public boolean load (Graph<V,E> g);
}
