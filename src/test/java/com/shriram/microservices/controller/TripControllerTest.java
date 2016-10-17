package com.shriram.microservices.controller;

import com.shriram.microservices.config.MicroservicesServletTest;
import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.model.trip.Trip;
import com.shriram.microservices.service.TripService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by TSShriram on 17/10/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MicroservicesServletTest.class)
public class TripControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @InjectMocks
    @Autowired
    TripController tripController;

    @Autowired
    @Qualifier("domainProperties")
    private Properties domainProperties;

    @Mock
    TripService tripService;

    private MockMvc mockMvc;

    private final static String LATITUDE = "1.234";
    private final static String LONGITUDE = "1.232";
    private final static String CONTENT_TYPE = "application/json;charset=UTF-8";
    private final static String CONTENT_TYPE_JSON = "application/json";
    private final static String MESSAGE_KEY = "message";
    private final static String CUSTOMER_ID = "1";
    private final static String TIMESTAMP_FORMAT = "datetime.token.format";
    private final static String START_TRIP_URL = "/trip/start";
    private final static String REQUEST_JSON = "";
    private final static String END_TRIP_URL = "/trip/end";
    private final static String ID = "1";

    //Mock data
    private Taxi taxi1;
    private Taxi taxi2;
    private Taxi taxi3;
    private Taxi taxi4;
    List<Taxi> taxis = new ArrayList<>();
    Location startLocation = new Location(Double.parseDouble(LATITUDE), Double.parseDouble(LONGITUDE));
    Trip retrievedTrip;
    Trip trip;
    Customer customer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        taxi1 = new Taxi("1");
//        taxi1.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));

        //Setting up taxi details
        taxi1.location(startLocation);
        taxi1.inTransit(true);

        //customer details
        customer = new Customer(CUSTOMER_ID);
        customer.location(startLocation);
        customer.taxi(taxi1);

        //Trip Details received as input
        trip = new Trip(CUSTOMER_ID);


        //Trip Details
        retrievedTrip = new Trip(CUSTOMER_ID);
        retrievedTrip.customer(customer);
        retrievedTrip.taxi(taxi1);
        retrievedTrip.startLocation(startLocation);
        retrievedTrip.startTime("10/13/2016 19:00:00");
        retrievedTrip.endTime("10/13/2016 19:15:00");
    }
//
//    @Test
//    public void startTripTest() throws Exception{
//
//        when(tripService.startTrip(customer,taxi1,startLocation, DateTimeUtil.getFormattedCurrentTime(domainProperties.getProperty(TIMESTAMP_FORMAT)))).thenReturn(retrievedTrip);
//        this.mockMvc
//                .perform(post(START_TRIP_URL).accept(MediaType.parseMediaType("application/json"))
//                        .content(new ObjectMapper().writeValueAsString(tripValueObject)))
//                .andExpect(status().isOk());
//    }

    @Test
    public void endTripTest() throws Exception {
        when(tripService.endTrip(trip)).thenReturn(retrievedTrip);

        this.mockMvc
                .perform(post(END_TRIP_URL + "?id=" + 1 + "&latitude=" + LATITUDE
                        + "&longitude=" + LONGITUDE)
                        .accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk());

    }
}
