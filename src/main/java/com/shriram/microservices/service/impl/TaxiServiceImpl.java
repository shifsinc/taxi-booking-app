package com.shriram.microservices.service.impl;

import com.shriram.microservices.model.location.Location;
import com.shriram.microservices.model.taxi.Taxi;
import com.shriram.microservices.service.TaxiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Qualifier(value = "taxiService")
public class TaxiServiceImpl implements TaxiService {


    Logger logger = Logger.getLogger(TaxiServiceImpl.class);

    @Override
    public Taxi searchTaxi(String id) {
        return null;
    }

    @Override
    public List<Taxi> searchTaxis(Location location) {
        return Collections.EMPTY_LIST;
    }

}
