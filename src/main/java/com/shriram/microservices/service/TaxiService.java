package com.shriram.microservices.service;

import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;

import java.util.List;

public interface TaxiService {

    /**
     * Retrieves particular Taxi details
     * <p>
     * This method retrieves a particular Taxi's details given its ID
     *
     * @return Taxi
     */
    Taxi searchTaxi(String id);

    /**
     * Retrieves a list of nearby Taxis
     * <p>
     * This method retrieves a list of nearby taxis given a location
     *
     * @param location
     *            -latitude and longitude supplied
     * @return List of Taxis
     */
    List<Taxi> searchTaxis(Location location);

}
