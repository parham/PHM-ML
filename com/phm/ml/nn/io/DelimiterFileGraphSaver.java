
package com.phm.ml.nn.io;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.connection.Connection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.Graph;

/**
 *
 * @author phm
 */
public class DelimiterFileGraphSaver extends FileGraphSaver {

    protected String delimNeuron = ",";
    protected String delimConnection = ";";
    
    public DelimiterFileGraphSaver () {
        // Empty body
    }
    
    public DelimiterFileGraphSaver (File nf) {
        setGraphFile(nf);
    }

    public void setNeuronDelimeter (String dlm) {
        delimNeuron = Objects.requireNonNull(dlm);
    }
    public String getNeuronDelimeter () {
        return delimNeuron;
    }
    public void setConnectionDelimeter (String dlm) {
        delimConnection = Objects.requireNonNull(dlm);
    }
    public String getConnectionDelimeter () {
        return delimConnection;
    }
    
    @Override
    public boolean save(Graph<Neuron, Connection> g) {
        FileWriter fw = null;
        try {
            Set<Connection> cons = g.edgeSet();
            fw = new FileWriter(graphFile);
            for (Connection c : cons) {
                String str = "";
                Neuron fn = c.neuronOne;
                for (int dim = 0; dim < fn.noAttributes() - 1; dim++) {
                    str += fn.value (dim) + delimNeuron;
                }
                str += fn.value (fn.noAttributes() - 1) + delimConnection;
                Neuron sn = c.neuronTwo;
                for (int dim = 0; dim < sn.noAttributes() - 1; dim++) {
                    str += sn.value (dim) + delimNeuron;
                }
                str += sn.value (sn.noAttributes() - 1) + "\n";
                fw.write (str);
            }
            fw.flush();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DelimiterFileGraphSaver.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(DelimiterFileGraphSaver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
}
