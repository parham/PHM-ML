
package com.phm.test.hwds;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author phm
 */
public class HandWrittingDSLoader {
    
//    public static Dataset loadAsDataSetMCH (List<HandWriting> ds) {
//        final Dataset res = new DefaultDataset ();
//        ds.stream().forEach((HandWriting h) -> {
//            Instance df;
//            float [] xarr = new float [h.xarr.size()];
//            float [] yarr = new float [h.yarr.size()];
//            for (int dim = 0; dim < h.xarr.size(); dim++) {
//                xarr [dim] = h.xarr.get(dim);
//                yarr [dim] = h.yarr.get(dim);
//            }
//            LinkedList<float []> list = new LinkedList<>();
//            list.add(xarr);
//            list.add(yarr);
//            df = new DataField (h.label, list);
//            res.add(df);
//        });
//        
//        return res;
//    }
    
//    public static Dataset<DataField<float []>> loadAsDataSetMCHWithDerivation (List<HandWriting> ds) {
//        final Dataset<DataField<float []>> res = new Dataset ();
//        ds.stream().forEach((HandWriting h) -> {
//            DataField<float []> df;
//            float [] xarr = new float [h.xarr.size()];
//            float [] yarr = new float [h.yarr.size()];
//            for (int dim = 1; dim < h.xarr.size(); dim++) {
//                xarr [dim] = h.xarr.get(dim);
//                yarr [dim] = h.yarr.get(dim);
//            }
//            LinkedList<float []> list = new LinkedList<>();
//            list.add(xarr);
//            list.add(yarr);
//            df = new DataField<>(h.label, list);
//            res.add(df);
//        });
//        
//        return res;
//    }
    
    public static List<float []> asDerivation (List<HandWriting> hnds) {
        LinkedList<float []> ds = new LinkedList<>();
        for (HandWriting hw : hnds) {
            LinkedList<Float> dvs = new LinkedList<>();
            for (int dim = 0; dim < hw.xarr.size() - 1; dim++) {
                float v = (float)(hw.yarr.get(dim + 1) - hw.yarr.get(dim)) / 
                          (float)(hw.xarr.get(dim + 1) - hw.xarr.get(dim));
                dvs.add(v);
            }
            float [] vs = new float [dvs.size()];
            for (int dim = 0; dim < dvs.size(); dim++) {
                vs [dim] = dvs.get(dim);
            }
            ds.add(vs);
        }
        return ds;
    }
    
    public static List<HandWriting> loadHandWritings (String path) {
        LinkedList<HandWriting> res = new LinkedList<>();
        // Load DataSet
        File dirpath = new File(path);
        System.out.println ("DIR: " + path);
        if (dirpath.isDirectory()) {
            File [] clss = dirpath.listFiles((File pathname) -> {
                return pathname.isDirectory();
            });
            for (File cs : clss) {
                System.out.println ("CLASS: " + cs.getName());
                File [] fhwds = cs.listFiles((File dir, String name) -> {
                    return name.endsWith("hwp");
                });
                for (File fhwd : fhwds) {
                    HandWriting tmp = loadHW(fhwd);
                    if (tmp != null) {
                        tmp.label = cs.getName();
                        res.add(tmp);
                    }
                }
            }
        }
        return res;
    }
    
    protected static HandWriting loadHW (File path) {
        try {
            HandWriting hd = new HandWriting();
            BufferedReader in = new BufferedReader(new FileReader(path));
            in.lines().forEach((String x) -> {
                StringTokenizer st = new StringTokenizer(x, ",");
                if (st.countTokens() > 1) {
                    hd.xarr.add(Float.valueOf(st.nextToken()));
                    hd.yarr.add(Float.valueOf(st.nextToken()));
                }
            });
            return hd;
        } catch (FileNotFoundException ex) {
            return null;
        }
    }
    
}
