
package com.phm.ml.distance.dtw;

import com.phm.ml.ParametersContainer;
import com.phm.ml.distance.DimensionalDistanceMeasure;
import com.phm.ml.distance.DistanceInfo;
import com.phm.ml.distance.DistanceMethod;
import com.phm.ml.distance.IneffectiveDDM;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import net.sf.javaml.core.Instance;

/**
 * <p>
 * <b>Publication details:<br></b>
 * <b>Authors:</b> Kruskall, J. & M. Liberman <br>
 * <b>Year:</b> 1983 <br>
 * <b>Title:</b> The Symmetric Time Warping Problem: From Continuous to Discrete <br>
 * <b>Published In:</b> Time Warps, String Edits and Macromolecules: The Theory and Practice of Sequence Comparison <br>
 * <b>Page:</b> 125-161 <br>
 * </p>
 * @author Parham Nooralishahi - PHM!
 * @email parham.nooralishahi@gmail.com
 */
public class DTWDistanceMethod extends DistanceMethod {
    
    protected SearchWindow searchWindow;
    protected DimensionalDistanceMeasure policy;
    protected boolean movedleft = true;
    protected boolean movedup = true;
    protected boolean moveleftup = true;
    
    
    public DTWDistanceMethod (SearchWindow sw) {
        this (sw, new IneffectiveDDM());
    }
    public DTWDistanceMethod (SearchWindow sw, DimensionalDistanceMeasure p) {
        searchWindow = Objects.requireNonNull (sw);
        policy = Objects.requireNonNull(p);
    }
    public DTWDistanceMethod (DimensionalDistanceMeasure p) {
        this (new FullWindowSW(), p);
    }
    public DTWDistanceMethod () {
        searchWindow = new FullWindowSW();
    }
    
    public void moveDirectToLeft (boolean s) {
        movedleft = s;
    }
    public boolean doesMoveDirectToLeft () {
        return movedleft;
    }
    public void moveDirectToUp (boolean s) {
        movedup = s;
    }
    public boolean doesMoveDirectToUp () {
        return movedup;
    }
    public void moveLeftnUp (boolean s) {
        moveleftup = s;
    }
    public boolean doesMoveLeftnUp () {
        return moveleftup;
    }
    
    protected void afterCalculateCostMatrix (Instance sc1, 
                                             Instance sc2, 
                                             CostMatrix cm, ParametersContainer pc) {
        // Empty body
    }
    protected void beforeCalculateCostMatrix (Instance sc1, 
                                              Instance sc2, 
                                              ParametersContainer pc) {
        // Empty body
    }
    public CostMatrix onCalculateCostMatrix (Instance sc1, 
                                             Instance sc2, 
                                             ParametersContainer pc) {          
        final Double [] s1 = sc1.values().toArray(new Double [0]);
        final Double [] s2 = sc2.values().toArray(new Double [0]);
        
        final int maxRow = s1.length;
        final int maxCol = s2.length;
        // Initialize Cost Matrix
        CostMatrix costMatrix = new CostMatrix(maxRow, maxCol);
        
        double [][] costTemp = new double [maxRow][maxCol];
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                double dc = s2 [col] - s1 [row];
                costTemp [row][col] = dc * dc;
            }
        }
        costMatrix.set (0, 0, costTemp [0][0]);
        
        for (int row = 1; row < maxRow; row++) {
            costMatrix.set(row, 0, costTemp [row][0] + 
                                   costMatrix.get(row - 1, 0));
        }
        for (int col = 1; col < maxCol; col++) {
            costMatrix.set (0, col, costTemp [0][col] + 
                                    costMatrix.get(0, col - 1));
        }
        for (int row = 1; row < maxRow; row++) {
            for (int col = 1; col < maxCol; col++) {
                costMatrix.set(row, col, costTemp [row][col] +
                        Math.min(costMatrix.get(row - 1, col), 
                        Math.min(costMatrix.get(row - 1, col - 1),
                                 costMatrix.get(row, col - 1))));
            }
        }
        return costMatrix;
    }
    
    protected List<DTWDistanceInfo.WarpingIndex> findNeighbors (SearchWindow win, int row, int col) {
        LinkedList<DTWDistanceInfo.WarpingIndex> list = new LinkedList<>();
        int r, c;
        if (doesMoveDirectToUp()) {
            r = row - 1;
            c = col;
            if (win.isValid(r,c)) list.add(new DTWDistanceInfo.WarpingIndex(r, c, win.get(r,c)));
        }
        if (doesMoveDirectToLeft()) {
            r = row;
            c = col - 1;
            if (win.isValid(r,c)) list.add(new DTWDistanceInfo.WarpingIndex(r, c, win.get(r,c)));
        }
        if (doesMoveLeftnUp()) {
            r = row - 1;
            c = col - 1;
            if (win.isValid(r,c)) list.add(new DTWDistanceInfo.WarpingIndex(r, c, win.get(r,c)));            
        }
       
        return list;
    }
    public void beforeCalculateDTWDistance (Instance sc1, 
                                            Instance sc2, 
                                            CostMatrix costMatrix,
                                            ParametersContainer pc) {
        // Empty body
    }
    public DTWDistanceInfo onCalculateDTWDistance (Instance sc1, 
                                                   Instance sc2, 
                                                   CostMatrix costMatrix,
                                                   ParametersContainer pc) {
        final int maxRow = sc1.noAttributes();
        final int maxCol = sc2.noAttributes();
        
        final double dist = costMatrix.get(maxRow - 1, maxCol - 1);
        LinkedList<DTWDistanceInfo.WarpingIndex> path = new LinkedList<>();
        
        float k = 1;
        int row = maxRow - 1;
        int col = maxCol - 1;
        DTWDistanceInfo.WarpingIndex temp = new DTWDistanceInfo.WarpingIndex(row, col, dist);
        path.add (temp);
        while (row >= 0 && col >= 0) {
            List<DTWDistanceInfo.WarpingIndex> neighbors = findNeighbors(searchWindow, row, col);
            if (neighbors.size() < 1) break;
            DTWDistanceInfo.WarpingIndex minw = neighbors.get(0);
            for (DTWDistanceInfo.WarpingIndex wi : neighbors) {
                if (minw.value > wi.value) {
                    minw = wi;
                }
            }
            row = minw.row;
            col = minw.col;
            temp = new DTWDistanceInfo.WarpingIndex(minw.row, minw.col, minw.value);
            k++;
            path.add(temp);
        }
       
        //(dist, new float[s1.length], costMatrix, path, k);
        DTWDistanceInfo dinfo = new DTWDistanceInfo ();
        dinfo.entityOne = sc1;
        dinfo.entityTwo = sc2;
        dinfo.distance = dist;
        dinfo.costMatrix = costMatrix;
        dinfo.normalizingFactor = k;
        dinfo.warpingPath = path;
        dinfo.distancedim = policy.calculate(sc1, sc2, dinfo);

        return dinfo;
    }
    public void afterCalculateDTWDistance (Instance sc1, 
                                           Instance sc2, 
                                           CostMatrix costMatrix,
                                           ParametersContainer pc) {
        // Empty body
    }
    
    @Override
    public DistanceInfo distance (Instance sc1, 
                                  Instance sc2, 
                                  ParametersContainer pc) {
        
        beforeCalculateCostMatrix(sc1, sc2, pc);
        final CostMatrix costMatrix = onCalculateCostMatrix(sc1, sc2, pc);
        afterCalculateCostMatrix(sc1, sc2, costMatrix, pc);
        searchWindow.initialize(costMatrix);
        
        beforeCalculateDTWDistance(sc1, sc2, costMatrix, pc);
        DTWDistanceInfo dinfo = onCalculateDTWDistance(sc1, sc2, costMatrix, pc);
        afterCalculateDTWDistance(sc1, sc2, costMatrix, pc);
        return dinfo;
    }
}
