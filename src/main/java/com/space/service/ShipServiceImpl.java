package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipServiceImpl implements ShipService{
    @Autowired
    ShipRepository shipRepository;

    @Override
    public Ship getShip(long id) {
        return shipRepository.getOne(id);
    }

}
