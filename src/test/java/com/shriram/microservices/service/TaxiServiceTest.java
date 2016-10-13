package com.shriram.microservices.service;

import com.shriram.microservices.config.MicroservicesServletTest;
import com.shriram.microservices.dao.AuthenticationDao;
import com.shriram.microservices.model.authentication.Credentials;
import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
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
public class TaxiServiceTest extends TestCase {

    @InjectMocks
    @Autowired
    private TaxiService taxiService;

    private Taxi taxi;
    private Location location;
    private Customer customer;

    Logger logger = Logger.getLogger(TaxiServiceTest.class);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        taxi = new Taxi("1");
        location = new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323"));
        taxi.location(location);
        taxi.pink(false);

        customer = new Customer("1");
    }


    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void returnTaxiTest() {
        assertEquals(taxiService.searchTaxi(location, false).id(), "1");
    }

    @Test
    public void bookTaxiTest() {
        assertEquals(taxiService.bookTaxi(taxi, customer).id(), "1");
    }

    @Test
    public void searchTaxisTest() {
        assertEquals(taxiService.searchTaxis(location).get(0).id(), "1");
    }

}
