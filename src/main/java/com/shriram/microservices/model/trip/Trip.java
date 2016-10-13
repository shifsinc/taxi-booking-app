package com.shriram.microservices.model.trip;

import com.shriram.microservices.model.bill.Bill;
import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.taxi.Taxi;

/**
 * Created by TSShriram on 13/10/2016.
 * Represents a trip which is uniquely identified by a customer and a bill
 */
public class Trip {

    private Customer customer;

    //adding this separately as customer.taxi might be different when this trip is completed, and a new trip is begun.
    private Taxi taxi;

    private Bill bill;

    public Trip(Customer customer, Taxi taxi) {
        this.customer = customer;
        this.taxi = taxi;
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

}
