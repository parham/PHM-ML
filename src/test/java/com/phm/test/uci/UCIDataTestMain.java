
package com.phm.test.uci;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import net.sf.javaml.core.Dataset;

/**
 *
 * @author phm
 */
public class UCIDataTestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.println ("Dataset is loading ...");
        List<UCILoader.UCIReaderRecord> list = 
                UCILoader.loadDSRecords("/home/phm/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/2015 - OSSMGNG/Dataset/UCI HAR/UCI HAR Dataset/train/Inertial Signals/",
                                        "/home/phm/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/2015 - OSSMGNG/Dataset/UCI HAR/UCI HAR Dataset/train/y_train.txt");
        System.out.println ("Dataset is converting " + list.size() + " data records");
        Dataset db = UCILoader.loadAsDataset(list);
        System.out.println ("Number of data fields ---> " + db.size());
        HashMap<Object, Integer> di = new HashMap<> ();
        db.stream().forEach((df) -> {
            if (!di.containsKey(df.classValue())) {
                di.put(df.classValue(), 1);
            } else {
                int v = di.get(df.classValue());
                di.put (df.classValue(), v + 1);
            }
        });
        di.forEach((Object lbl, Integer v) -> {
            System.out.println (lbl.toString() + " : " + v);
        });
    }
    
}
