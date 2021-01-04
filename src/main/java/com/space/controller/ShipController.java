package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/")

public class ShipController {
    private ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping(value = "ships/{id}")//Get ship from bd
    public ResponseEntity<Ship> getShip(@PathVariable(name = "id") String uriId) {
        final Long id = convertStringToLong(uriId);
        if (id == null || id <= 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        final Ship ship = shipService.getShip(id);
        return ship != null
                ? new ResponseEntity<>(ship, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }





    private Long convertStringToLong (String uriId){//Converting ID to Long
        if (uriId == null){
            return null;
        }
        else
            try {
                return Long.parseLong(uriId);
            }
            catch (Exception exp){
                return null;
            }
    }

}
