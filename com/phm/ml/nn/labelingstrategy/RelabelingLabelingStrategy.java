
package com.phm.ml.nn.labelingstrategy;
 
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import java.util.List;
import net.sf.javaml.core.Instance;

/**
 *
 * @author phm
 */
public class RelabelingLabelingStrategy extends LabelingStrategy {
    @Override
    public void label(NeuronGroup parent, 
                      List<Neuron> winners, 
                      Instance current) {
        if (winners.size() > 0) {
            winners.get(0).setParameter(NEURON_LABEL, (String) current.classValue());
        }
    }
}
