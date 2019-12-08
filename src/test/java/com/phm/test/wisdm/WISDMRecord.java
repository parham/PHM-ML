
package com.phm.test.wisdm;

/**
 *
 * @author phm
 */
public class WISDMRecord {
    public long timestamp;
    public int userid;
    public Activity activity;
    public float accX;
    public float accY;
    public float accZ;
    
    public WISDMRecord (long time, int id, Activity actv, 
                        float ax, float ay, float az) {
        timestamp = time;
        userid = id;
        activity = actv;
        accX = ax;
        accY = ay;
        accZ = az;
    }
    public WISDMRecord (int id, Activity actv, 
                        float ax, float ay, float az) {
        this (System.currentTimeMillis(), id, actv, ax, ay, az);
    }
    public WISDMRecord () {
        this (System.currentTimeMillis(), 0, Activity.Walking, 0, 0, 0);
    }
    @Override
    public String toString () {
        return String.valueOf(timestamp) + "," + userid + "," + 
               activity.name + " - (" + accX + "," + accY + "," + accZ + ")";
    }
}
