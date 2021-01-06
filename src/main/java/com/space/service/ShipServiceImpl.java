package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShipServiceImpl implements ShipService{

    @Autowired
    private ShipRepository shipRepository;

    @Override
    public List<Ship> getShips(String name, String planet, ShipType shipType, Long after,
                               Long before, Boolean isUsed, Double minSpeed, Double maxSpeed,
                               Integer minCrewSize, Integer maxCrewSize, Double minRating,
                               Double maxRating) {

        List<Ship> list = new ArrayList<>();
        Iterable<Ship> iterable = shipRepository.findAll();
        iterable.forEach(list::add);

        List<Ship> shipsList = new ArrayList<>();
        Date afterDate = after == null ? null : new Date(after);
        Date beforeDate = before == null ? null : new Date(before);
        for (Ship ship : list){
            if (name != null && !(ship.getName().contains(name)))
                continue;
            if (planet != null && !(ship.getPlanet().contains(planet)))
                continue;
            if (shipType != null && !(ship.getShipType().equals(shipType)))
                continue;
            if (afterDate != null && ship.getProdDate().before(afterDate))
                continue;
            if (beforeDate != null && ship.getProdDate().after(beforeDate))
                continue;
            if (isUsed != null && ship.getUsed().booleanValue() != isUsed.booleanValue())
                continue;
            if (minSpeed != null && ship.getSpeed().compareTo(minSpeed) < 0)
                continue;
            if (maxSpeed != null && ship.getSpeed().compareTo(maxSpeed) > 0)
                continue;
            if (minCrewSize != null && ship.getCrewSize().compareTo(minCrewSize) < 0)
                continue;
            if (maxCrewSize != null && ship.getCrewSize().compareTo(maxCrewSize) > 0)
                continue;
            if (minRating != null && ship.getRating().compareTo(minRating) < 0)
                continue;
            if (maxRating != null && ship.getRating().compareTo(maxRating) > 0)
                continue;

            shipsList.add(ship);
        }
        return shipsList;
    }

    @Override
    public List<Ship> sortShips(List<Ship> shipsList, ShipOrder order){
        if (order != null){
            switch (order){
                case ID:

            }

        }
    }

    @Override
    public Ship getShip(long id) {
        return shipRepository.findById(id).orElse(null);
    }


}
