package com.phm.ml.event;

import java.util.Objects;

/**
 *
 * @author phm
 */
public class Event {
    public final String name;
    public final String description;
    public final long time;
    
    public Event (String nm, String dsc) {
        this (nm, dsc, 0);
    }
    public Event (String nm, String dsc, long t) {
        this.name = Objects.requireNonNull(nm);
        this.description = Objects.requireNonNull(dsc);
        time = t;
    }
    
    @Override
    public String toString () {
        return "EVENT [Name : " + this.name + ", Description : " + this.description + "]";
    }
    @Override
    public boolean equals (Object obj) {
        return (obj != null && obj instanceof Event && ((Event) obj).hashCode() == hashCode());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
