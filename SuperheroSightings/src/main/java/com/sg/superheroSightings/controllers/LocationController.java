package com.sg.superheroSightings.controllers;

import com.sg.superheroSightings.dao.LocationDao;
import com.sg.superheroSightings.dto.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class LocationController {
    @Autowired
    private LocationDao locationDao;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("errors", violations);
        model.addAttribute("locations", locations);

        return "locations";
    }


    @PostMapping("addLocation")
    public String addSuper(HttpServletRequest request) {
        String locationName = request.getParameter("locationName");
        String locationDescription = request.getParameter("locationDescription");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipCode = request.getParameter("zipCode");
        String locationLat = request.getParameter("locationLat");
        String locationLong = request.getParameter("locationLong");

        Location loc = new Location();
        loc.setLocationName(locationName);
        loc.setLocationDescription(locationDescription);
        loc.setStreet(street);
        loc.setCity(city);
        loc.setState(state);
        loc.setZipCode(zipCode);
        loc.setLocationLat(locationLat);
        loc.setLocationLong(locationLong);


        //locationDao.addLocation(loc);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(loc);

        if(violations.isEmpty()) {
            locationDao.addLocation(loc);
        }

        return "redirect:/locations";
    }


    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDao.deleteLocationById(id);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location loc = locationDao.getLocationById(id);

        model.addAttribute("location", loc);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location loc, HttpServletRequest request, BindingResult result) {

        if(result.hasErrors()) {
            return "editSuper";
        }

        int id = Integer.parseInt(request.getParameter("id"));
        loc = locationDao.getLocationById(id);

        loc.setLocationName(request.getParameter("locationName"));
        loc.setLocationDescription(request.getParameter("locationDescription"));
        loc.setStreet(request.getParameter("street"));
        loc.setCity(request.getParameter("city"));
        loc.setState(request.getParameter("state"));
        loc.setZipCode(request.getParameter("zipCode"));
        loc.setLocationLat(request.getParameter("locationLat"));
        loc.setLocationLong(request.getParameter("locationLong"));

        locationDao.updateLocation(loc);

        return "redirect:/locations";
    }





}
