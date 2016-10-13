package com.shriram.microservices.valueObject;

/**
 * Created by TSShriram on 13/10/2016.
 */
public class TaxiValueObject {

    private String id;
    private LocationValueObject location;

    public TaxiValueObject(String id, LocationValueObject location) {
        this.id = id;
        this.location = location;
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

}
