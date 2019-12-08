
package com.phm.ml.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class DelimiterWithLabelFileDatasetSaver extends FileDatasetSaver {

	protected File finLabeled;
	protected String delimIn = ",";
	protected String delimLabel = ",";

	public DelimiterWithLabelFileDatasetSaver(File fin, File flabel, String din, String dlbl) {
		super(fin);
		finLabeled = Objects.requireNonNull(flabel);
		delimIn = Objects.requireNonNull(din);
		delimLabel = Objects.requireNonNull(dlbl);
	}

	public DelimiterWithLabelFileDatasetSaver(File fin, File flabel, String din) {
		this(fin, flabel, din, ",");
	}

	public DelimiterWithLabelFileDatasetSaver(File fin, File flabel) {
		this(fin, flabel, ",", ",");
	}

	public File getLabelFile() {
		return finLabeled;
	}

	@Override
	public boolean save(Dataset ds) {
		try {

			FileWriter fw = new FileWriter(file);
			StringBuilder sb = new StringBuilder();
			for (Instance inst : ds) {
				for (int dim = 0; dim < inst.noAttributes() - 1; dim++) {
					sb.append(inst.value(dim) + delimIn);
				}
				sb.append(inst.value(inst.noAttributes() - 1));
				sb.append("\n");
			}
			fw.write(sb.toString());
			fw.close();

			FileWriter fwLabeled = new FileWriter(finLabeled);
			StringBuilder sblbl = new StringBuilder();
			for (Instance inst : ds) {
				sblbl.append(inst.classValue() + "\n");
			}
			fwLabeled.write(sblbl.toString());
			fwLabeled.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

}
