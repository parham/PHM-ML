
package com.phm.test.gps;

import com.phm.ml.io.CSVFileDatasetLoader;
import java.io.File;
import org.apache.commons.csv.CSVFormat;
import net.sf.javaml.core.DefaultDataset;

/**
 *
 * @author phm
 */
public class GPSTestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CSVFileDatasetLoader csvl = new CSVFileDatasetLoader(
            new File("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/gps/gps1.csv"), CSVFormat.EXCEL);
        
        DefaultDataset ds = new DefaultDataset();
        if (!csvl.load(ds)) {
            System.out.println ("Error is failed ...");
        }
        System.out.println ("Dataset size : " + ds.size());
    }
}
