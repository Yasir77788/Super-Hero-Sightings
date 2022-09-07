package com.sg.superheroSightings.dao;

import com.sg.superheroSightings.dto.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationDaoDBImpl implements LocationDao{
    @Autowired
    JdbcTemplate jdbc;


    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM Location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public Location getLocationById(int locationId) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM Location WHERE locationId = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), locationId);

        } catch (DataAccessException ex) {
            return null;
        }
    }


    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO Location(locationName, locationDescription, "
                + "street, city, state, zipCode,locationLat, locationLong) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getStreet(),
                location.getCity(),
                location.getState(),
                location.getZipCode(),
                location.getLocationLat(),
                location.getLocationLong());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);
        return location;
    }


    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE Location SET locationName=?, locationDescription=?, "
                + "street=?, city=?, state=?, zipcode=?, locationLat=?, "
                + "locationLong=? WHERE locationId=?";
        jdbc.update(UPDATE_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getStreet(),
                location.getCity(),
                location.getState(),
                location.getZipCode(),
                location.getLocationLat(),
                location.getLocationLong(),
                location.getLocationId());
    }

    @Override
    public void deleteLocationById(int locationId) {
        final String DELETE_LOC_FROM_ORG = "DELETE FROM Organization WHERE locationId = ?";
        jdbc.update(DELETE_LOC_FROM_ORG, locationId);

        final String DELETE_LOC_FROM_SIGHTING = "DELETE FROM Sighting WHERE locationId = ?";
        jdbc.update(DELETE_LOC_FROM_SIGHTING, locationId);

        final String DELETE_LOCATION = "DELETE FROM Location WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION, locationId);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("locationId"));
            location.setLocationName(rs.getString("locationName"));
            location.setLocationDescription(rs.getString("locationDescription"));
            location.setStreet(rs.getString("street"));
            location.setCity(rs.getString("city"));
            location.setState(rs.getString("state"));
            location.setZipCode(rs.getString("zipCode"));
            location.setLocationLat(rs.getString("locationLat"));
            location.setLocationLong(rs.getString("locationLong"));

            return location;
        }
    }
}
