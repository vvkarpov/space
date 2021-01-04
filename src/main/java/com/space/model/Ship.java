package com.space.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ship")

public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;//ID ship

    private String name; //Ship name
    private String planet;//Arrival planet
    @Enumerated(EnumType.STRING)
    private ShipType shipType;//Ship type
    @Temporal(TemporalType.DATE)
    private Date prodDate;//Release date
    private Boolean isUsed;//Used or New
    private Double speed;//Ship speed
    private Integer crewSize;//Crew size
    private Double rating;//Ship rating

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}