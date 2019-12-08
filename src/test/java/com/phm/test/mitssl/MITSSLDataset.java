
package com.phm.test.mitssl;

import com.phm.ml.io.DelimiterWithLabelFileDatasetLoader;
import java.io.File;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;

/**
 *
 * @author phm
 */
public class MITSSLDataset {
//    new File("/home/phm/Datasets/ChapelleSS/benchmark/SSL,set=1,X.tab"),
//    new File("/home/phm/Datasets/ChapelleSS/benchmark/SSL,set=1,y.tab"),
    public static DefaultDataset loadMITSSLDataset (File fdata, File flbl) {
        DelimiterWithLabelFileDatasetLoader df = new DelimiterWithLabelFileDatasetLoader (fdata, flbl, "\t", "\t");
        DefaultDataset dd = new DefaultDataset();
        if (!df.load(dd)) {
            System.out.println ("Failed!!");
        }
        return dd;
    }
    public static DefaultDataset loadMITSSLDataset (File fdata, File flbl, int ndim) {
        DelimiterWithLabelFileDatasetLoader df = new DelimiterWithLabelFileDatasetLoader (fdata, flbl, "\t", "\t");
        DefaultDataset dd = new DefaultDataset();
        if (!df.load(dd)) {
            System.out.println ("Failed!!");
        }
        DefaultDataset db = new DefaultDataset();
        for (int index = 0; index < dd.size(); index++) {
            Object cls = dd.get(index).classValue();
            double [] arr = new double[ndim];
            for (int dim = 0; dim < ndim; dim++) {
                arr [dim] = dd.get(index).value(dim);
            }
            db.add(new DenseInstance(arr, cls));
        }
        return db;
    }
}
