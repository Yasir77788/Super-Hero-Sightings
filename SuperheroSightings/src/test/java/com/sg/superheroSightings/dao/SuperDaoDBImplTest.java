package com.sg.superheroSightings.dao;

import com.sg.superheroSightings.dto.Super;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class SuperDaoDBImplTest {

    @Autowired
    private SuperDao superDao;


    public SuperDaoDBImplTest() {
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
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllSupers() {
        // ARRANGE
        Super testSuper = new Super();
        testSuper.setSuperName("Superhuman");
        testSuper.setSuperDescription("A person with good super skills");
        testSuper.setSuperPower("x-ray vision");
        testSuper.setSuperStatus("Hero");
        testSuper = superDao.addSuper(testSuper);

        Super testVillain = new Super();
        testVillain.setSuperName("The Joker");
        testVillain.setSuperDescription("A person with evil intentions");
        testVillain.setSuperPower("Lethal concoctions");
        testVillain.setSuperStatus("Villain");
        testVillain = superDao.addSuper(testVillain);

        // ACT - call the code under test
        List<Super> superList = superDao.getAllSupers();

        // ASSERT - verify the test result
        assertEquals(2, superList.size());
        assertTrue(superList.contains(testSuper));
        assertTrue(superList.contains(testVillain));
    }

    @Test
    public void testAddAndGetSuper() {
        Super testSuper = new Super();
        testSuper.setSuperName("Batman");
        testSuper.setSuperDescription("A man that can climb high buildings");
        testSuper.setSuperPower("super intellect");
        testSuper.setSuperStatus("Hero");
        testSuper = superDao.addSuper(testSuper);

        Super heroFromDao = superDao.getSuperById(testSuper.getSuperId());

        assertEquals(testSuper, heroFromDao);
    }

    @Test
    public void testUpdateSuper(){
        Super testSuper = new Super();
        testSuper.setSuperName("Superman 1.0");
        testSuper.setSuperDescription("A man with super skills");
        testSuper.setSuperPower("super-speed");
        testSuper.setSuperStatus("Hero");
        testSuper = superDao.addSuper(testSuper);

        Super superFromDao = superDao.getSuperById(testSuper.getSuperId());
        assertEquals(testSuper, superFromDao);

        testSuper.setSuperName("Superman 2.0: updated");
        testSuper.setSuperDescription("A man with super skills updated");
        testSuper.setSuperPower("super-speed: updated");
        testSuper.setSuperStatus("Hero");
        superDao.updateSuper(testSuper);

        assertNotEquals(testSuper, superFromDao);

        // get the updated super from Dao
        superFromDao = superDao.getSuperById(testSuper.getSuperId());
        // compare the returned updated-superFromDao with the updated testsuper
        assertEquals(testSuper, superFromDao);
    }

    @Test
    public void testDeleteSuperById() {
        Super superObj = new Super();
        superObj.setSuperName("Test Superman");
        superObj.setSuperDescription("A man who helps the vulnerable");
        superObj.setSuperPower("superhuman strength");
        superObj.setSuperStatus("Hero");
        superObj = superDao.addSuper(superObj);

        Super superFromDao = superDao.getSuperById(superObj.getSuperId());
        assertEquals(superObj, superFromDao);

        superDao.deleteSuperById(superObj.getSuperId());

        superFromDao = superDao.getSuperById(superObj.getSuperId());
        assertNull(superFromDao);
    }

}
