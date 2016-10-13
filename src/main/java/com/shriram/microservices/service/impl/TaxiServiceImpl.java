package com.shriram.microservices.service.impl;

import com.shriram.microservices.model.customer.Customer;
import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.service.TaxiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier(value = "taxiService")
public class TaxiServiceImpl implements TaxiService {


    Logger logger = Logger.getLogger(TaxiServiceImpl.class);

    @Override
    public Taxi searchTaxi(Location location) {

        //hardcoding taxi for now
        Taxi taxi = new Taxi("1");
        taxi.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));

        return taxi;
    }

    @Override
    public Customer bookTaxi(Taxi taxi, Customer customer) {

        //though has no significance till an entry is not made to the DB
        taxi.inTransit(true);

        customer.taxi(taxi);

        //TODO Should make an insert to the customer and taxi table, citing customer taxi linking
        return customer;
    }

    @Override
    public List<Taxi> searchTaxis(Location location) {

        List<Taxi> taxis = new ArrayList<>();

        //hardcoding taxis
        Taxi taxi1 = new Taxi("1");
        taxi1.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));

        Taxi taxi2 = new Taxi("1");
        taxi2.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));

        Taxi taxi3 = new Taxi("1");
        taxi3.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));

        Taxi taxi4 = new Taxi("1");
        taxi4.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));

        taxis.add(taxi1);
        taxis.add(taxi2);
        taxis.add(taxi3);
        taxis.add(taxi4);

        return taxis;
    }

}
