
package com.phm.test.arti2d;

import com.phm.ml.io.CSVFileDatasetLoader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author phm
 */
public class ArtificialDatasetLoader extends CSVFileDatasetLoader {

    public ArtificialDatasetLoader(File fin, CSVFormat csvf, Charset charset) {
        super (fin, csvf, charset);
    }
    public ArtificialDatasetLoader(File fin, CSVFormat csvf) {
        super (fin, csvf);
    }
    public ArtificialDatasetLoader(File fin) {
        super (fin);
    }

    
    public boolean loadWithoutNoise (Dataset ds) {
        if (load (ds)) {
            DefaultDataset dtmp = new DefaultDataset(ds);
            for (Instance x : dtmp) {
                if (((String) x.classValue()).contentEquals("NOISE"))
                ds.remove(x);
            }
        }
        return false;
    }
    
    @Override
    public boolean load (Dataset ds) {
        try {
            CSVParser parser = CSVParser.parse(fins, charset, format);
            List<CSVRecord> records = parser.getRecords();
            for (CSVRecord c : records) {
                double [] values = new double [c.size() - 1];
                for (int dim = 0; dim < values.length; dim++) {
                    String v = c.get(dim);
                    values [dim] = v != null ? Double.valueOf(v) : 0;
                }
                DenseInstance inst = new DenseInstance(values);
                inst.setClassValue(c.get(values.length));
                ds.add(inst);
            }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

}
