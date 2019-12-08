
package com.phm.ml.nn.io;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.connection.Connection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import org.jgrapht.Graph;

/**
 *
 * @author phm
 */
public class DelimiterFileGraphLoader extends FileGraphLoader {

    protected String delimNeuron = ",";
    protected String delimConnection = ";";
    
    public DelimiterFileGraphLoader () {
        // Empty body
    }
    
    public DelimiterFileGraphLoader (File nf) {
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
    public boolean load (Graph<Neuron, Connection> g) {
        try {
            try (BufferedReader fr = new BufferedReader (new FileReader (graphFile))) {
                fr.lines().forEach((String rec) -> {
                    String [] neurons = rec.split(delimConnection);
                    Neuron [] ns = new Neuron [neurons.length];
                    for (int index = 0; index < neurons.length; index++) {
                        String [] ncent = neurons [index].split(delimNeuron);
                        double [] dims = new double [ncent.length];
                        for (int dim = 0; dim < dims.length; dim++) {
                            String tk = ncent [dim].trim();
                            if (!tk.isEmpty()) {
                                dims [index] = Double.valueOf(tk);
                            }
                        }
                        ns [index] = new Neuron (dims);
                    }
                    g.addVertex(ns [0]);
                    g.addVertex(ns [1]);
                    g.addEdge(ns [0], ns [1]);
                });
            }
            return true;
        } catch (IOException ex) {
            System.out.println (ex);
            return false;
        }
    }
}
