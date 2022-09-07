package com.sg.superheroSightings.dao;

import com.sg.superheroSightings.dto.Location;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class LocationDaoDBImplTest {

    @Autowired
    private LocationDao locationDao;

    public LocationDaoDBImplTest() {
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


        List<Location> locationList = locationDao.getAllLocations();
        for(Location loc : locationList) {
            locationDao.deleteLocationById(loc.getLocationId());
        }


    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetLocation() {
        // ARRANGE
        Location loc = new Location();
        loc.setLocationName("Farmers' Market");
        loc.setLocationDescription("Large store for everything");
        loc.setStreet("123 Fine Street");
        loc.setCity("Ted City");
        loc.setState("OV");
        loc.setZipCode("12399");
        loc.setLocationLat("033-sl-934");
        loc.setLocationLong("123-kj-234");

        // ACT
        // add location
        locationDao.addLocation(loc);
       // get location by id
        Location locFromDao = locationDao.getLocationById(loc.getLocationId());

        // ASSERT
        assertEquals(loc, locFromDao);
    }

    @Test
    public void testGetAllLocations() {
        Location location1 = new Location();
        location1.setLocationName("Marv Store");
        location1.setLocationDescription("The store is on the side fo the road");
        location1.setStreet("567 Well Street");
        location1.setCity("Big city");
        location1.setState("AK");
        location1.setZipCode("00433");
        location1.setLocationLat("033-sl-934");
        location1.setLocationLong("123-kj-234");
        locationDao.addLocation(location1);

        Location location2 = new Location();
        location2.setLocationName("KC High school");
        location2.setLocationDescription("The school is a new building");
        location2.setStreet("564 edu Street");
        location2.setCity("Education City");
        location2.setState("HS");
        location2.setZipCode("22055");
        location2.setLocationLat("549-mn-321");
        location2.setLocationLong("234-tm-004");
        locationDao.addLocation(location2);

        List<Location> locationList = locationDao.getAllLocations();

        assertEquals(2, locationList.size());
        assertTrue(locationList.contains(location1));
        assertTrue(locationList.contains(location2));
    }

    @Test
    public void testUpdateLocation() {
        Location loc = new Location();
        loc.setLocationName("Marv Store");
        loc.setLocationDescription("The store is on the side fo the road");
        loc.setStreet("567 Well Street");
        loc.setCity("Big city");
        loc.setState("AK");
        loc.setZipCode("00433");
        loc.setLocationLat("033-sl-934");
        loc.setLocationLong("123-kj-234");
        locationDao.addLocation(loc);

        Location fromDAO = locationDao.getLocationById(loc.getLocationId());
        assertEquals(loc, fromDAO);

        loc.setLocationName("Updated New Marv Store");
        locationDao.updateLocation(loc);

        assertNotEquals(loc, fromDAO);

        fromDAO = locationDao.getLocationById(loc.getLocationId());
        assertEquals(loc, fromDAO);
    }


    @Test
    public void testDeleteLocationById() {

        Location loc = new Location();
        loc.setLocationName("C Mart");
        loc.setLocationDescription("Large store for everything");
        loc.setStreet("123 Fine Street");
        loc.setCity("Ted City");
        loc.setState("OV");
        loc.setZipCode("12399");
        loc.setLocationLat("033-sl-934");
        loc.setLocationLong("123-kj-234");
        locationDao.addLocation(loc);

        Location locFromDao = locationDao.getLocationById(loc.getLocationId());
        assertEquals(loc, locFromDao);

        locationDao.deleteLocationById(loc.getLocationId());

        locFromDao = locationDao.getLocationById(loc.getLocationId());
        assertNull(locFromDao);
    }

}
