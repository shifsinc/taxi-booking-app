package com.shriram.microservices.valueObject;

import com.shriram.microservices.model.location.Location;

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

    public LocationValueObject(Location location) {
        this.latitude = Double.toString(location.latitude());
        this.longitude = Double.toString(location.longitude());
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

    public Location getLocationObject() {
        return new Location(Double.parseDouble(this.latitude), Double.parseDouble(this.longitude));
    }

}
