package com.shriram.microservices.controller;

import com.shriram.microservices.config.MicroservicesServletTest;
import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.service.TaxiService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by TSShriram on 15/10/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MicroservicesServletTest.class)
public class TaxiControllerTest extends TestCase {

    @Autowired
    private WebApplicationContext wac;

    @InjectMocks
    @Autowired
    TaxiController taxiController;

    @Mock
    TaxiService taxiService;

    private MockMvc mockMvc;

    private final static String TAXI_SEARCH_URL = "/taxi/search";
    private final static String LATITUDE = "1.234";
    private final static String LONGITUDE = "1.232";
    private final static String CONTENT_TYPE = "application/json;charset=UTF-8";
    private final static String CONTENT_TYPE_JSON = "application/json";
    private final static String MESSAGE_KEY = "message";
    private final static String CUSTOMER_ID = "1";
    private final static String BOOK_TAXI_URL = "/taxi/book";

    //Mock data
    private Taxi taxi1;
    private Taxi taxi2;
    private Taxi taxi3;
    private Taxi taxi4;
    List<Taxi> taxis = new ArrayList<>();
    Customer customer;
    Location location = new Location(Double.parseDouble(LATITUDE), Double.parseDouble(LONGITUDE));

    Customer retrievedCustomer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        //hardcoding taxis
        taxi1 = new Taxi("1");
        taxi1.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));

        taxi2 = new Taxi("2");
        taxi2.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));

        taxi3 = new Taxi("3");
        taxi3.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));

        taxi4 = new Taxi("4");
        taxi4.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));

        taxis.add(taxi1);
        taxis.add(taxi2);
        taxis.add(taxi3);
        taxis.add(taxi4);
        customer = new Customer(CUSTOMER_ID);


    }

    @Test
    public void startTripLongitudeLatitudeMissingTest() throws Exception {
        this.mockMvc
                .perform(get(TAXI_SEARCH_URL).accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isBadRequest()).andExpect(content().contentType(CONTENT_TYPE_JSON));
    }

    @Test
    public void startTripLatitudeMissingTest() throws Exception {
        this.mockMvc
                .perform(get(TAXI_SEARCH_URL + "?longitude=" + LONGITUDE).accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isBadRequest()).andExpect(content().contentType(CONTENT_TYPE_JSON))
                .andExpect(jsonPath(MESSAGE_KEY).value("Required String parameter 'latitude' is not present"));
    }

    @Test
    public void startTripLongitudeMissingTest() throws Exception {
        this.mockMvc
                .perform(get(TAXI_SEARCH_URL + "?latitude=" + LATITUDE).accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isBadRequest()).andExpect(content().contentType(CONTENT_TYPE_JSON))
                .andExpect(jsonPath(MESSAGE_KEY).value("Required String parameter 'longitude' is not present"));
    }

    @Test
    public void startTripTest() throws Exception {
        when(taxiService.searchTaxis(Mockito.any(Location.class))).thenReturn(taxis);
        this.mockMvc
                .perform(get(TAXI_SEARCH_URL + "?longitude=" + LONGITUDE + "&latitude=" + LATITUDE).accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    public void bookTaxiTest() throws Exception {
        when(taxiService.searchTaxi(location, Boolean.getBoolean("true"))).thenReturn(taxi1);
        when(taxiService.bookTaxi(taxi1, customer)).thenReturn(customer);
        taxi1.inTransit(true);
        customer.taxi(taxi1);
        this.mockMvc
                .perform(post(BOOK_TAXI_URL + "?customerID=" + CUSTOMER_ID + "&latitude=" + LATITUDE + "&longitude=" + LONGITUDE + "&isPink=true").accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.message").value("booked")).andExpect(jsonPath("$.taxi.id").value("1"));
    }

    @Test
    public void bookTaxiCustomerIDMissingTest() throws Exception {
        when(taxiService.searchTaxi(location, Boolean.getBoolean("true"))).thenReturn(taxi1);
        when(taxiService.bookTaxi(taxi1, customer)).thenReturn(customer);
        taxi1.inTransit(true);
        customer.taxi(taxi1);
        this.mockMvc
                .perform(post(BOOK_TAXI_URL + "?latitude=" + LATITUDE + "&longitude=" + LONGITUDE + "&isPink=true").accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void bookTaxiLatitudeMissingTest() throws Exception {
        when(taxiService.searchTaxi(location, Boolean.getBoolean("true"))).thenReturn(taxi1);
        when(taxiService.bookTaxi(taxi1, customer)).thenReturn(customer);
        taxi1.inTransit(true);
        customer.taxi(taxi1);
        this.mockMvc
                .perform(post(BOOK_TAXI_URL + "?customerID=" + CUSTOMER_ID + "&longitude=" + LONGITUDE + "&isPink=true").accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void bookTaxiLongitudeMissingTest() throws Exception {
        when(taxiService.searchTaxi(location, Boolean.getBoolean("true"))).thenReturn(taxi1);
        when(taxiService.bookTaxi(taxi1, customer)).thenReturn(customer);
        taxi1.inTransit(true);
        customer.taxi(taxi1);
        this.mockMvc
                .perform(post(BOOK_TAXI_URL + "?customerID=" + CUSTOMER_ID + "&latitude=" + LATITUDE + "&isPink=true").accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isBadRequest());
    }
}
