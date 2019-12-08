
package com.phm.test;

import com.phm.ml.ParametersContainer;
import com.phm.ml.gui.GraphFrame;
import com.phm.ml.gui.GraphVisualComponent;
import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.siggen.ThresholdImage2DSignalGenerator;
import com.phm.ml.triangulation.GNGTriangulator;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.sf.javaml.core.Dataset;
import org.jgrapht.Graph;

/**
 *
 * @author phm
 */
public class TestGNGTriangulator {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        BufferedImage img = ImageIO.read(new File("/home/phm/Pictures/sample2.png"));
        ThresholdImage2DSignalGenerator sig = new ThresholdImage2DSignalGenerator(img, new Color(0, 0, 0), ThresholdImage2DSignalGenerator.GeneratorPolicy.GP_RANDOM);
        Dataset ds = sig.getDataSet();
        //GNGNoLearnTriangulator gng = new GNGNoLearnTriangulator (new ParametersContainer());
        GNGTriangulator gng = new GNGTriangulator(new ParametersContainer());
        System.out.println ("Triangulation is started ... ");
        Graph<Neuron, Connection> g = gng.triangulate(ds);
        System.out.println ("Number of Vertex : " + g.vertexSet().size());
        System.out.println ("Number of Edges : " + g.edgeSet().size());
        GraphFrame<Neuron, Connection> frMain = new GraphFrame();
        frMain.addGraph(new GraphVisualComponent<>(g));
        frMain.setVisible(true);
    }
    
}
