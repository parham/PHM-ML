
package com.phm.test;

import com.phm.ml.regression.Lin2011AutoRegression;
import java.util.LinkedList;
import java.util.List;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import org.apache.commons.math3.filter.DefaultMeasurementModel;
import org.apache.commons.math3.filter.DefaultProcessModel;
import org.apache.commons.math3.filter.KalmanFilter;
import org.apache.commons.math3.filter.MeasurementModel;
import org.apache.commons.math3.filter.ProcessModel;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;

/**
 *
 * @author phm
 */
public class LinAutoRegressionTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // discrete time interval
        double dt = 0.1d;
        // position measurement noise (meter)
        double measurementNoise = 10d;
        // acceleration noise (meter/sec^2)
        double accelNoise = 0.2d;

        // A = [ 1 dt ]
        //     [ 0  1 ]
        RealMatrix A = new Array2DRowRealMatrix(new double[][] { { 1, dt }, { 0, 1 } });
        // B = [ dt^2/2 ]
        //     [ dt     ]
        RealMatrix B = new Array2DRowRealMatrix(new double[][] { { Math.pow(dt, 2d) / 2d }, { dt } });
        // H = [ 1 0 ]
        RealMatrix H = new Array2DRowRealMatrix(new double[][] { { 1d, 0d } });
        // x = [ 0 0 ]
        RealVector x = new ArrayRealVector(new double[] { 0, 0 });

        RealMatrix tmp = new Array2DRowRealMatrix(new double[][] {
            { Math.pow(dt, 4d) / 4d, Math.pow(dt, 3d) / 2d },
            { Math.pow(dt, 3d) / 2d, Math.pow(dt, 2d) } });
        // Q = [ dt^4/4 dt^3/2 ]
        //     [ dt^3/2 dt^2   ]
        RealMatrix Q = tmp.scalarMultiply(Math.pow(accelNoise, 2));
        // P0 = [ 1 1 ]
        //      [ 1 1 ]
        RealMatrix P0 = new Array2DRowRealMatrix(new double[][] { { 1, 1 }, { 1, 1 } });
        // R = [ measurementNoise^2 ]
        RealMatrix R = new Array2DRowRealMatrix(new double[] { Math.pow(measurementNoise, 2) });

        // constant control input, increase velocity by 0.1 m/s per cycle
        RealVector u = new ArrayRealVector(new double[] { 0.1d });

        ProcessModel pm = new DefaultProcessModel(A, B, Q, x, P0);
        MeasurementModel mm = new DefaultMeasurementModel(H, R);
        KalmanFilter filter = new KalmanFilter(pm, mm);
        RandomGenerator rand = new JDKRandomGenerator();

        RealVector tmpPNoise = new ArrayRealVector(new double[] { Math.pow(dt, 2d) / 2d, dt });
        RealVector mNoise = new ArrayRealVector(1);

        // iterate 60 steps
        for (int i = 0; i < 60; i++) {
            filter.predict(u);

            // simulate the process
            RealVector pNoise = tmpPNoise.mapMultiply(accelNoise * rand.nextGaussian());

            // x = A * x + B * u + pNoise
            x = A.operate(x).add(B.operate(u)).add(pNoise);

            // simulate the measurement
            mNoise.setEntry(0, measurementNoise * rand.nextGaussian());

            // z = H * x + m_noise
            RealVector z = H.operate(x).add(mNoise);

            filter.correct(z);

            double position = filter.getStateEstimation()[0];
            double velocity = filter.getStateEstimation()[1];
        }  
        
        ////////////////////////////////////////////////
        double [] weight = {74.0f, 74.0f, 74.1f, 74.2f, 74.5f, 75f, 74.5f, 74.2f, 74.5f, 74.2f, 74.0f, 73.8f, 73.5f, 73.3f, 73.1f, 72.7f, 73.0f,
                           74.1f, 74.0f, 74.5f, 74.8f, 75.0f, 75.1f, 75.2f, 75.5f, 75.2f, 75.5f, 75.2f, 75.0f, 74.9f, 74.8f, 74.8f, 75.0f};
        LinkedList<Instance> ds = new LinkedList<> ();
        for (int index = 0; index < weight.length; index++) {
            DenseInstance ins = new DenseInstance (1);
            ins.put(0, weight [index]);
            ds.add (ins);
        }
        Lin2011AutoRegression ar = new Lin2011AutoRegression(5);
        
        //DefaultAutoRegression ar = new DefaultAutoRegression (5);
        List<Instance> insts = ar.simulate(ds);
        for (int index = 0; index < insts.size(); index++) {
            System.out.println (insts.get(index).value (0));
        }
    }
    
}
