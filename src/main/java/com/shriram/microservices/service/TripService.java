package com.shriram.microservices.service;

import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.model.trip.Trip;

public interface TripService {

    /**
     * Starts a trip
     * <p>
     * This method starts a trip for a customer and a taxi
     *
     * @return Trip
     */
    Trip startTrip(Customer customer, Taxi taxi, Location startLocation, String startTime);

    /**
     * Ends a trip
     * <p>
     * This method ends a trip and returns the bill as a part of the trip
     *
     * @param trip -The trip to be stopped
     * @return Trip with billing details
     */
    Trip endTrip(Trip trip);

}
