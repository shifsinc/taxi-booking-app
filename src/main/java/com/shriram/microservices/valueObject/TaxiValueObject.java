package com.shriram.microservices.valueObject;

import com.shriram.microservices.model.taxi.Taxi;

/**
 * Created by TSShriram on 13/10/2016.
 */
public class TaxiValueObject {

    private String id;
    private LocationValueObject location;

    public TaxiValueObject() {

    }

    public TaxiValueObject(String id, LocationValueObject location) {
        this.id = id;
        this.location = location;
    }

    public TaxiValueObject(Taxi taxi) {
        this();
        this.id = taxi.id();
        this.location = new LocationValueObject(taxi.location());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocationValueObject getLocation() {
        return location;
    }

    public void setLocation(LocationValueObject location) {
        this.location = location;
    }

    /**
     * Dont use the default Java standard get/set/is before the method you dont want to send as response.
     * Else, Jackson mapper sees this is a problem and will look for serializers in model
     **/
    public Taxi createTaxiObject() {
        Taxi taxi = new Taxi(this.id);
        taxi.location(this.location.createLocationObject());
        return taxi;
    }

}
