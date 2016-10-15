package com.shriram.microservices.service;

import com.shriram.microservices.config.MicroservicesServletTest;
import com.shriram.microservices.model.bill.Bill;
import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.model.trip.Trip;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MicroservicesServletTest.class)
public class TripServiceTest extends TestCase {

    @InjectMocks
    @Autowired
    private TripService tripService;

    @Mock
    private BillingService billingService;

    private Taxi taxi;
    private Location location;
    private Customer customer;
    private Trip startTrip;
    private Trip endTrip;

    Logger logger = Logger.getLogger(TripServiceTest.class);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        taxi = new Taxi("1");
        location = new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323"));
        taxi.location(location);
        taxi.pink(false);

        startTrip = new Trip("1");
        endTrip = new Trip("2");

        startTrip.customer(new Customer("1"));
        startTrip.taxi(taxi);
        startTrip.startLocation(location);
        startTrip.startTime("10/13/2016 19:00:00");

        customer = new Customer("1");
    }


    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void returnStartTripTest() {
        assertEquals(tripService.startTrip(customer, taxi, location, "10/13/2016 19:00:00").customer().id(), "1");
    }

    @Test
    public void endTripTest() {
        when(billingService.retrieveBill(Mockito.any(Trip.class))).thenReturn(new Bill(25, 25));
        assertEquals(tripService.endTrip(endTrip).customer().id(), "1");
    }

}
