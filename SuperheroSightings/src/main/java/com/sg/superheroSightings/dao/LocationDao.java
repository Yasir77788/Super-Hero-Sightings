package com.sg.superheroSightings.dao;

import com.sg.superheroSightings.dto.Location;

import java.util.List;

public interface LocationDao {

      List<Location> getAllLocations();

      Location getLocationById(int locationId);

      Location addLocation(Location location);

      void updateLocation(Location location);

      void deleteLocationById(int locationID);

}
