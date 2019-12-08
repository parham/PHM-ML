
package com.phm.ml.io;

import java.io.File;
import java.util.Objects;

/**
 *
 * @author phm
 */
public abstract class FileDatasetLoader implements DatasetLoader {
    protected final File fins;
    
    public FileDatasetLoader (File fin) {
        fins = Objects.requireNonNull(fin);
    }
    
    public File getFile () {
        return fins;
    }
}
