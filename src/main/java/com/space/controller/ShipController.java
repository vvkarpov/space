package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/")

public class ShipController {

    private ShipService shipService;

    public ShipController() {
    }

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping(value = "ships/{id}")//Get ship from bd
    public ResponseEntity<Ship> getShip(@PathVariable(name = "id") String uriID) {
        Long id = convertStringToLong(uriID);
        if (id == null || id <= 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Ship ship = shipService.getShip(id);
        return ship != null
                ? new ResponseEntity<>(ship, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Long convertStringToLong (String uriID){//Converting ID to Long
        if (uriID == null){
            return null;
        }
        else
            try {
                return Long.parseLong(uriID);
            }
            catch (Exception exp){
                return null;
            }
    }

}
