package com.sg.superheroSightings.controllers;

import com.sg.superheroSightings.dao.LocationDao;
import com.sg.superheroSightings.dao.OrganizationDao;
import com.sg.superheroSightings.dao.SightingDao;
import com.sg.superheroSightings.dao.SuperDao;
import com.sg.superheroSightings.dto.Location;
import com.sg.superheroSightings.dto.Sighting;
import com.sg.superheroSightings.dto.Super;
import com.sg.superheroSightings.service.SuperService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SightingController {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private SuperDao superDao;

    @Autowired
    private OrganizationDao orgDao;

    @Autowired
    private SightingDao sightingDao;

    @Autowired
    private SuperService superService;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
    private boolean displayImg = false;


    @GetMapping("home")
    public String displayLatestSightings(Model model) {
        List<Sighting> sightings = sightingDao.getMostRecentSightings();
        model.addAttribute("sightings", sightings);
        return "home";
    }

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Location>  locations = locationDao.getAllLocations();

        List<Super> supers = superDao.getAllSupers();

        model.addAttribute("errors", violations);
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("supers", supers);

        return "sightings";
    }


    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {

        Sighting sighting = new Sighting();

        String sightingDateString = request.getParameter("sightingDate");
        String superId  = request.getParameter("superId");
        String locationId = request.getParameter("locationId");


        LocalDate sightingDate = LocalDate.parse(sightingDateString);

        sighting.setSightingDate(sightingDate);
        sighting.setSuperObj(superDao.getSuperById(Integer.parseInt(superId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if(violations.isEmpty()) {
            sightingDao.addSighting(sighting);
        }

        return "redirect:/sightings";

    }


    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);

        displayImg = superService.isImageSet(sighting.getSuperObj().getSuperId()+"");
        model.addAttribute("displayImg",displayImg);

        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {

        int id = Integer.parseInt(request.getParameter("id"));

        Sighting sighting = sightingDao.getSightingById(id);

        List<Super> supers = superDao.getAllSupers();
        List<Location> locations = locationDao.getAllLocations();

        model.addAttribute("sighting", sighting);
        model.addAttribute("supers", supers);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting sighting, HttpServletRequest request,
                                      BindingResult result, Model model) {

        if(result.hasErrors()) {
            return "editSighting";
        }

        int sightingId = Integer.parseInt(request.getParameter("id"));
        sighting = sightingDao.getSightingById(sightingId);


        String sightingDateString = request.getParameter("sightingDate");
        String locationId = request.getParameter("locationId");
        String superId = request.getParameter("superId");

        LocalDate sightingDate = LocalDate.parse(sightingDateString);
        sighting.setSightingDate(sightingDate);
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sighting.setSuperObj(superDao.getSuperById(Integer.parseInt(superId)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        if (violations.isEmpty()) {
            sightingDao.updateSighting(sighting);
        } else {

            sighting = sightingDao.getSightingById(sighting.getSightingId());
            model.addAttribute("errors", violations);
            model.addAttribute("sighting", sighting);
        }

        return "redirect:/sightings";
    }

    @GetMapping("getSuperId")
    public String getSightingForSuper(Model model) {
        return "getSuperId";
    }

    @PostMapping("getSightingsForSuper")
    public String getSightingForSuper(HttpServletRequest request, Model model) {

        int superId = Integer.parseInt(request.getParameter("superId"));
        List<Sighting> sightingList = new ArrayList<>();
        Super superObj = superDao.getSuperById(superId);

        sightingList = sightingDao.getSightingForSuper(superObj);

        model.addAttribute("errors", violations);
        model.addAttribute("sightings", sightingList);

        return "sightingsForAsuper";
    }

    @GetMapping("getLocationId")
    public String getSightingForLocation(Model model) {
        return "getLocationId";
    }

    @PostMapping("sightingsForLocation")
    public String getSightingForLocation(HttpServletRequest request, Model model) {

        int locationId = Integer.parseInt(request.getParameter("locationId"));
        List<Sighting> sightingList = new ArrayList<>();
        Location loc  = locationDao.getLocationById(locationId);

        sightingList = sightingDao.getSightingsForLocation(loc);

        model.addAttribute("errors", violations);
        model.addAttribute("sightings", sightingList);

        return "sightingsForLocation";
    }

    @GetMapping("getSightingDate")
    public String getSightingForDate(Model model) {
        return "getSightingDate";
    }

    @PostMapping("sightingsForDate")
    public String getSightingForDate(HttpServletRequest request, Model model) {


        String sightingDateString = request.getParameter("sightingDate");


        LocalDate sightingDate = LocalDate.parse(sightingDateString);

        List<Sighting> sightingList = new ArrayList<>();
        sightingList  = sightingDao.getSightingForDate(sightingDate);



        model.addAttribute("errors", violations);
        model.addAttribute("sightings", sightingList);

        return "sightingsForDate";
    }



}
