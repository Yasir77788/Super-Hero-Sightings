package com.sg.superheroSightings.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class Sighting {
    private int sightingId;

    @NotNull(message = "Sighting date must not be empty.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sightingDate;


    //@NotNull(message = "Sighting-location must not be null")
    private Location location;


    //@NotNull(message = "Sighting-super must not be null.")
    private Super superObj;

    public Sighting() {
    }

    public Sighting(LocalDate sightingDate, Location location, Super superObj) {
        this.sightingDate = sightingDate;
        this.location = location;
        this.superObj = superObj;
    }

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public LocalDate getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(LocalDate sightingDate) {
        this.sightingDate = sightingDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Super getSuperObj() {
        return superObj;
    }

    public void setSuperObj(Super superObj) {
        this.superObj = superObj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return sightingId == sighting.sightingId && Objects.equals(sightingDate, sighting.sightingDate) && Objects.equals(location, sighting.location) && Objects.equals(superObj, sighting.superObj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sightingId, sightingDate, location, superObj);
    }

    @Override
    public String toString() {
        return "Sighting{" +
                "sightingId=" + sightingId +
                ", sightingDate=" + sightingDate +
                ", location=" + location +
                ", superObj=" + superObj +
                '}';
    }
}
