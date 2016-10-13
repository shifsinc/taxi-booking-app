package com.shriram.microservices.service.impl;

import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.service.TaxiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
        taxi.inTransit(false);

        return taxi;
    }

    @Override
    public List<Taxi> searchTaxis(Location location) {

        List<Taxi> taxis = new ArrayList<>();

        //hardcoding taxis
        Taxi taxi1 = new Taxi("1");
        taxi1.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));
        taxi1.inTransit(false);

        Taxi taxi2 = new Taxi("1");
        taxi2.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));
        taxi2.inTransit(false);

        Taxi taxi3 = new Taxi("1");
        taxi3.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));
        taxi3.inTransit(false);

        Taxi taxi4 = new Taxi("1");
        taxi4.location(new Location(Double.parseDouble("23.2323434"), Double.parseDouble("24.232323")));
        taxi4.inTransit(false);

        taxis.add(taxi1);
        taxis.add(taxi2);
        taxis.add(taxi3);
        taxis.add(taxi4);

        return taxis;
    }

}
