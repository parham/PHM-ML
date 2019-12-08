
package com.phm.test.wisdm;

/**
 *
 * @author phm
 */
public class WISDMTestOSSSMGNG2 {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws IOException {
//        LinkedList<WISDMRecord> rec = new LinkedList<>();
////        try (WISDMReader rin = new WISDMReader("/home/phm/Projects/INCREMENTAL "
////                + "NEURAL NETWORK PROJECTS/Action Recognition/Dataset/"
////                + "WISDM_ar_v1.1/WISDM_ar_v1.1_raw.txt")) {
////            rin.read (rec);
////        }
//        try (WISDMReader rin = new WISDMReader("/home/phm/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Action Recognition/Dataset/WISDM_ar_v1.1/WISDM_ar_v1.1_raw.txt")) {
//            rin.read (rec);
//        }
//        System.out.println ("Loading is finished");
//        UserActivityDataSet dataset = new UserActivityDataSet();
//        if (!dataset.load(rec)) {
//            System.out.println ("User Dataset creation is failed");
//        }
//        dataset.normalize();
//        System.out.println ("Users' information is analyzed");
//        //DataSet<DataField<float []>> acdb = dataset.basedOnActivityAsMCHSeries(Activity.Jogging);
//        Dataset<DataField<float []>> acdb = dataset.basedOnActivitiesAsMCHSeries ();
//        Dataset<DataField<float []>> dbtest = new Dataset<>(acdb);
//        //DataSet<DataField<float []>> acdb = dataset.basedOnActivity(Activity.Sitting);
//        //DataSet<DataField<float []>> acdb = dataset.basedOnActivity();
//        System.out.println ("Activity is extracted");
//        // Neural Network
//        LinkedList<float []> list = new LinkedList<>();
//        list.add(new float [100]);
//        list.add(new float [100]);
//        list.add(new float [100]);
//        NeuronGroup<DataField<float []>> group = new NeuronGroup<DataField<float[]>>(list);
//        OSSSMGNGTrainSupervisor2 ossgng = 
//                new OSSSMGNGTrainSupervisor2 (new NeuronDistanceMethod (new MDDTWDistanceMethod (
//                                             new FullWindowSW(), 
//                                             new MinimumDimDistancePolicy())));
//        group.supervisors.add (ossgng);
//        group.analyzers.add((NeuronAnalyzer<DataField<float []>>) (String state, Neuron<DataField<float []>> n,
//                        ParametersContainer param, DataField<float []> current,
//                        NNResultContainer result) -> {
//            System.out.println (param.get(Neuron.RECIEVED_SIGNALS_NUM));
//        });
//        ossgng.initialize(group);
//        group.parameters.put(GNG_MAX_EDGE_AGE_KEY, 10);
//        group.parameters.put(GNG_LANDA_KEY, 30);
//        
//        SignalGenerator<DataField<float []>> sig = new ListedSignalGenerator(acdb, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
//        NeuronSignalGenerator<DataField<float []>> sg = new NeuronSignalGenerator<DataField<float []>>(sig);
//        
//        NNResultContainer result = new NNResultContainer ();
//        sg.signalAll(group, ossgng.getName(), result);
//        System.out.println("Neural Network training is finished ...");        // TODO code application logic here
//        GNGSimulateSupervisor ss = new GNGSimulateSupervisor(
//                                   new NeuronDistanceMethod (
//                                   new MDDTWDistanceMethod (new FullWindowSW (), 
//                                                            new MinimumDimDistancePolicy())));
//        group.supervisors.add(ss);
//        Random r = new Random(System.currentTimeMillis());
//        int numtrue = 0;
//        for (int i = 0; i < 1000; i++) {
//            NNResultContainer res = new NNResultContainer ();
//            int rin = r.nextInt(dbtest.size());
//            DataField<float []> tmp = dbtest.get(rin);
//            group.feed(ss.getName(), tmp, res);
//            Neuron n = res.getRecent().winners.get(0);
//            
//            String lbl = (String) n.parameters.get(OSSSMGNGTrainSupervisor.NEURON_LABEL);
//            System.out.println (tmp.label + " --> " + lbl);
//            if (tmp.label.contentEquals(lbl)) {
//                numtrue++;
//            }
//        }
//        System.out.println ("RATE: " + (float) numtrue / 1000);
//    }
//    
}
