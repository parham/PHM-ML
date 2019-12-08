
package com.phm.test.uci;

/**
 *
 * @author phm
 */
public class UCITestMain {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws IOException {
//        System.out.println ("Dataset is loading ...");
//        List<UCILoader.UCIReaderRecord> list = 
//                UCILoader.loadDSRecords("/home/phm/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Action Recognition/Dataset/UCI HAR/UCI HAR Dataset/train/Inertial Signals/",
//                                        "/home/phm/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Action Recognition/Dataset/UCI HAR/UCI HAR Dataset/train/y_train.txt");
//        System.out.println ("Dataset is converting ...");
//        Dataset<DataField<float []>> db = UCILoader.loadAsTimeSeriesDataSet(list);
//        System.out.println ("Dataset's size ---> " + db.size());
//        HashMap<String, Integer> di = new HashMap<> ();
//        db.stream().forEach((df) -> {
//            if (!di.containsKey(df.label)) {
//                di.put(df.label, 1);
//            } else {
//                int v = di.get(df.label);
//                di.put (df.label, v + 1);
//            }
//        });
//        di.forEach((String lbl, Integer v) -> {
//            System.out.println (lbl + " : " + v);
//        });
//    }
//    
}
