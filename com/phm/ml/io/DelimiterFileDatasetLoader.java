
package com.phm.ml.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.StringTokenizer;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DenseInstance;

/**
 *
 * @author phm
 */
public class DelimiterFileDatasetLoader extends FileDatasetLoader {

    protected String delimiter = ",";
    
    public DelimiterFileDatasetLoader(File fin, String dlm) {
        super(fin);
        delimiter = Objects.requireNonNull(dlm);
    }
    public DelimiterFileDatasetLoader(File fin) {
        super(fin);
        delimiter = Objects.requireNonNull(",");
    }
    
    public void setDelimiter (String dlm) {
        delimiter = Objects.requireNonNull(dlm);
    }
    public String getDelimiter () {
        return delimiter;
    }
    

    @Override
    public boolean load(Dataset ds) {
        try {
            try (BufferedReader fr = new BufferedReader (new FileReader (fins))) {
                fr.lines().forEach((String rec) -> {
                    StringTokenizer stoken = new StringTokenizer(rec, delimiter);
                    double [] dims = new double [stoken.countTokens()];
                    for (int index = 0; index < dims.length; index++) {
                        String tk = stoken.nextToken().trim();
                        if (!tk.isEmpty()) {
                            dims [index] = Double.valueOf(tk);
                        }
                    }
                    ds.add(new DenseInstance(dims));
                });
            }
            return true;
        } catch (IOException ex) {
            System.out.println (ex);
            return false;
        }
    }

//    @Override
//    public boolean load(Dataset ds, Object defClskey) {
//        try {
//            try (BufferedReader fr = new BufferedReader (new FileReader (fins))) {
//                fr.lines().forEach((String rec) -> {
//                    StringTokenizer stoken = new StringTokenizer(rec, delimiter);
//                    double [] dims = new double [stoken.countTokens()];
//                    for (int index = 0; index < dims.length; index++) {
//                        String tk = stoken.nextToken().trim();
//                        if (!tk.isEmpty()) {
//                            dims [index] = Double.valueOf(tk);
//                        }
//                    }
//                    DenseInstance inst = new DenseInstance(dims);
//                    inst.setClassValue(defClskey);
//                    ds.add(inst);
//                });
//            }
//            return true;
//        } catch (IOException ex) {
//            System.out.println (ex);
//            return false;
//        }
//    }
    
}
