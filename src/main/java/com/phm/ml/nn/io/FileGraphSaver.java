
package com.phm.ml.nn.io;

import java.io.File;
import java.util.Objects;

import com.phm.ml.io.GraphSaver;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.connection.Connection;

/**
 *
 * @author phm
 */
public abstract class FileGraphSaver implements GraphSaver<Neuron, Connection> {
	protected File graphFile;

	public void setGraphFile(File f) {
		graphFile = Objects.requireNonNull(f);
	}

	public File getGraphFile() {
		return graphFile;
	}
}
