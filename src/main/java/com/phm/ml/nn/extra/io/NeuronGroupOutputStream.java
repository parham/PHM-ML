
package com.phm.ml.nn.extra.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;

/**
 *
 * @author PARHAM
 */
public class NeuronGroupOutputStream extends FilterOutputStream {
	public NeuronGroupOutputStream(OutputStream out) {
		super(out);
	}

	public void write(NeuronGroup ng) throws IOException {
		StringBuilder sb = new StringBuilder();
		List<Neuron> ns = ng.neurons.toList();
		for (Neuron n : ns) {
			for (int index = 0; index < n.noAttributes() - 1; index++) {
				sb.append(n.value(index)).append(",");
			}
			sb.append(n.value(n.noAttributes() - 1));
			sb.append("\n");
		}
		this.write(sb.toString().getBytes());
	}
}
