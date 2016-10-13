package com.shriram.microservices.model.trip;

import com.shriram.microservices.model.bill.Bill;
import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import org.joda.time.DateTime;

/**
 * Created by TSShriram on 13/10/2016.
 * Represents a trip which is uniquely identified by a customer and a bill
 */
public class Trip {

    private Customer customer;

    //adding this separately as customer.taxi might be different when this trip is completed, and a new trip is begun.
    private Taxi taxi;

    private Location startLocation;
    private Location endLocation;

    private DateTime startTime;
    private DateTime endTime;

    private Bill bill;

    public DateTime getStartTime() {
        return startTime;
    }

    public DateTime endTime() {
        return endTime;
    }

    public void endTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public Trip(Customer customer, Taxi taxi, Location startLocation, DateTime startTime) {
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
