package com.shriram.microservices.service.impl;

import com.shriram.microservices.model.bill.Bill;
import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.model.trip.Trip;
import com.shriram.microservices.service.BillingService;
import com.shriram.microservices.service.TripService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(value = "billingService")
public class BillingServiceImpl implements BillingService {

    Logger logger = Logger.getLogger(BillingServiceImpl.class);

    @Override
    public Bill retrieveBill(Trip trip) {
        return null;
    }

}
