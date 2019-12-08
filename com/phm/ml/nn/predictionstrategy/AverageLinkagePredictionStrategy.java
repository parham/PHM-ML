
package com.phm.ml.nn.predictionstrategy;
    
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.algorithms.gng.ossgng.OnlineSemiSupervisedGNGSupervisor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class AverageLinkagePredictionStrategy extends PredictionStrategy {
    
    @Override
    public void predict(NeuronGroup net, List<Neuron> winners, Instance signal) {
        String lblsignal = (String) signal.classValue();
        if (lblsignal == null) {
            List<Neuron> ns = net.neurons.toList();
            HashMap<String, PNRecord> table = new HashMap<>();
            // Calculate essentials
            ns.stream().forEach((Neuron n) -> {
                String lt = (String) n.getParameter(NEURON_LABEL);
                float dis = (float) n.getParameter(OnlineSemiSupervisedGNGSupervisor.NEURON_DISTANCE);
                //FMCHDistanceInfo dd = disMethod.distance(net, n, signal);
                //float dis = dd.distance;
                if (!table.containsKey(lt)) {
                    table.put(lt, new PNRecord(lt, 1, dis));
                } else {
                    PNRecord tmp = table.get(lt);
                    tmp.average += dis;
                    tmp.num++;
                    table.put(lt, tmp);
                }
            });
            LinkedList<PNRecord> list = new LinkedList<>(table.values());
            for (PNRecord p : list) {
                p.average /= p.num;
            }
            // Find the best
            PNRecord min = list.get(0);
            for (PNRecord p : list) {
                if (p.average < min.average) {
                    min = p;
                }
            }
            // Assign label
            signal.setClassValue(min.label);
        }
    }
    
    protected class PNRecord {
        String label;
        int num = 0;
        float average = 0.0f;
        
        public PNRecord (String l, int n, float avg) {
            label = l;
            num = n;
            average = avg;
        }
    }
}
