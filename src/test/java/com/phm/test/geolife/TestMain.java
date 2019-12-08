
package com.phm.test.geolife;

import java.io.File;
import net.sf.javaml.core.DefaultDataset;

/**
 *
 * @author phm
 */
public class TestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //String [] tokens = tmp.split("\\s+");
        GeoLifeDatasetLoader gl = new GeoLifeDatasetLoader(
            new File ("/run/media/phm/PHM/PHM - I AM ONE/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Extended Gaussian-TopoART/Dataset/Geolife Trajectories 1.3/Data/100"));
        DefaultDataset ds = new DefaultDataset();
        gl.load(ds);
        System.out.println (ds.size());
    }
}
