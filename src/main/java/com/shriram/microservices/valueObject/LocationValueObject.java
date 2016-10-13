package com.shriram.microservices.valueObject;

/**
 * Created by TSShriram on 13/10/2016.
 */
public class LocationValueObject {

    private String latitude;
    private String longitude;

    public LocationValueObject(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
