package com.shriram.microservices.model.taxi;

import com.shriram.microservices.model.location.Location;

/**
 * Created by TSShriram on 13/10/2016.
 * Represents a Taxi, or a cab, or a car owned by the company
 */
public class Taxi {

    //primary field as id is the only one defining a Taxi
    private String id;

    private Location location;

    //These are set to defaults
    private double costPerMinute;
    private double costPerKM;

    public Taxi(String id) {
        this.id = id;
        this.costPerMinute = 1;
        this.costPerKM = 2;
    }

    //no setter for id, as the id defines it
    public String id() {
        return id;
    }

    public Location location() {
        return location;
    }

    public void location(Location location) {
        this.location = location;
    }

    public double costPerMinute() {
        return 1;
    }

    public double costPerKM() {
        return 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Taxi taxi = (Taxi) o;

        if (!id.equals(taxi.id)) return false;
        return location != null ? location.equals(taxi.location) : taxi.location == null;

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
