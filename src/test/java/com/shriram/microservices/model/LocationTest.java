package com.shriram.microservices.model;

import com.shriram.microservices.config.MicroservicesServletTest;
import com.shriram.microservices.model.location.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

/**
 * Created by TSShriram on 16/10/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MicroservicesServletTest.class)
public class LocationTest {

    @Test
    public void distanceToTest() throws Exception {
        Location start = new Location(2, 3);
        Location end = new Location(3, 4);
        assertEquals(start.distanceTo(end), 1.41421356237, 0.00002);
    }

}
