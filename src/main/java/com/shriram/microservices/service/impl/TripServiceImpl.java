package com.shriram.microservices.service.impl;

import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.model.trip.Trip;
import com.shriram.microservices.service.BillingService;
import com.shriram.microservices.service.TripService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(value = "tripService")
public class TripServiceImpl implements TripService {

    Logger logger = Logger.getLogger(TripServiceImpl.class);

    @Autowired
    @Qualifier("billingService")
    private BillingService billingService;

    @Override
    public Trip startTrip(Customer customer, Taxi taxi, Location startLocation, String startTime) {

        //FIXME how to streamline id - keyHolder
        Trip trip = new Trip(customer, taxi, startLocation, startTime);
        return trip;
    }

    @Override
    public Trip endTrip(Trip trip) {

        //TODO - Retrieve from DB
        Location location = new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323"));

        Taxi taxi = new Taxi("1");
        taxi.location(location);
        taxi.inTransit(true);

        Customer customer = new Customer("1");
        customer.location(location);

        trip.customer(customer);
        customer.taxi(taxi);
        trip.taxi(taxi);
        trip.startLocation(location);
        trip.startTime("10/13/2016 19:00:00");
        trip.endTime("10/13/2016 19:15:00");


        //Retrieving the bill and setting to trip
        trip.bill(billingService.retrieveBill(trip));
        return trip;
    }

}
