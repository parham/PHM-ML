
package com.phm.test.pahe;

import com.phm.ml.io.CSVFileDatasetLoader;
import com.phm.ml.io.CSVFileDatasetSaver;
import com.phm.ml.regression.Lin2011AutoRegression;
import java.io.File;
import java.util.List;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import org.apache.commons.csv.CSVFormat;

/**
 *
 * @author phm
 */
public class PaheHenriqueTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CSVFileDatasetLoader csvl = new CSVFileDatasetLoader(
                                    new File("/run/media/phm/PHM/PHM - I AM ONE/Projects/Auto Regressive Model using Kalman Filter/Dataset/Predicting Acute Hypotensive Episodes/test.txt"), CSVFormat.EXCEL);
        DefaultDataset dd = new DefaultDataset();
        if (!csvl.load(dd)) {
            System.out.println ("ERROR : dataset cannot be loaded");
        } else if (dd.size() > 0) {
            System.out.println ("loading is successfull");
            Lin2011AutoRegression ar = new Lin2011AutoRegression (5);
            
            List<Instance> insts = ar.simulate(dd);
            DefaultDataset dres = new DefaultDataset(insts);
            CSVFileDatasetSaver csvs = new CSVFileDatasetSaver(
                                       new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/Auto Regressive Model using Kalman Filter/Dataset/Predicting Acute Hypotensive Episodes/test_kalman.txt"), CSVFormat.EXCEL);
            csvs.save(dres);
        }
    }
    
}
