package com.shriram.microservices.model.customer;

import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;

/**
 * Created by TSShriram on 13/10/2016.
 * Represents a customer who has a current location and a taxi once its assigned.
 */
public class Customer {

    //primary attribute
    private String id;

    private String name;
    private Taxi taxi;
    private Location location;

    public Customer(String id) {
        this.id = id;
    }

    //no setter because primary attribute and it would make object mutable.
    public String id() {
        return id;
    }

    //Renaming all getters and setters to express more intent via attribute naming likeliness. Access attribute like customer.location();
    public Location location() {
        return location;
    }

    public void location(Location location) {
        this.location = location;
    }

    public Taxi taxi() {
        return taxi;
    }

    public void taxi(Taxi taxi) {
        this.taxi = taxi;
    }

    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

}
