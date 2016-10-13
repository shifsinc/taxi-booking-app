package com.shriram.microservices.service.impl;

import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.model.trip.Trip;
import com.shriram.microservices.service.BillingService;
import com.shriram.microservices.service.TaxiService;
import com.shriram.microservices.service.TripService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Qualifier(value = "tripService")
public class TripServiceImpl implements TripService {

    Logger logger = Logger.getLogger(TripServiceImpl.class);

    @Autowired
    @Qualifier("billingService")
    private BillingService billingService;

    @Override
    public Trip startTrip(Customer customer, Taxi taxi, Location startLocation, String startTime) {

        Trip trip = new Trip(customer, taxi, startLocation, startTime);
        return trip;
    }

    @Override
    public Trip endTrip(Trip trip) {

        //Retrieving the bill and setting to trip
        trip.bill(billingService.retrieveBill(trip));
        return trip;
    }

}
