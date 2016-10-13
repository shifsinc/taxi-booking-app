package com.shriram.microservices.controller;

import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.model.trip.Trip;
import com.shriram.microservices.service.TripService;
import com.shriram.microservices.util.DateTimeUtil;
import com.shriram.microservices.util.ResponseUtil;
import com.shriram.microservices.valueObject.TripValueObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 * Taxi Controller for search and booking a cab
 * <p>
 * This controller searches and books a cab for a given location and a customer
 *
 * @method - searchTaxis - retrieves all nearby for a given location
 * @method - bookTaxi - books the closest taxi for a given location
 */
@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    @Qualifier("tripService")
    private TripService tripService;

    @Autowired
    @Qualifier("domainProperties")
    private Properties domainProperties;

    private final static String TIMESTAMP_FORMAT = "datetime.token.format";

    Logger logger = Logger.getLogger(TripController.class);

    /**
     * Starts a trip given customer, taxi and location details
     * <p>
     * This method starts a trip given customer, taxi and location details, and returns the trip details along with an unique id
     * <p>
     * url - /trip/start
     *
     * @param request         - HttpServletRequest
     * @param response        - HttpServletResponse
     * @param tripValueObject - trip details
     */
    @RequestMapping(method = RequestMethod.POST, value = "/start")
    @ResponseBody
    public Object startTrip(@RequestBody TripValueObject tripValueObject, HttpServletRequest request,
                            HttpServletResponse response) {
        if (null == tripValueObject) {
            return ResponseUtil.badRequest(response, "missing trip details");
        }

        if (null == tripValueObject.getCustomer() || StringUtils.isEmpty(tripValueObject.getCustomer().getId())) {
            return ResponseUtil.badRequest(response, "missing customer details");
        }

        if (null == tripValueObject.getStartLocation() || StringUtils.isEmpty(tripValueObject.getStartLocation().getLatitude()) || StringUtils.isEmpty(tripValueObject.getStartLocation().getLongitude())) {
            return ResponseUtil.badRequest(response, "missing location details");
        }

        if (null == tripValueObject.getTaxi() || StringUtils.isEmpty(tripValueObject.getTaxi().getId())) {
            return ResponseUtil.badRequest(response, "missing taxi details");
        }


        Customer customer = tripValueObject.getCustomer().getCustomerObject();
        Taxi taxi = tripValueObject.getTaxi().getTaxiObject();
        Location startLocation = tripValueObject.getStartLocation().getLocationObject();

        Trip retrievedTrip = tripService.startTrip(customer, taxi, startLocation, DateTimeUtil.getFormattedCurrentTime(domainProperties.getProperty(TIMESTAMP_FORMAT)));

        return new TripValueObject(retrievedTrip);
    }

    /**
     * Ends the current trip
     * <p>
     * This method ends the current trip given a trip id and location
     * <p>
     * url - /trip/end
     *
     * @param request   - HttpServletRequest
     * @param response  - HttpServletResponse
     * @param id        - ID of the trip
     * @param latitude  - Latitude of the given location
     * @param longitude - Longitude of the given location
     */
    @RequestMapping(method = RequestMethod.POST, value = "/end")
    @ResponseBody
    public Object endTrip(@RequestParam("id") String id, @RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude, HttpServletRequest request,
                          HttpServletResponse response) {

        if (StringUtils.isEmpty(latitude) || StringUtils.isEmpty(longitude)) {
            return ResponseUtil.badRequest(response, "missing latitude/longitude");
        }

        Trip retrievedTrip = tripService.endTrip(new Trip(id));

        return new TripValueObject(retrievedTrip);
    }

}
