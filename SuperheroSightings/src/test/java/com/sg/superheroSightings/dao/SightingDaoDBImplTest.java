package com.sg.superheroSightings.dao;

import com.sg.superheroSightings.dto.Location;
import com.sg.superheroSightings.dto.Sighting;
import com.sg.superheroSightings.dto.Super;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class SightingDaoDBImplTest {

    @Autowired
    private SightingDao sightingDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private SuperDao superDao;

    public SightingDaoDBImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {

    }

    @AfterAll
    public static void tearDownClass() {
    }

    // clear out the test-DB before each test-method run
    @BeforeEach
    public void setUp() {

        List<Super> superList = superDao.getAllSupers();
        for(Super s : superList) {
            superDao.deleteSuperById(s.getSuperId());
        }

        List<Location> locationList = locationDao.getAllLocations();
        for(Location loc : locationList) {
            locationDao.deleteLocationById(loc.getLocationId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getSightingId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetSighting() throws ParseException {
        Location location = new Location();
        location.setLocationName("Music Corner");
        location.setLocationDescription("The location on the corner");
        location.setStreet("1234 First Street");
        location.setCity("Music city");
        location.setState("MC");
        location.setZipCode("89098");
        location.setLocationLat("123-ac-487");
        location.setLocationLong("342-da-873");
        locationDao.addLocation(location);

        Super superObj = new Super();
        superObj.setSuperName("Test Spider-man");
        superObj.setSuperDescription("A man that is a spider");
        superObj.setSuperPower("Can climb");
        superObj.setSuperStatus("Hero");
        superDao.addSuper(superObj);

        Sighting sighting = new Sighting();
        LocalDate date1 = LocalDate.of(2022, 8, 30);
        sighting.setSightingDate(date1);
        sighting.setSuperObj(superObj);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDAO = sightingDao.getSightingById(sighting.getSightingId());

        assertEquals(sighting, fromDAO);
    }


    @Test
    public void testGetAllSightings() {
        Location loc1 = new Location();
        loc1.setLocationName("XYZ");
        loc1.setLocationDescription("Good Place");
        loc1.setStreet("1234 st");
        loc1.setCity("Fair city");
        loc1.setState("ST");
        loc1.setZipCode("12345");
        loc1.setLocationLat("123--1f-23");
        loc1.setLocationLong("321-dx-211");
        locationDao.addLocation(loc1);

        Super super1 = new Super();
        super1.setSuperName("Test Spiderman");
        super1.setSuperDescription("A man that is a spider");
        super1.setSuperPower("Can climb high buildings");
        super1.setSuperStatus("Hero");
        superDao.addSuper(super1);

        Sighting sighting1 = new Sighting();
        LocalDate date1 = LocalDate.of(2022, 8, 30);
        sighting1.setSightingDate(date1);
        sighting1.setSuperObj(super1);
        sighting1.setLocation(loc1);
        sighting1 = sightingDao.addSighting(sighting1);

        Location loc2 = new Location();
        loc2.setLocationName("City Hospital2");
        loc2.setLocationDescription("The Hospital on the stree");
        loc2.setStreet("1234 Best Street");
        loc2.setCity("Good city");
        loc2.setState("SC");
        loc2.setZipCode("12345");
        loc2.setLocationLat("123-cx-123");
        loc2.setLocationLong("341-mc-291");
        locationDao.addLocation(loc2);

        Super super2 = new Super();
        super2.setSuperName("Test Spiderman2");
        super2.setSuperDescription("A man that is a spider");
        super2.setSuperPower("Can climb");
        super2.setSuperStatus("Villain");
        superDao.addSuper(super2);

        Sighting sighting2 = new Sighting();
        LocalDate date2 = LocalDate.of(2022, 8, 30);
        sighting2.setSightingDate(date2);
        sighting2.setSuperObj(super1);
        sighting2.setLocation(loc1);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightinglist = sightingDao.getAllSightings();

        assertEquals(2, sightinglist.size());
        assertTrue(sightinglist.contains(sighting1));
        assertTrue(sightinglist.contains(sighting2));
    }

    @Test
    public void testUpdateSighting() {
        Location loc1 = new Location();
        loc1.setLocationName("XYZ");
        loc1.setLocationDescription("Good Place");
        loc1.setStreet("1234 st");
        loc1.setCity("Fair city");
        loc1.setState("ST");
        loc1.setZipCode("12345");
        loc1.setLocationLat("123--1f-23");
        loc1.setLocationLong("321-dx-211");
        locationDao.addLocation(loc1);


        Super super1 = new Super();
        super1.setSuperName("Test Spiderman");
        super1.setSuperDescription("A man that is a spider");
        super1.setSuperPower("Can climb high buildings");
        super1.setSuperStatus("Hero");
        superDao.addSuper(super1);


        Super super2 = new Super();
        super2.setSuperName("Test Spiderman2");
        super2.setSuperDescription("A man that is a spider");
        super2.setSuperPower("Can climb");
        super2.setSuperStatus("Villain");
        superDao.addSuper(super2);


        Sighting sighting = new Sighting();
        LocalDate date1 = LocalDate.of(2022, 8, 30);
        sighting.setSightingDate(date1);
        sighting.setSuperObj(super1);
        sighting.setLocation(loc1);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDAO = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDAO);

        sighting.setSuperObj(super2);
        sightingDao.updateSighting(sighting);

        assertNotEquals(sighting, fromDAO);

        fromDAO = sightingDao.getSightingById(sighting.getSightingId());
        //assertEquals(sighting, fromDAO);
    }

    @Test
    public void testDeleteSightingById() {
        Location location = new Location();
        location.setLocationName("Music Corner");
        location.setLocationDescription("The location on the corner");
        location.setStreet("1234 First Street");
        location.setCity("Music city");
        location.setState("MC");
        location.setZipCode("89098");
        location.setLocationLat("123-ac-487");
        location.setLocationLong("342-da-873");
        locationDao.addLocation(location);


        Super super1 = new Super();
        super1.setSuperName("Test Spiderman");
        super1.setSuperDescription("A man that is a spider");
        super1.setSuperPower("Can climb high buildings");
        super1.setSuperStatus("Hero");
        superDao.addSuper(super1);

        Sighting sighting = new Sighting();
        LocalDate date1 = LocalDate.of(2022, 8, 30);
        sighting.setSightingDate(date1);
        sighting.setSuperObj(super1);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDAO = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDAO);

        sightingDao.deleteSightingById(sighting.getSightingId());

        fromDAO = sightingDao.getSightingById(sighting.getSightingId());
        assertNull(fromDAO);
    }

    @Test
    public void testGetRecentSightings() {
        Location loc1 = new Location();
        loc1.setLocationName("XYZ");
        loc1.setLocationDescription("Good Place");
        loc1.setStreet("1234 st");
        loc1.setCity("Fair city");
        loc1.setState("ST");
        loc1.setZipCode("12345");
        loc1.setLocationLat("123--1f-23");
        loc1.setLocationLong("321-dx-211");
        locationDao.addLocation(loc1);

        Super super1 = new Super();
        super1.setSuperName("Test Spiderman");
        super1.setSuperDescription("A man that is a spider");
        super1.setSuperPower("Can climb high buildings");
        super1.setSuperStatus("Hero");
        superDao.addSuper(super1);

        Sighting sighting1 = new Sighting();
        LocalDate date1 = LocalDate.of(2022, 8, 30);
        sighting1.setSightingDate(date1);
        sighting1.setSuperObj(super1);
        sighting1.setLocation(loc1);
        sighting1 = sightingDao.addSighting(sighting1);

        Location loc2 = new Location();
        loc2.setLocationName("City Hospital2");
        loc2.setLocationDescription("The Hospital on the stree");
        loc2.setStreet("1234 Best Street");
        loc2.setCity("Good city");
        loc2.setState("SC");
        loc2.setZipCode("12345");
        loc2.setLocationLat("123-cx-123");
        loc2.setLocationLong("341-mc-291");
        locationDao.addLocation(loc2);

        Super super2 = new Super();
        super2.setSuperName("Test Spiderman2");
        super2.setSuperDescription("A man that is a spider");
        super2.setSuperPower("Can climb");
        super2.setSuperStatus("Villain");
        superDao.addSuper(super2);

        Sighting sighting2 = new Sighting();
        LocalDate date2 = LocalDate.of(2022, 8, 30);
        sighting2.setSightingDate(date2);
        sighting2.setSuperObj(super1);
        sighting2.setLocation(loc1);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> sightinglist = sightingDao.getMostRecentSightings();

        assertEquals(2, sightinglist.size());
        assertTrue(sightinglist.contains(sighting1));
        assertTrue(sightinglist.contains(sighting2));
    }

    @Test
    public void testGetSightingsForSuper() throws ParseException {
        Location location = new Location();
        location.setLocationName("Music Corner");
        location.setLocationDescription("The location on the corner");
        location.setStreet("1234 First Street");
        location.setCity("Music city");
        location.setState("MC");
        location.setZipCode("89098");
        location.setLocationLat("123-ac-487");
        location.setLocationLong("342-da-873");
        locationDao.addLocation(location);

        Super superObj = new Super();
        superObj.setSuperName("Test Spider-man");
        superObj.setSuperDescription("A man that is a spider");
        superObj.setSuperPower("Can climb");
        superObj.setSuperStatus("Hero");
        superDao.addSuper(superObj);

        Sighting sighting = new Sighting();
        LocalDate date1 = LocalDate.of(2022, 8, 30);
        sighting.setSightingDate(date1);
        sighting.setSuperObj(superObj);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        LocalDate date2 = LocalDate.of(2022, 8, 30);
        sighting2.setSightingDate(date2);
        sighting2.setSuperObj(superObj);
        sighting2.setLocation(location);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> listOfSighting = sightingDao.getSightingForSuper(superObj);

        assertEquals(2, listOfSighting.size());
        assertTrue(listOfSighting.contains(sighting2));

    }


    @Test
    public void testGetSightingsForLocation() throws ParseException {
        Location location = new Location();
        location.setLocationName("Music Corner");
        location.setLocationDescription("The location on the corner");
        location.setStreet("1234 First Street");
        location.setCity("Music city");
        location.setState("MC");
        location.setZipCode("89098");
        location.setLocationLat("123-ac-487");
        location.setLocationLong("342-da-873");
        locationDao.addLocation(location);

        Super superObj = new Super();
        superObj.setSuperName("Test Spider-man");
        superObj.setSuperDescription("A man that is a spider");
        superObj.setSuperPower("Can climb");
        superObj.setSuperStatus("Hero");
        superDao.addSuper(superObj);

        Sighting sighting = new Sighting();
        LocalDate date1 = LocalDate.of(2022, 8, 30);
        sighting.setSightingDate(date1);
        sighting.setSuperObj(superObj);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        LocalDate date2 = LocalDate.of(2022, 8, 30);
        sighting2.setSightingDate(date2);
        sighting2.setSuperObj(superObj);
        sighting2.setLocation(location);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> listOfSighting = sightingDao.getSightingsForLocation(location);

        assertEquals(2, listOfSighting.size());
        assertTrue(listOfSighting.contains(sighting2));

    }


}
