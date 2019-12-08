
package com.phm.test.wisdm;

/**
 *
 * @author phm
 */
public enum Activity {
    Walking ("Walking"),
    Jogging ("Jogging"),
    Sitting ("Sitting"),
    Standing ("Standing"),
    Upstairs ("Upstairs"), 
    Downstairs ("Downstairs");

    public final String name;
    Activity (String nm) {
        name = nm;
    }
}
