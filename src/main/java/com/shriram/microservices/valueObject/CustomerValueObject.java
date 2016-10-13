package com.shriram.microservices.valueObject;

import com.shriram.microservices.model.customer.Customer;

/**
 * Created by TSShriram on 13/10/2016.
 */
public class CustomerValueObject {

    private String id;
    private String name;
    private TaxiValueObject taxi;
    private LocationValueObject location;

    public CustomerValueObject(String id, String name, TaxiValueObject taxi, LocationValueObject location) {
        this.id = id;
        this.name = name;
        this.taxi = taxi;
        this.location = location;
    }

    public CustomerValueObject(Customer customer) {
        this.id = customer.id();
        this.name = customer.name();
        this.taxi = new TaxiValueObject(customer.taxi());
        this.location = new LocationValueObject(customer.location());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaxiValueObject getTaxi() {
        return taxi;
    }

    public void setTaxi(TaxiValueObject taxi) {
        this.taxi = taxi;
    }

    public LocationValueObject getLocation() {
        return location;
    }

    public void setLocation(LocationValueObject location) {
        this.location = location;
    }

    public Customer getCustomerObject() {
        Customer customer = new Customer(this.id);
        customer.location(this.location.getLocation());
        return customer;
    }

}
