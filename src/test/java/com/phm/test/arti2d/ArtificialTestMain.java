
package com.phm.test.arti2d;

import java.io.File;
import net.sf.javaml.core.DefaultDataset;
import org.apache.commons.csv.CSVFormat;

/**
 *
 * @author phm
 */
public class ArtificialTestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DefaultDataset ds = new DefaultDataset();
        File file = new File ("/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/topoart_data/dataset.csv");
        ArtificialDatasetLoader loader = new ArtificialDatasetLoader(file, CSVFormat.EXCEL);
        if (loader.load (ds)) {
            System.out.println ("Data is loaded, successfully");
            System.out.println ("Dataset is sized : " + ds.size());
        } else {
            System.out.println ("Data is failed to load");
        }
//        for (Instance inst : ds) {
//            System.out.println ("( " + inst.value(0) + " , " +
//                                       inst.value(1) + " ) --> " + 
//                                       inst.classValue());
//        }
    }
    
}
