
package com.phm.ml.nn;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 *
 * @author phm
 */
public class NNResultContainer implements Iterable<NNResult> {
    public LinkedList<NNResult> results = new LinkedList<NNResult>();

    public void add (NNResult res) {
        results.addLast(res);
    }
    public NNResult getRecent () {
        return results.getLast();
    }
    public void clear () {
        results.clear ();
    }
    
    public Stream<NNResult> stream () {
        return results.stream();
    }
    @Override
    public Iterator<NNResult> iterator() {
        return results.iterator();
    }    
}
