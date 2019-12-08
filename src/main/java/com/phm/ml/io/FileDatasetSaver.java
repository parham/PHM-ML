
package com.phm.ml.io;

import java.io.File;
import java.util.Objects;

/**
 *
 * @author phm
 */
public abstract class FileDatasetSaver implements DatasetSaver {

	protected File file;

	public FileDatasetSaver(File f) {
		file = Objects.requireNonNull(f);
	}

	public File getFile() {
		return file;
	}
}
