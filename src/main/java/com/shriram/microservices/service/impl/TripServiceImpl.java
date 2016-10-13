package com.shriram.microservices.service.impl;

import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.model.trip.Trip;
import com.shriram.microservices.service.TaxiService;
import com.shriram.microservices.service.TripService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Qualifier(value = "tripService")
public class TripServiceImpl implements TripService {

    Logger logger = Logger.getLogger(TripServiceImpl.class);

    @Override
    public Trip startTrip(Customer customer, Taxi taxi, Location startLocation) {
        return null;
    }

    @Override
    public Trip endTrip(Trip trip) {
        return null;
    }

}
