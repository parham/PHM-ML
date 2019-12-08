
package com.phm.ml.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 *
 * @author phm
 */
public class CSVFileDatasetSaver extends FileDatasetSaver {

    protected CSVFormat format = CSVFormat.EXCEL;
    
    public CSVFileDatasetSaver(File f, CSVFormat frmt) {
        super(f);
    }
    
    public CSVFormat getCSVFormat () {
        return format;
    }

    @Override
    public boolean save(Dataset ds) {
        try {
            try (CSVPrinter printer = new CSVPrinter (new FileWriter(file), format)) {
                for (Instance x : ds) {
                    List<Double> values = new LinkedList<>();
                    for (int dim = 0; dim < x.noAttributes(); dim++) {
                        values.add(x.value(dim));
                    }
                    printer.printRecord(values);
                }
                printer.flush();
            }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
}
