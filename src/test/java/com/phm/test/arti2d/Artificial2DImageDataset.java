
package com.phm.test.arti2d;

import com.phm.ml.io.Image2DDataLoader;
import java.awt.Color;
import java.io.File;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;

/**
 *
 * @author phm
 */
public class Artificial2DImageDataset {
    public Dataset load (File file) {
        Image2DDataLoader ddl = new Image2DDataLoader(file, Color.BLACK);
        DefaultDataset ds = new DefaultDataset ();
        if (ddl.load(ds)) {
            return ds;
        }
        return null;
    }
}