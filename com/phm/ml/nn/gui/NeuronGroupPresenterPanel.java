
package com.phm.ml.nn.gui;

import com.phm.ml.nn.Neuron;
import com.phm.ml.nn.NeuronGroup;
import com.phm.ml.nn.connection.Connection;
import com.phm.ml.nn.connection.ConnectionsContainer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author phm
 */
public class NeuronGroupPresenterPanel extends javax.swing.JPanel {

    protected NeuronGroup ngroup;
    protected AtomicBoolean drawCon = new AtomicBoolean(true);
    
    public NeuronGroupPresenterPanel () {
        initComponents();
    }
    public NeuronGroupPresenterPanel (NeuronGroup ng) {
        this ();
        setNeuronGroup(ng);
    }
    
    public final void setNeuronGroup (NeuronGroup ng) {
        ngroup = Objects.requireNonNull(ng);
    }
    public final NeuronGroup getNeuronGroup () {
        return ngroup;
    }
    public final void setDrawConnections (boolean s) {
        drawCon.set(s);
        repaint();
    }
    public final boolean getDrawConnections () {
        return drawCon.get();
    }
    
    @Override
    public void paint (Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        if (ngroup != null) {
            LinkedList<Neuron> ns = new LinkedList<>(ngroup.neurons.toList());
            // Draw Neurons
            g2d.setColor(Color.BLUE);
            for (Neuron n : ns) {
                int x = (int) (n.value(0) * getWidth());
                int y = (int) (n.value(1) * getHeight());
                g2d.fillOval(x, y, 10, 10);
            }
            // Draw Connections
            if (drawCon.get()) {
                ConnectionsContainer cc = ngroup.getConnections();
                g2d.setColor(Color.BLACK);
                for (Connection c : cc) {
                    int xp1 = (int) (c.neuronOne.value(0) * getWidth());
                    int yp1 = (int) (c.neuronOne.value(1) * getHeight());
                    int xp2 = (int) (c.neuronTwo.value(0) * getWidth());
                    int yp2 = (int) (c.neuronTwo.value(1) * getHeight());
                    g2d.drawLine(xp1, yp1, xp2, yp2);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
