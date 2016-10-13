package com.shriram.microservices.service.impl;

import com.shriram.microservices.model.bill.Bill;
import com.shriram.microservices.model.trip.Trip;
import com.shriram.microservices.service.BillingService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Qualifier(value = "billingService")
public class BillingServiceImpl implements BillingService {

    Logger logger = Logger.getLogger(BillingServiceImpl.class);

    @Autowired
    @Qualifier("domainProperties")
    private Properties domainProperties;

    private final static String TIMESTAMP_FORMAT = "datetime.token.format";

    @Override
    public Bill retrieveBill(Trip trip) {
        Bill bill = new Bill(trip.startLocation().distanceTo(trip.endLocation()), timeTravelled(trip.startTime()));
        bill.totalAmount(calculateBillAmount(trip));
        return bill;
    }

    //calculates bill amount
    private double calculateBillAmount(Trip trip) {
        double distanceTravelled = trip.startLocation().distanceTo(trip.endLocation());
        int timeTravelled = timeTravelled(trip.startTime());
        return trip.taxi().costPerKM() * distanceTravelled + trip.taxi().costPerMinute() * timeTravelled;
    }

    //calculates time travelled in rounded off minutes
    private int timeTravelled(String startTime) {
        DateTime tokenDateTime = new DateTime(DateTime.parse(startTime, DateTimeFormat.forPattern(domainProperties.getProperty(TIMESTAMP_FORMAT))));
        DateTime currentDateTime = new DateTime();
        int timeRemaining = Minutes.minutesBetween(tokenDateTime, currentDateTime).getMinutes();
        return timeRemaining;
    }

}
