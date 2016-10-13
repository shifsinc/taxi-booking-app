package com.shriram.microservices.model.taxi;

import com.shriram.microservices.model.location.Location;

/**
 * Created by TSShriram on 13/10/2016.
 * Represents a Taxi, or a cab, or a car owned by the company
 */
public class Taxi {

    private String id;
    private Location location;
    private double costPerMinute;
    private double costPerKM;

}
