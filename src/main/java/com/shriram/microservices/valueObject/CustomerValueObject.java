package com.shriram.microservices.valueObject;

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

}
