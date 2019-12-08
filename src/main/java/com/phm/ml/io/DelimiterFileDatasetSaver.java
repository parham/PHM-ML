
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
public class DelimiterFileDatasetSaver extends FileDatasetSaver {
	protected String delimiter = ",";

	public DelimiterFileDatasetSaver(File fin, String dlm) {
		super(fin);
		delimiter = Objects.requireNonNull(dlm);
	}

	public DelimiterFileDatasetSaver(File fin) {
		super(fin);
		delimiter = Objects.requireNonNull(",");
	}

	public void setDelimiter(String dlm) {
		delimiter = Objects.requireNonNull(dlm);
	}

	public String getDelimiter() {
		return delimiter;
	}

	@Override
	public boolean save(Dataset ds) {
		try {
			try (FileWriter fw = new FileWriter(file)) {
				StringBuilder sb = new StringBuilder();
				for (Instance inst : ds) {
					for (int dim = 0; dim < inst.noAttributes() - 1; dim++) {
						sb.append(inst.value(dim) + delimiter);
					}
					sb.append(inst.value(inst.noAttributes() - 1));
					sb.append("\n");
				}
				fw.write(sb.toString());
			}
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

}
