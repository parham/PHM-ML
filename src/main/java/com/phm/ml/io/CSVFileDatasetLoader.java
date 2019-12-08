
package com.phm.ml.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;

/**
 *
 * @author phm
 */
public class CSVFileDatasetLoader extends FileDatasetLoader {

	protected CSVFormat format;
	protected Charset charset;

	public CSVFileDatasetLoader(File fin, CSVFormat frmt, Charset chs) {
		super(fin);
		format = Objects.requireNonNull(frmt);
		charset = Objects.requireNonNull(chs);
	}

	public CSVFileDatasetLoader(File fin, CSVFormat frmt) {
		this(fin, frmt, Charset.defaultCharset());
	}

	public CSVFileDatasetLoader(File fin) {
		this(fin, CSVFormat.EXCEL);
	}

	public CSVFormat getCSVFormat() {
		return format;
	}

	public Charset getCharset() {
		return charset;
	}

	@Override
	public boolean load(Dataset ds) {
		try {
			CSVParser parser = CSVParser.parse(fins, charset, format);
			List<CSVRecord> records = parser.getRecords();
			for (CSVRecord c : records) {
				double[] values = new double[c.size()];
				for (int dim = 0; dim < values.length; dim++) {
					String v = c.get(dim);
					values[dim] = v != null ? Double.valueOf(v) : 0;
				}
				DenseInstance inst = new DenseInstance(values);
				ds.add(inst);
			}
		} catch (IOException ex) {
			return false;
		}
		return true;
	}
//
//    @Override
//    public boolean load(Dataset ds, Object defClskey) {
//        try {
//            CSVParser parser = CSVParser.parse(fins, charset, format);
//            List<CSVRecord> records = parser.getRecords();
//            for (CSVRecord c : records) {
//                double [] values = new double [c.size()];
//                for (int dim = 0; dim < values.length; dim++) {
//                    String v = c.get(dim);
//                    values [dim] = v != null ? Double.valueOf(v) : 0;
//                }
//                DenseInstance inst = new DenseInstance(values);
//                inst.setClassValue(defClskey);
//                ds.add(inst);
//            }
//        } catch (IOException ex) {
//            return false;
//        }
//        return true;
//    }
}
