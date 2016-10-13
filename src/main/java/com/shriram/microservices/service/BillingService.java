package com.shriram.microservices.service;

import com.shriram.microservices.model.bill.Bill;
import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.model.trip.Trip;

public interface BillingService {

    /**
     * Retrieves the bill for a trip
     * <p>
     * This method generates a bill for the trip, or if already present retrieves the bill
     *
     * @return Trip
     */
    Bill retrieveBill(Trip trip);

}
