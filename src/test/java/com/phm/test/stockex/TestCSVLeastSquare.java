
package com.phm.test.stockex;

import com.phm.ml.filter.NormalizeDatasetFilter;
import com.phm.ml.regression.DefaultAutoRegression;
import java.io.File;
import java.io.IOException;
import java.util.List;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author Owner
 */
public class TestCSVLeastSquare {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ICUStockExchange icu = new ICUStockExchange();
        Dataset ds = icu.load(new File ("/home/phm/Projects/Auto Regressive Model using Kalman Filter/Dataset/Istanbul Stock Exchange/usd_ise.csv"));
        NormalizeDatasetFilter ndf = new NormalizeDatasetFilter();
        ndf.filter(ds);
        if (ds != null) {
            DefaultAutoRegression ar = new DefaultAutoRegression (5);
            List<Instance> insts = ar.simulate(ds);
            DefaultDataset dres = new DefaultDataset(insts);
            icu.save(new File ("/home/phm/Projects/Auto Regressive Model using Kalman Filter/Dataset/Istanbul Stock Exchange/results/res_usd_ise.csv"), dres);
            System.out.println ("Successfull");
        } else {
            System.out.println ("Failed");   
        }
    }
    
}
