
package com.phm.test.uci;

/**
 *
 * @author phm
 */
public class UCITestKNN_MDDTW {
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
//        // DataSet<DataField<float []>> ds = UCILoader.loadAsTimeSeriesDataSet(list);
//        Dataset<DataField<float []>> db = UCILoader.loadAsTimeSeriesDataSet(list);
//        LinkedList<DataField<float []>> dbtest = new LinkedList<>();
//        System.out.println ("Dataset size ---> " + db.size());
//        System.out.println ("Learning ...");
//        Random rand = new Random(System.currentTimeMillis());
//        for (int index = 0; index < 1070; index++) {
//            int id = rand.nextInt(db.size());
//            DataField<float []> df = db.remove(id);
//            dbtest.add(df);
//        }
//        System.out.println ("Dataset is created ...");
//        ////////////////
//        KNearestNeighborsClassifier<DataField<float []>> knn = 
//                new KNearestNeighborsClassifier(new MDDTWDistanceMethod(new NormNormalizeMethod(), 
//                                                                        new GaussianFilterMethod(),
//                                                                        new FullWindowSW(),
//                                                                        new MinimumDimDistancePolicy()), 3);
//        knn.train(db);
//        long runtime = System.currentTimeMillis();
//        int rate = 0;
//        for (DataField<float []> df : dbtest) {
//            String lbl = knn.classify(df);
//            System.out.println (df.label + " --> " + lbl);
//            if (df.label.contentEquals(lbl)) {
//                rate++;
//            }
//        }
//        runtime -= System.currentTimeMillis();
//        System.out.println ("Runtime --> " + Math.abs(runtime));
//        System.out.println ("Accuracy Rate : " + (float) rate / dbtest.size());
//    }
}
