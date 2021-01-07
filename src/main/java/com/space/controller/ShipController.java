package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
@RequestMapping("/rest/")

public class ShipController {

    @Autowired
    private ShipService shipService;

    @GetMapping("ships")//Get all ships with filters
    public List<Ship> getShips(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "planet", required = false) String planet,
            @RequestParam(value = "shipType", required = false) ShipType shipType,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "maxRating", required = false) Double maxRating,
            @RequestParam(value = "order", required = false) ShipOrder order,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ){
        List<Ship> ships = shipService.getShips(name, planet, shipType, after, before, isUsed, minSpeed,
                maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);
        List<Ship> sortShips = shipService.sortShips(ships, order);

        return shipService.getPage(sortShips, pageNumber, pageSize);
    }

    @RequestMapping(path = "ships/count", method = RequestMethod.GET)//Get ships count
    public Integer getShipsCount(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "planet", required = false) String planet,
            @RequestParam(value = "shipType", required = false) ShipType shipType,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "maxRating", required = false) Double maxRating
    ) {
        return shipService.getShips(name, planet, shipType, after, before, isUsed, minSpeed,
                maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating).size();
    }

    @PostMapping("ships")//Create ship
    @ResponseBody
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship){

        Calendar startCalendar = new GregorianCalendar(2800, Calendar.JANUARY, 1);
        Date startProd = startCalendar.getTime();
        Calendar endCalendar = new GregorianCalendar(3019, Calendar.DECEMBER, 31);
        Date endProd = endCalendar.getTime();

        if (ship == null || ship.getName() == null || ship.getPlanet() == null ||
            ship.getShipType() == null || ship.getProdDate() == null || ship.getSpeed() == null ||
            ship.getCrewSize() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if (ship.getName().length() > 50 || ship.getPlanet().length() > 50){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if (ship.getName().isEmpty() || ship.getPlanet().isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if (Math.round(ship.getSpeed() * 100) / 100D < 0.01 ||
                Math.round(ship.getSpeed() * 100) / 100D > 0.99){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if (ship.getCrewSize() < 1 || ship.getCrewSize() > 9999){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if (ship.getProdDate().getTime() < 0 || ship.getProdDate().before(startProd) ||
                ship.getProdDate().after(endProd)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            if (ship.getUsed() == null)
                ship.setUsed(false);
            ship.setRating(ratingShip(ship.getSpeed(), ship.getUsed(), ship.getProdDate()));
            return new ResponseEntity<>(shipService.createShip(ship), HttpStatus.OK);
        }

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

    @PostMapping(value = "ships/{id}")//Update ship
    @ResponseBody
    public ResponseEntity<Ship> updateShip(@PathVariable(name = "id") String uriID,
                                           @RequestBody Ship ship){

        Long id = convertStringToLong(uriID);
        if (id == null || id <= 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Ship updateShip = shipService.updateShip(id, ship);
        return updateShip != null
                ? new ResponseEntity<>(updateShip, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Long convertStringToLong(String uriID){//Converting ID to Long
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

    private Double ratingShip(Double speed, Boolean isUsed, Date prodDate){
        Double k = isUsed ? 1 : 0.5;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(prodDate);
        int year = calendar.get(Calendar.YEAR);
        double rating = 80 * speed *  k / (3019 - year + 1);
        return Math.round(rating * 100) / 100D;
    }

}
