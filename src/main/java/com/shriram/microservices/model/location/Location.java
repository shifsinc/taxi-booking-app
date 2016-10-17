package com.shriram.microservices.model.location;

/**
 * Created by TSShriram on 13/10/2016.
 * This represents a Location on Earth
 */
public class Location {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.latitude, latitude) != 0) return false;
        return Double.compare(location.longitude, longitude) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //no setters as change in latitude or longitude would result in a new location
    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }

    public double distanceTo(Location location) {
        double distance = Math.sqrt(Math.pow(this.longitude - location.longitude, 2) + Math.pow(this.longitude - location.longitude, 2));
        return distance;
    }

}
