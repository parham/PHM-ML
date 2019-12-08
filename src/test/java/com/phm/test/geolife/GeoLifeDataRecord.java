
package com.phm.test.geolife;

/**
 *
 * @author phm
 */
public class GeoLifeDataRecord {
    public double longitute = 0.0;
    public double latitute = 0.0;
    public double altitute = 0.0;
    public long timestamp = 0;
    public String label = "label";
    
    public GeoLifeDataRecord () {
        //Empty body
    }
    public GeoLifeDataRecord (long time, double lng, double lat, double alt, String lbl) {
        longitute = lng;
        latitute = lat;
        altitute = alt;
        timestamp = time;
        label = lbl;
    }
    public GeoLifeDataRecord (long time, double lng, double lat, double alt) {
        this (time, lng, lat, alt, "");
    }
    public GeoLifeDataRecord (double lng, double lat, double alt) {
        this (System.currentTimeMillis(), lng, lat, alt, "");
    }
    
    @Override
    public String toString () {
        return String.valueOf(timestamp) + " : ( " + label + " ) " + longitute + " " + latitute + " " + altitute;
    }
}
