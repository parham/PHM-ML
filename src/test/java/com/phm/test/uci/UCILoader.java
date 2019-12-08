
package com.phm.test.uci;

import com.phm.ml.io.CSVFileDatasetLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class UCILoader {
    public static boolean loadHAR (File data, File lbl, Dataset dd) throws IOException {
        // Read data
        DefaultDataset ds = new DefaultDataset();
        CSVFileDatasetLoader dl = new CSVFileDatasetLoader(data);
        boolean state = dl.load (ds);
        // Read classes
        BufferedReader br = new BufferedReader(new FileReader(lbl));
        String clbl = "";
        int index = 0;
        while ((clbl = br.readLine()) != null) {
            ds.get(index++).setClassValue(clbl);
//            System.out.println ("CLASS -->   " + clbl);
        }
        
        int nch = 5;
        for (int ind = 0; ind < ds.size(); ind++) {
            double [] dims = new double [nch];
            Instance tmp = ds.get(ind);
            for (int dim = 0; dim < nch; dim++) {
                dims [dim] = tmp.get(dim);
            }
            dd.add(new DenseInstance(dims, tmp.classValue()));
        }
        
        return state;
    }
    public static List<UCIReaderRecord> loadDSRecords (String pdir,
                                                       String lblpath) throws IOException {
        File fdir = new File (pdir);
        if (!fdir.isDirectory()) return null;
        DefaultDataset ds = new DefaultDataset();
        File xtrain = new File(fdir, "body_acc_x_train.txt");
        File ytrain = new File(fdir, "body_acc_y_train.txt");
        File ztrain = new File(fdir, "body_acc_z_train.txt");
        File flbl = new File(lblpath);

        BufferedReader xin = new BufferedReader (new FileReader(xtrain));
        BufferedReader yin = new BufferedReader (new FileReader(ytrain));
        BufferedReader zin = new BufferedReader (new FileReader(ztrain));
        BufferedReader lblin = new BufferedReader (new FileReader(flbl));
        
        LinkedList<UCIReaderRecord> list = new LinkedList<>();
        String xs, ys, zs, lbls;
        while ((xs = xin.readLine()) != null &&
               (ys = yin.readLine()) != null &&
               (zs = zin.readLine()) != null &&
               (lbls = lblin.readLine()) != null) {
            list.add(new UCIReaderRecord(xs, ys, zs, lbls));
        }
        
        xin.close();
        yin.close();
        zin.close();
        return list;
    }
    public static Dataset loadAsDataset (List<UCIReaderRecord> list) {
        DefaultDataset ds = new DefaultDataset();
        list.stream().forEach((UCIReaderRecord u) -> {
            String [] sx = u.xtrain.split(" ");
            String [] sy = u.ytrain.split(" ");
            String [] sz = u.ztrain.split(" ");
            
            LinkedList<Float> x = new LinkedList<>();
            LinkedList<Float> y = new LinkedList<>();
            LinkedList<Float> z = new LinkedList<>();
            
            for (String sx1 : sx) {
                if (!sx1.isEmpty()) {
                    x.add(Float.valueOf(sx1));
                }
            }
            for (String sy1 : sy) {
                if (!sy1.isEmpty()) {
                    y.add(Float.valueOf(sy1));
                }
            }
            for (String sz1 : sz) {
                if (!sz1.isEmpty()) {
                    z.add(Float.valueOf(sz1));
                }
            }
            
            LinkedList<float []> listtmp = new LinkedList<>();
            float [] tx = new float [x.size ()];
            float [] ty = new float [y.size ()];
            float [] tz = new float [z.size ()];
            for (int dim = 0; dim < x.size(); dim++) {
                tx [dim] = x.get(dim);
            }
            for (int dim = 0; dim < y.size(); dim++) {
                ty [dim] = y.get(dim);
            }
            for (int dim = 0; dim < z.size(); dim++) {
                tz [dim] = z.get(dim);
            }
            int dimnum = Math.min(tx.length, Math.min(ty.length, tz.length));
            for (int dim = 0; dim < dimnum; dim++) {
                double [] dd = new double[3];
                dd [0] = tx [dim];
                dd [1] = ty [dim];
                dd [2] = tz [dim];
                DenseInstance data = new DenseInstance(dd);
                data.setClassValue(u.label);
                ds.add(data);
            }
        });
        return ds;
    }
    
    
    
    public static float [] stringToFloat (String str) {
        String [] sarr = str.split(" ");
        float [] farr = new float [sarr.length];
        for (int index = 0; index < sarr.length; index++) {
            farr [index] = Float.valueOf(sarr [index]);
        }
        return farr;
    }
    
    public static class UCIReaderRecord {
        public String xtrain;
        public String ytrain;
        public String ztrain;
        public String label;
        
        public UCIReaderRecord (String x, String y, String z, String l) {
            xtrain = x;
            ytrain = y;
            ztrain = z;
            label = l;
        }
    }
}