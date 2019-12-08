
package com.phm.test.hwds;

/**
 *
 * @author phm
 */
public class HWDBKNN_MDDTW {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        List<HandWriting> list = HandWrittingDSLoader.loadHandWritings("/home/phm/Projects/" + 
//                                    "INCREMENTAL NEURAL NETWORK PROJECTS/" + 
//                                    "SimilarityGNG/Dataset/handwriting_dataset");
//        System.out.println ("Handwriting is loaded ...");
//        Dataset<DataField<float []>> db = HandWrittingDSLoader.loadAsDataSetMCH(list);
//        System.out.println ("Dataset size is --> " + db.size());
//        Dataset<DataField<float []>> dbtest = new Dataset<>();
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
//        System.out.println ("Training is finished ...");
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
    }
}
