
package com.phm.test.hwds;

/**
 *
 * @author phm
 */
public class HWDBTestOSSGNG {

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
//        LinkedList<DataField<float []>> dbtest = new LinkedList<>();
//        Random rand = new Random(System.currentTimeMillis());
//        for (int index = 0; index < 1070; index++) {
//            int id = rand.nextInt(db.size());
//            DataField<float []> df = db.remove(id);
//            dbtest.add(df);
//        }
//        System.out.println ("Dataset is created ...");
//        LinkedList<float []> ldb = new LinkedList<>();
//        ldb.add(new float [100]);
//        ldb.add(new float [100]);
//        
//        NeuronGroup<DataField<float []>> group = new NeuronGroup<>(ldb);
//        OnlineSemiSupervisedGNGSupervisor ossgng = 
//                new OnlineSemiSupervisedGNGSupervisor (new NeuronDistanceMethod (new MDDTWDistanceMethod (
//                                             new FullWindowSW(), 
//                                             new MinimumDimDistancePolicy())),
//                                             new SingleLinkagePredictionStrategy(),
//                                             new FrequencyBasedLabelingStrategy(),
//                                             OSSSMGNGTrainSupervisor2.OSSSMGNG_DEFAULT_UNLABEL);
//        group.supervisors.add (ossgng);
//        group.analyzers.add((NeuronAnalyzer<DataField<float []>>) (String state, Neuron<DataField<float []>> n,
//                        ParametersContainer param, DataField<float []> current,
//                        NNResultContainer result) -> {
//            System.out.println (param.get(Neuron.RECIEVED_SIGNALS_NUM));
//        });
//        ossgng.initialize(group);
//        group.parameters.put(GNG_MAX_EDGE_AGE_KEY, 20);
//        group.parameters.put(GNG_LANDA_KEY, 83);
//        group.setInputStrategy(new OSSSMGNGInputStrategy<>(new NormNormalizeMethod (),
//                                                           new InefficientFilterMethod ()));
//        
//        SignalGenerator<DataField<float []>> sig = new ListedSignalGenerator(db, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
//        NeuronSignalGenerator<DataField<float []>> sg = new NeuronSignalGenerator<DataField<float []>>(sig);        
//        NNResultContainer result = new NNResultContainer();
//        long runtime = System.currentTimeMillis();
//        sg.signalAll(group, ossgng.getName(), result);
//        System.out.println("training procedure is finished ...");
//        runtime -= System.currentTimeMillis();
//        System.out.println("Runtime --> " + Math.abs(runtime));
//        // 
//        List<Neuron<DataField<float[]>>> ns = group.neurons.toList();
////        ns.stream().forEach((n) -> {
////            System.out.println (n.parameters.get(OnlineSSSimilarityMGNGSupervisor.OSSNEURON_LABEL));
////        }); 
//// Testing procedure
//        GNGSimulateSupervisor ss = new GNGSimulateSupervisor(
//                                   new NeuronDistanceMethod (
//                                   new MDDTWDistanceMethod (new FullWindowSW (), 
//                                                            new MinimumDimDistancePolicy())));
//        group.supervisors.add(ss);
//        runtime = System.currentTimeMillis();
//        Random r = new Random(System.currentTimeMillis());
//        int numtrue = 0;
//        for (int i = 0; i < dbtest.size(); i++) {
//            NNResultContainer res = new NNResultContainer ();
//            long rtime = System.currentTimeMillis();
//            int rin = r.nextInt(dbtest.size());
//            DataField<float []> tmp = dbtest.get(rin);
//            group.feed(ss.getName(), tmp, res);
//            Neuron n = res.getRecent().winners.get(0);
//            String lbl = (String) n.parameters.get(OSSSMGNGTrainSupervisor.NEURON_LABEL);
//            System.out.println (tmp.label + " --> " + lbl);
//            if (tmp.label.contentEquals(lbl)) {
//                numtrue++;
//            }
//            rtime -= System.currentTimeMillis();
//            System.out.println ("T : " + rtime);
//        }
//        runtime -= System.currentTimeMillis();
//        System.out.println("Runtime --> " + Math.abs(runtime));
//        System.out.println ("RATE: " + (float) numtrue);
//        try {
////            NeuronGroupOutputStream out = new NeuronGroupOutputStream(
////                    new FileOutputStream("G:\\PHM - I AM ONE\\Projects\\Review on Neural Network for using on 3D downsampling\\gng.txt"));
//            try (NeuronGroupOutputStream out = new NeuronGroupOutputStream(
//                    new FileOutputStream("/home/phm/osssmgng.txt"))) {
////                            NeuronGroupOutputStream out = new NeuronGroupOutputStream(
////                    new FileOutputStream("G:\\PHM - I AM ONE\\Projects\\Review on Neural Network for using on 3D downsampling\\gng.txt"));
//                out.write(group);
//                out.flush();
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(TestGNG.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(TestGNG.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
