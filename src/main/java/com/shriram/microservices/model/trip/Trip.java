package com.shriram.microservices.model.trip;

import com.shriram.microservices.model.bill.Bill;
import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;

/**
 * Created by TSShriram on 13/10/2016.
 * Represents a trip which is uniquely identified by a customer and a bill
 */
public class Trip {

    String id;

    private Customer customer;

    //adding this separately as customer.taxi might be different when this trip is completed, and a new trip is begun.
    private Taxi taxi;

    private Location startLocation;
    private Location endLocation;

    private String startTime;
    private String endTime;

    private Bill bill;

    public String id() {
        return id;
    }

    public String startTime() {
        return startTime;
    }

    public String endTime() {
        return endTime;
    }

    public void endTime(String endTime) {
        this.endTime = endTime;
    }

    public Trip(String id) {
        this.id = id;
    }

    public Trip(Customer customer, Taxi taxi, Location startLocation, String startTime) {
        this.customer = customer;
        this.taxi = taxi;
        this.startLocation = startLocation;
        this.startTime = startTime;
    }

    public Customer customer() {
        return customer;
    }

    public Taxi taxi() {
        return taxi;
    }

    public Bill bill() {
        return bill;
    }

    public void bill(Bill bill) {
        this.bill = bill;
    }

    public Location startLocation() {
        return startLocation;
    }

    public Location endLocation() {
        return endLocation;
    }

    public void endLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

}
