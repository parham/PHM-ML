
package com.phm.test.opportunity;

/**
 *
 * @author phm
 */
public class OpportunityTestOSSSMGNG {
//
//    /**
//     * @param args the command line arguments
//     * @throws java.io.IOException
//     */
//    public static void main(String[] args) throws IOException {
//        
//        //LinkedList<OpportunityDSRecord> list = OpportunityDSLoader.loadAsList (new File("/home/phm/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/new/OpportunityUCIDataset/dataset/S1-ADL1.dat"));
//        Dataset<DataField<float []>> db = OpportunityDSLoader.loadAsActivityDS(
//                new File("/home/phm/Projects/INCREMENTAL NEURAL NETWORK PROJECTS/new/OpportunityUCIDataset/dataset/S1-ADL3.dat"));
//        System.out.println (db.size());
//        OpportunityDSLoader.normalizeActivityDS(db);
//        Dataset<DataField<float []>> dbtest = new Dataset<>(db);
//        System.out.println ("DataSet is normalized --> " + db.size());
//        System.out.println ("Dataset is loaded ...");
//        System.out.println ("Learning ...");
//        LinkedList<float []> ldb = new LinkedList<>();
//        ldb.add(new float [100]);
//        ldb.add(new float [100]);
//        NeuronGroup<DataField<float []>> group = new NeuronGroup<>(ldb);
//        OSSSMGNGTrainSupervisor ossgng = 
//                new OSSSMGNGTrainSupervisor (new NeuronDistanceMethod (new MDDTWDistanceMethod (
//                                             new FullWindowSW(), 
//                                             new MinimumDimDistancePolicy())));
//        group.supervisors.add (ossgng);
//        group.analyzers.add((NeuronAnalyzer<DataField<float []>>) (String state, Neuron<DataField<float []>> n,
//                        ParametersContainer param, DataField<float []> current,
//                        NNResultContainer result) -> {
//            System.out.println (param.get(Neuron.RECIEVED_SIGNALS_NUM));
//            System.out.println (((DataField<float []>) current).label + " --> " + 
//                                result.getRecent().winners.get(0).parameters.get(OSSSMGNGTrainSupervisor.NEURON_LABEL) + ":" +
//                                result.getRecent().winners.get(0).parameters.get(GNGTrainingSupervisor.NEURON_DISTANCE));
////            System.out.print (((DataField<float []>) current).label + " --> ");
////            result.getRecent().winners.stream().forEach((Neuron x) -> {
////                System.out.print (x.parameters.get(OSSSMGNGTrainSupervisor.NEURON_LABEL) + ":" + 
////                                  x.parameters.get(GNGTrainingSupervisor.NEURON_DISTANCE) + "\t");
////            });
////            System.out.println();
//        });
//        ossgng.initialize(group);
//        group.parameters.put(GNG_MAX_EDGE_AGE_KEY, 40);
//        group.parameters.put(GNG_LANDA_KEY, 100);
//        group.setInputStrategy(new OSSSMGNGInputStrategy<>(new InefficientNormalizeMethod(),
//                                                           new InefficientFilterMethod ()));
//        SignalGenerator<DataField<float []>> sig = new ListedSignalGenerator(db, ListedSignalGenerator.GeneratorPolicy.GP_RANDOM);
//        NeuronSignalGenerator<DataField<float []>> sg = new NeuronSignalGenerator<>(sig);        
//        NNResultContainer result = new NNResultContainer ();
//        sg.signalAll(group, ossgng.getName(), result);
//        System.out.println ("List all neurons' label");
//        List<Neuron<DataField<float[]>>> ns = group.neurons.toList();
//        ns.stream().forEach((n) -> {
//            System.out.println ((String) n.parameters.get(OSSSMGNGTrainSupervisor.NEURON_LABEL));
//        });
//        System.out.println("Training procedure is finished ...");
//        OSSSMGNGSimulateSupervisor ss = new OSSSMGNGSimulateSupervisor(
//                                   new NeuronDistanceMethod (
//                                   new MDDTWDistanceMethod (new FullWindowSW (), 
//                                                            new MinimumDimDistancePolicy())));
//        group.supervisors.add(ss);
//        Random r = new Random(System.currentTimeMillis());
//        int numtrue = 0;
//        int numtotal = 0;
//        for (int i = 0; i < 100; i++) {
//            int rin = r.nextInt(dbtest.size());
//            DataField<float []> tmp = dbtest.get(rin);
//            NNResultContainer res = new NNResultContainer ();
//            group.feed (ss.getName(), tmp, res);
//            for (NNResult x : res) {
//                Neuron n = x.winners.get(0);
//                String lbl = (String) n.parameters.get(OSSSMGNGTrainSupervisor.NEURON_LABEL);
//                float dis = (float) n.parameters.get(OSSSMGNGSimulateSupervisor.NEURON_DISTANCE);
//                System.out.println (tmp.label + " --> " + lbl + ":" + dis);
//                numtotal++;
//                if (tmp.label.contentEquals(lbl)) {
//                    numtrue++;
//                } 
//            }
//        }
//        System.out.println ("RATE: " + (float) numtrue / numtotal);
//    }
    
}
