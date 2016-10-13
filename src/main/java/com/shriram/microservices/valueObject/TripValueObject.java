package com.shriram.microservices.valueObject;

/**
 * Created by TSShriram on 13/10/2016.
 * TripValueObject for request and response for the Trip
 */
public class TripValueObject {

    private CustomerValueObject customer;
    private TaxiValueObject taxi;
    private LocationValueObject startLocation;

    public TripValueObject(CustomerValueObject customer, TaxiValueObject taxi, LocationValueObject startLocation) {
        this.customer = customer;
        this.taxi = taxi;
        this.startLocation = startLocation;
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
