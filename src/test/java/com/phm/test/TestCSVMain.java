
package com.phm.test;

import com.phm.ml.io.CSVFileDatasetLoader;
import com.phm.ml.io.CSVFileDatasetSaver;
import com.phm.ml.regression.DefaultAutoRegression;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import org.apache.commons.csv.CSVFormat;

/**
 *
 * @author Owner
 */
public class TestCSVMain {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        CSVFileDatasetLoader csv = new CSVFileDatasetLoader (new File ("/home/phm/Projects/Auto Regressive Model using Kalman Filter/Dataset/Istanbul Stock Exchange/processed_data.csv"), 
                                                             CSVFormat.EXCEL);
        DefaultDataset ds = new DefaultDataset();
        if (csv.load (ds)) {
            // Recursive Least Square
            DefaultAutoRegression ar = new DefaultAutoRegression (5);
            List<Instance> insts = ar.simulate(ds);
            for (int index = 0; index < insts.size(); index++) {
                System.out.println (insts.get(index).value (0));
            }
            
            CSVFileDatasetSaver csvs = new CSVFileDatasetSaver(new File ("/home/phm/Projects/Auto Regressive Model using Kalman Filter/Dataset/Istanbul Stock Exchange/results/autor.csv"), 
                                                               CSVFormat.EXCEL);
            DefaultDataset dres = new DefaultDataset(insts);
            csvs.save (dres);
            
            // Kalman Filter
//            Lin2011AutoRegression ar = new Lin2011AutoRegression (5);
//            List<Instance> insts = ar.simulate(ds);
//            for (int index = 0; index < insts.size(); index++) {
//                System.out.println (insts.get(index).value (0));
//            }            
            System.out.println ("Successfull");
        } else {
            System.out.println ("Failed");        
        }
    }
    
}
