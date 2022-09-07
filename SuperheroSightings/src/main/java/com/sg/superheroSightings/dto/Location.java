package com.sg.superheroSightings.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Location {

    private int locationId;

    @NotBlank(message = "Location name must not be empty.")
    @Size(max = 50, message="Location name must be less than 50 characters.")
    private String locationName;

    @NotBlank(message = "Location description must not be empty.")
    @Size(max = 200, message="Location description must be less than 200 characters.")
    private String locationDescription;

    @NotBlank(message = "Location street name must not be empty.")
    @Size(max = 50, message="Location street must be less than 50 characters.")
    private String street;

    @NotBlank(message = "Location city must not be empty.")
    @Size(max = 50, message="Location city must be less than 50 characters.")
    private String city;

    @NotBlank(message = "Location city must not be empty.")
    @Size(max = 50, message="Location city name must be less than 50 characters.")
    private String state;

    @NotBlank(message = "Location zipcode must not be empty.")
    @Size(max = 10, message="Location zipcode must be less than 10 characters.")
    private String zipCode;

    @NotBlank(message = "locationLong must not be empty.")
    @Size(max = 50, message="locationLong  must be less than 50 characters.")
    private String locationLong;

    @NotBlank(message = "locationLat must not be empty.")
    @Size(max = 50, message="locationLat  must be less than 50 characters.")
    private String locationLat;

    public Location() {
    }

    public Location(String locationName, String locationDescription, String street, String city,
                    String state, String zipCode, String locationLong, String locationLat) {
        this.locationName = locationName;
        this.locationDescription = locationDescription;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.locationLong = locationLong;
        this.locationLat = locationLat;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(String locationLong) {
        this.locationLong = locationLong;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationId == location.locationId && Objects.equals(locationName, location.locationName) && Objects.equals(locationDescription, location.locationDescription) && Objects.equals(street, location.street) && Objects.equals(city, location.city) && Objects.equals(state, location.state) && Objects.equals(zipCode, location.zipCode) && Objects.equals(locationLong, location.locationLong) && Objects.equals(locationLat, location.locationLat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, locationName, locationDescription, street, city, state, zipCode, locationLong, locationLat);
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                ", locationDescription='" + locationDescription + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", locationLong='" + locationLong + '\'' +
                ", locationLat='" + locationLat + '\'' +
                '}';
    }
}
