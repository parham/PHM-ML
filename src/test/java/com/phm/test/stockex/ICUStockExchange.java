
package com.phm.test.stockex;

import com.phm.ml.io.CSVFileDatasetLoader;
import com.phm.ml.io.CSVFileDatasetSaver;
import java.io.File;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import org.apache.commons.csv.CSVFormat;

/**
 *
 * @author phm
 */
public class ICUStockExchange {
    public Dataset load (File file) {
        CSVFileDatasetLoader csvl = new CSVFileDatasetLoader (file, CSVFormat.EXCEL);
        DefaultDataset ds = new DefaultDataset();
        if (csvl.load (ds)) {
            return ds;
        }
        return null;
    }
    public boolean save (File file, Dataset ds) {
        CSVFileDatasetSaver csv = new CSVFileDatasetSaver (file, CSVFormat.EXCEL);
        if (csv.save(ds)) {
            return true;
        }
        return false;
    }
}
