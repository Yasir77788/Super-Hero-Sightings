package com.sg.superheroSightings.dao;

import com.sg.superheroSightings.dto.Location;
import com.sg.superheroSightings.dto.Sighting;
import com.sg.superheroSightings.dto.Super;

import java.time.LocalDate;
import java.util.List;

public interface SightingDao {

      Sighting getSightingById(int sightingId);

      List<Sighting> getAllSightings();

      Sighting addSighting(Sighting sighting);

      void updateSighting(Sighting sighting);

      void deleteSightingById(int sightingId);

      List<Sighting> getMostRecentSightings();

      List<Sighting> getSightingForSuper(Super sp);

      List<Sighting> getSightingsForLocation(Location location);



     List<Sighting> getSightingForDate(LocalDate date);

//    List<Sighting> getSightingsForLocationAndDate(Location location, Date date);


}
