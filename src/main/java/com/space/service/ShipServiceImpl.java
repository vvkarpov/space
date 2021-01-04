package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class ShipServiceImpl implements ShipService{

    private ShipRepository shipRepository;

    public ShipServiceImpl() {
    }

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository) {
        super();
        this.shipRepository = shipRepository;
    }

    @Override
    public Ship getShip(long id) {
        return shipRepository.findById(id).orElse(null);
    }

}
