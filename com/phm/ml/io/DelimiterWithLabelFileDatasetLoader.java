
package com.phm.ml.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.StringTokenizer;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class DelimiterWithLabelFileDatasetLoader extends FileDatasetLoader {

    protected File finLabeled;
    protected String delimIn = ",";
    protected String delimLabel = ",";
    
    public DelimiterWithLabelFileDatasetLoader(File fin, 
                                               File flabel,
                                               String din,
                                               String dlbl) {
        super(fin);
        finLabeled = Objects.requireNonNull (flabel);
        delimIn = Objects.requireNonNull (din);
        delimLabel = Objects.requireNonNull (dlbl);
    }
    public DelimiterWithLabelFileDatasetLoader(File fin, 
                                               File flabel,
                                               String din) {
        this (fin, flabel, din, ",");
    }
    public DelimiterWithLabelFileDatasetLoader(File fin, 
                                               File flabel) {
        this (fin, flabel, ",", ",");
    }
    
    public File getLabelFile () {
        return finLabeled;
    }

    @Override
    public boolean load (Dataset ds) {
        try {
            BufferedReader fr = new BufferedReader (new FileReader (fins));
            BufferedReader frlbl = new BufferedReader (new FileReader (finLabeled));
            fr.lines().forEach((String rec) -> {
                StringTokenizer stoken = new StringTokenizer(rec, delimIn);
                double [] dims = new double [stoken.countTokens()];
                for (int index = 0; index < dims.length; index++) {
                    String tk = stoken.nextToken().trim();
                    if (!tk.isEmpty()) {
                        dims [index] = Double.valueOf(tk);
                    }
                }
                ds.add(new DenseInstance(dims));
            });
            for (int index = 0; index < ds.size(); index++) {
                String lbl = frlbl.readLine();
                Instance inst = ds.get(index);
                if (lbl != null) {
                    inst.setClassValue(lbl);
                }
            }
            fr.close();
            frlbl.close();
            return true;
        } catch (IOException ex) {
            System.out.println (ex);
            return false;
        }
    }

//    @Override
//    public boolean load(Dataset ds, Object defClskey) {
//        try {
//            BufferedReader fr = new BufferedReader (new FileReader (fins));
//            BufferedReader frlbl = new BufferedReader (new FileReader (finLabeled));
//            fr.lines().forEach((String rec) -> {
//                StringTokenizer stoken = new StringTokenizer(rec, delimIn);
//                double [] dims = new double [stoken.countTokens()];
//                for (int index = 0; index < dims.length; index++) {
//                    String tk = stoken.nextToken().trim();
//                    if (!tk.isEmpty()) {
//                        dims [index] = Double.valueOf(tk);
//                    }
//                }
//                DenseInstance inst = new DenseInstance(dims);
//                ds.add (inst);
//            });
//            fr.close();
//            frlbl.close();
//            return true;
//        } catch (IOException ex) {
//            System.out.println (ex);
//            return false;
//        }
//    }
    
}
