
package com.phm.test.geolife;

import com.phm.ml.io.DatasetLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;
import weka.gui.ExtensionFileFilter;

/**
 *
 * @author phm
 */
public class GeoLifeDatasetLoader implements DatasetLoader {
    
    public static final double DAY2MILI_FACTOR = 86400000;
    
    protected File labelfile = null;
    protected LinkedList<File> files = new LinkedList<>();
    
    public GeoLifeDatasetLoader (File path) {
        initialize(path);
    }
    
    private void initialize (File p) {
        if (!p.isDirectory()) {
            throw new InvalidParameterException();
        } else {
            File lbl = new File(p, "labels.txt");
            if (lbl.exists()) {
                labelfile = lbl;
            }
            File traj = new File(p, "Trajectory");
            File [] fls = traj.listFiles (new ExtensionFileFilter ("plt", "GPS file"));
            files.addAll(Arrays.asList(fls));
        }
    }
    
    List<GeoLifeDataRecord> loadData (File lf) {
        BufferedReader br = null;
        LinkedList<GeoLifeDataRecord> list = new LinkedList<>();
        try {
            br = new BufferedReader(new FileReader(lf));
            String tmp = "";
            for (int index = 0; index < 6; index++) {
                br.readLine();
            }
            while ((tmp = br.readLine()) != null) {
                String [] tokens = tmp.split(",");
                double lat = Double.valueOf (tokens [0]);
                double lng = Double.valueOf (tokens [1]);
                double alt = Double.valueOf (tokens [3]);
                double tvl = Double.valueOf (tokens [4]);
                long time = (long) (tvl * DAY2MILI_FACTOR);
                GeoLifeDataRecord rec = new GeoLifeDataRecord(time, lng, lat, alt, "");
                list.add(rec);
            }
        } catch (IOException ex) {
            
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(GeoLifeDatasetLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    @Override
    public boolean load (Dataset ds) {
        for (File f : files) {
            System.out.println (f.getName());
            List<GeoLifeDataRecord> list = loadData(f);
            for (GeoLifeDataRecord x : list) {
                double [] data = new double [3];
                data [0] = x.longitute;
                data [1] = x.latitute;
                data [2] = x.altitute;
                DenseInstance di = new DenseInstance (data);
                di.setClassValue(x.label);
                ds.add (di);
            }
        }
        return true;
    }
//
//    @Override
//    public boolean load(Dataset ds, Object defClskey) {
//        for (File f : files) {
//            System.out.println (f.getName());
//            List<GeoLifeDataRecord> list = loadData(f);
//            for (GeoLifeDataRecord x : list) {
//                double [] data = new double [3];
//                data [0] = x.longitute;
//                data [1] = x.latitute;
//                data [2] = x.altitute;
//                DenseInstance di = new DenseInstance (data);
//                di.setClassValue(defClskey);
//                ds.add (di);
//            }
//        }
//        return true;
//    }
    
    
    public class GeoLifeLabelRecord {
        long startTime = 0;
        long endTime = 0;
        String label = "";
        
        public GeoLifeLabelRecord () {
            // Empty body
        }
        public GeoLifeLabelRecord (long st, long et, String lbl) {
            startTime = st;
            endTime = et;
            label = lbl;
        }
        public boolean in (long time) {
            return time >= startTime && time <= endTime;
        }
    }
}
