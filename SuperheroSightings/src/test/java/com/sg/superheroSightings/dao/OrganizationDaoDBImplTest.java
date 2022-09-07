package com.sg.superheroSightings.dao;

import com.sg.superheroSightings.dto.Location;
import com.sg.superheroSightings.dto.Organization;
import com.sg.superheroSightings.dto.Super;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class OrganizationDaoDBImplTest {
    @Autowired
    private SightingDao sightingDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private SuperDao superDao;

    @Autowired
    private OrganizationDao orgDao;

    public OrganizationDaoDBImplTest() {
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


        List<Organization> orgList = orgDao.getAllOrganizations();
        for (Organization org : orgList) {
            orgDao.deleteOrganizationById(org.getOrgId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetOrg() {
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


        Super testSuper = new Super();
        testSuper.setSuperName("Superhuman");
        testSuper.setSuperDescription("A person with good super skills");
        testSuper.setSuperPower("x-ray vision");
        testSuper.setSuperStatus("Hero");
        testSuper = superDao.addSuper(testSuper);

        List<Super> superList = new ArrayList<>();
        superList.add(testSuper);

        Organization org1 = new Organization();
        org1.setOrgName("Test ABC Org");
        org1.setOrgDescription("Non Profit");
        org1.setOrgPhone("456-123-8844");
        org1.setOrgEmail("org1@email.com");
        org1.setHeroOrVillainOrg("Hero");
        org1.setLocation(location);
        org1.setMembers(superList);
        org1 = orgDao.addOrganization(org1);

        Organization fromDAO = orgDao.getOrganizationById(org1.getOrgId());

        assertEquals(org1, fromDAO);
    }

    @Test
    public void testGetAllOrgs() {
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


        Super testSuper = new Super();
        testSuper.setSuperName("Superhuman");
        testSuper.setSuperDescription("A person with good super skills");
        testSuper.setSuperPower("x-ray vision");
        testSuper.setSuperStatus("Hero");
        testSuper = superDao.addSuper(testSuper);

        List<Super> superList = new ArrayList<>();
        superList.add(testSuper);

        Organization org1 = new Organization();
        org1.setOrgName("Test ABC Org");
        org1.setOrgDescription("Non Profit");
        org1.setOrgPhone("456-123-8844");
        org1.setOrgEmail("org1@email.com");
        org1.setHeroOrVillainOrg("Hero");
        org1.setLocation(location);
        org1.setMembers(superList);
        org1 = orgDao.addOrganization(org1);


        List<Organization> orgList = orgDao.getAllOrganizations();

        assertEquals(1, orgList.size());
        assertTrue(orgList.contains(org1));

    }

    @Test
    public void testUpdateOrg() {
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


        Super testSuper = new Super();
        testSuper.setSuperName("Superhuman");
        testSuper.setSuperDescription("A person with good super skills");
        testSuper.setSuperPower("x-ray vision");
        testSuper.setSuperStatus("Hero");
        testSuper = superDao.addSuper(testSuper);

        List<Super> superList = new ArrayList<>();
        superList.add(testSuper);

        Organization org1 = new Organization();
        org1.setOrgName("Test ABC Org");
        org1.setOrgDescription("Non Profit");
        org1.setOrgPhone("456-123-8844");
        org1.setOrgEmail("org1@email.com");
        org1.setHeroOrVillainOrg("Hero");
        org1.setLocation(location);
        org1.setMembers(superList);
        org1 = orgDao.addOrganization(org1);

        Organization fromDAO = orgDao.getOrganizationById(org1.getOrgId());
        assertEquals(org1, fromDAO);

        org1.setOrgName("New Org 2.0");


        orgDao.updateOrganization(org1);

        assertNotEquals(org1, fromDAO);

        fromDAO = orgDao.getOrganizationById(org1.getOrgId());
        assertEquals(org1, fromDAO);
    }

    @Test
    public void testDeleteOrgById() {

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


        Super testSuper = new Super();
        testSuper.setSuperName("Superhuman");
        testSuper.setSuperDescription("A person with good super skills");
        testSuper.setSuperPower("x-ray vision");
        testSuper.setSuperStatus("Hero");
        testSuper = superDao.addSuper(testSuper);

        List<Super> superList = new ArrayList<>();
        superList.add(testSuper);

        Organization org1 = new Organization();
        org1.setOrgName("Test ABC Org");
        org1.setOrgDescription("Non Profit");
        org1.setOrgPhone("456-123-8844");
        org1.setOrgEmail("org1@email.com");
        org1.setHeroOrVillainOrg("Hero");
        org1.setLocation(location);
        org1.setMembers(superList);
        org1 = orgDao.addOrganization(org1);

        Organization fromDAO = orgDao.getOrganizationById(org1.getOrgId());
        assertEquals(org1, fromDAO);
        // ACT
        orgDao.deleteOrganizationById(org1.getOrgId());
        fromDAO = orgDao.getOrganizationById(org1.getOrgId());
        assertNull(fromDAO);

    }

    @Test
    public void testGetsOrgsForSuper() {

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


        Super testSuper = new Super();
        testSuper.setSuperName("Superhuman");
        testSuper.setSuperDescription("A person with good super skills");
        testSuper.setSuperPower("x-ray vision");
        testSuper.setSuperStatus("Hero");
        testSuper = superDao.addSuper(testSuper);

        Super testSuper2 = new Super();
        testSuper2.setSuperName("Superhuman 2.0");
        testSuper2.setSuperDescription("A person2 with good super skills");
        testSuper2.setSuperPower("x-ray vision2");
        testSuper2.setSuperStatus("Hero");
        testSuper2 = superDao.addSuper(testSuper2);

        List<Super> superList = new ArrayList<>();
        superList.add(testSuper);
        superList.add(testSuper2);

        List<Super> superList2 = new ArrayList<>();
        superList2.add(testSuper2);

        Organization org = new Organization();
        org.setOrgName("Test ABC Org");
        org.setOrgDescription("Non Profit");
        org.setOrgPhone("456-123-8844");
        org.setOrgEmail("org1@email.com");
        org.setHeroOrVillainOrg("Hero");
        org.setLocation(location);
        org.setMembers(superList);
        org = orgDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setOrgName("Test ABC Org");
        org2.setOrgDescription("Non Profit");
        org2.setOrgPhone("456-123-8844");
        org2.setOrgEmail("org1@email.com");
        org2.setHeroOrVillainOrg("Hero");
        org2.setLocation(location);
        org2.setMembers(superList2);
        org2 = orgDao.addOrganization(org2);


        Organization org3 = new Organization();
        org3.setOrgName("Test ABC Org");
        org3.setOrgDescription("Non Profit");
        org3.setOrgPhone("456-123-8844");
        org3.setOrgEmail("org1@email.com");
        org3.setHeroOrVillainOrg("Hero");
        org3.setLocation(location);
        org3.setMembers(superList);
        org3 = orgDao.addOrganization(org3);

        List<Organization> orgs = orgDao.getOrgsForSuper(testSuper);
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(org));
        assertFalse(orgs.contains(org2));
        assertTrue(orgs.contains(org3));

    }

    @Test
    public void testGetOrgsForSuper() {
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


        Super testSuper = new Super();
        testSuper.setSuperName("Superhuman");
        testSuper.setSuperDescription("A person with good super skills");
        testSuper.setSuperPower("x-ray vision");
        testSuper.setSuperStatus("Hero");
        testSuper = superDao.addSuper(testSuper);

        List<Super> superList = new ArrayList<>();
        superList.add(testSuper);

        Organization org1 = new Organization();
        org1.setOrgName("Test ABC Org");
        org1.setOrgDescription("Non Profit");
        org1.setOrgPhone("456-123-8844");
        org1.setOrgEmail("org1@email.com");
        org1.setHeroOrVillainOrg("Hero");
        org1.setLocation(location);
        org1.setMembers(superList);
        org1 = orgDao.addOrganization(org1);


        Organization org2 = new Organization();
        org2.setOrgName("Test ABC Org");
        org2.setOrgDescription("Non Profit");
        org2.setOrgPhone("456-123-8844");
        org2.setOrgEmail("org1@email.com");
        org2.setHeroOrVillainOrg("Hero");
        org2.setLocation(location);
        org2.setMembers(superList);
        org2 = orgDao.addOrganization(org2);



        List<Organization> orgList = orgDao.getOrgsForSuper(testSuper);

        assertEquals(2, orgList.size());
        assertTrue(orgList.contains(org2));

    }

}
