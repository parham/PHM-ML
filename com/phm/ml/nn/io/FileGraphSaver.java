
package com.phm.ml.nn.io;

import com.phm.ml.io.GraphSaver;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.connection.Connection;
import java.io.File;
import java.util.Objects;

/**
 *
 * @author phm
 */
public abstract class FileGraphSaver implements GraphSaver<Neuron, Connection> {
    protected File graphFile;
    
    public void setGraphFile (File f) {
        graphFile = Objects.requireNonNull(f);
    }
    public File getGraphFile () {
        return graphFile;
    }
}
