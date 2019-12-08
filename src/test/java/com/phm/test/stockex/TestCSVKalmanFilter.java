
package com.phm.test.stockex;

import com.phm.ml.filter.NormalizeDatasetFilter;
import com.phm.ml.regression.Lin2011AutoRegression;
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
public class TestCSVKalmanFilter {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ICUStockExchange icu = new ICUStockExchange();
        Dataset ds = icu.load(new File ("/run/media/phm/PHM/PHM - I AM ONE/Dataset/Istanbul Stock Exchange/usd_ise.csv"));
        NormalizeDatasetFilter ndf = new NormalizeDatasetFilter();
        ndf.filter(ds);
        icu.save (new File("/run/media/phm/PHM/PHM - I AM ONE/Dataset/Istanbul Stock Exchange/orginal_usd_ise.csv"), ds);
        if (ds != null) {
            Lin2011AutoRegression ar = new Lin2011AutoRegression (5);
            List<Instance> insts = ar.simulate(ds);
            DefaultDataset dres = new DefaultDataset(insts);
            icu.save(new File ("/run/media/phm/PHM/PHM - I AM ONE/Dataset/Istanbul Stock Exchange/res_usd_ise_kalman.csv"), dres);
            System.out.println ("Successfull");
        } else {
            System.out.println ("Failed");   
        }
    }
}