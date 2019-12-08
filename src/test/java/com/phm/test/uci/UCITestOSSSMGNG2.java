
package com.phm.test.uci;

/**
 *
 * @author phm
 */
public class UCITestOSSSMGNG2 {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String [] args) throws IOException {
//        System.out.println ("Dataset is loading ...");
//        List<UCILoader.UCIReaderRecord> list = 
//                UCILoader.loadDSRecords("/home/phm/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Action Recognition/Dataset/UCI HAR/UCI HAR Dataset/train/Inertial Signals/",
//                                        "/home/phm/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/Action Recognition/Dataset/UCI HAR/UCI HAR Dataset/train/y_train.txt");
//        System.out.println ("Dataset is converting ...");
//        // DataSet<DataField<float []>> ds = UCILoader.loadAsTimeSeriesDataSet(list);
//        Dataset<DataField<float []>> db = UCILoader.loadAsTimeSeriesDataSet(list);
//        LinkedList<DataField<float []>> dbtest = new LinkedList<>(db);
//        System.out.println ("Dataset size ---> " + db.size());
//        System.out.println ("Learning ...");
//        
//        LinkedList<float []> ldb = new LinkedList<>();
//        ldb.add(new float [100]);
//        ldb.add(new float [100]);
//        NeuronGroup<DataField<float []>> group = new NeuronGroup<>(ldb);
//        OSSSMGNGTrainSupervisor2 ossgng = 
//                new OSSSMGNGTrainSupervisor2(new NeuronDistanceMethod (new MDDTWDistanceMethod (
//                                             new FullWindowSW(), 
//                                             new MinimumDimDistancePolicy())));
//        group.supervisors.add (ossgng);
//        group.analyzers.add((NeuronAnalyzer<DataField<float []>>) (String state, Neuron<DataField<float []>> n,
//                        ParametersContainer param, DataField<float []> current,
//                        NNResultContainer result) -> {
//            System.out.println (param.get(Neuron.RECIEVED_SIGNALS_NUM));
//        });
//        ossgng.initialize(group);
//        group.parameters.put(GNG_MAX_EDGE_AGE_KEY, 80);
//        group.parameters.put(GNG_LANDA_KEY, 400);
//        group.setInputStrategy(new OSSSMGNGInputStrategy<>(new InefficientNormalizeMethod(),
//                                                           new InefficientFilterMethod ()));
//        SignalGenerator<DataField<float []>> sig = new ListedSignalGenerator(db, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
//        NeuronSignalGenerator<DataField<float []>> sg = new NeuronSignalGenerator<DataField<float []>>(sig);        
//        NNResultContainer result = new NNResultContainer ();
//        sg.signalAll(group, ossgng.getName(), result);
//        System.out.println("training procedure is finished ...");
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
