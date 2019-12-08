
package com.phm.test;

import com.phm.ml.regression.DefaultAutoRegression;
import java.util.LinkedList;
import java.util.List;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class DefaultRegressionTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double [] weight = {74.0f, 74.0f, 74.1f, 74.2f, 74.5f, 75f, 74.5f, 74.2f, 74.5f, 74.2f, 74.0f, 73.8f, 73.5f, 73.3f, 73.1f, 72.7f, 73.0f,
                           74.1f, 74.0f, 74.5f, 74.8f, 75.0f, 75.1f, 75.2f, 75.5f, 75.2f, 75.5f, 75.2f, 75.0f, 74.9f, 74.8f, 74.8f, 75.0f};
        LinkedList<Instance> ds = new LinkedList<> ();
        for (int index = 0; index < weight.length; index++) {
            DenseInstance ins = new DenseInstance (1);
            ins.put(0, weight [index]);
            ds.add (ins);
        }
        DefaultAutoRegression ar = new DefaultAutoRegression (5);
        List<Instance> insts = ar.simulate(ds);
        for (int index = 0; index < insts.size(); index++) {
            System.out.println (insts.get(index).value (0));
        }
    }
    
}
