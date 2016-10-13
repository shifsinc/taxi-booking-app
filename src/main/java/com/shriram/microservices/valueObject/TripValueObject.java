package com.shriram.microservices.valueObject;

import com.shriram.microservices.model.trip.Trip;

/**
 * Created by TSShriram on 13/10/2016.
 * TripValueObject for request and response for the Trip
 */
public class TripValueObject {

    private String id;
    private CustomerValueObject customer;
    private TaxiValueObject taxi;
    private LocationValueObject startLocation;

    public TripValueObject(String id, CustomerValueObject customer, TaxiValueObject taxi, LocationValueObject startLocation) {
        this.id = id;
        this.customer = customer;
        this.taxi = taxi;
        this.startLocation = startLocation;
    }

    public TripValueObject(Trip trip) {
        this.id = trip.id();
        this.customer = new CustomerValueObject(trip.customer());
        this.taxi = new TaxiValueObject(trip.taxi());
        this.startLocation = new LocationValueObject(trip.startLocation());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerValueObject getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerValueObject customer) {
        this.customer = customer;
    }

    public TaxiValueObject getTaxi() {
        return taxi;
    }

    public void setTaxi(TaxiValueObject taxi) {
        this.taxi = taxi;
    }

    public LocationValueObject getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LocationValueObject startLocation) {
        this.startLocation = startLocation;
    }

}
