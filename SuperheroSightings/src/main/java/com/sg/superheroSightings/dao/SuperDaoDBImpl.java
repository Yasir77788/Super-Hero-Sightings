package com.sg.superheroSightings.dao;

import com.sg.superheroSightings.dto.Super;
import com.sg.superheroSightings.dto.Location;
import com.sg.superheroSightings.dto.Organization;
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
public class SuperDaoDBImpl implements SuperDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Super> getAllSupers() {
        final String GET_ALL_SUPERS = "SELECT * FROM Super";
        return jdbc.query(GET_ALL_SUPERS, new SuperMapper());
    }

    @Override
    public Super getSuperById(int superId) {
        try {
            final String GET_SUPER_BY_ID = "SELECT * FROM Super WHERE superId = ?";
            return jdbc.queryForObject(GET_SUPER_BY_ID, new SuperMapper(), superId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Super addSuper(Super superObj) {
        final String INSERT_SUPER = "INSERT INTO Super(superName, superDescription, "
                + "superPower,superStatus) VALUES(?,?,?,?)";
        jdbc.update(INSERT_SUPER,
                superObj.getSuperName(),
                superObj.getSuperDescription(),
                superObj.getSuperPower(),
                superObj.getSuperStatus() );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superObj.setSuperId(newId);
        return superObj;
    }

    @Override
    public void updateSuper(Super superObj) {
        final String UPDATE_HERO = "UPDATE Super SET superName=?, superDescription=?, "
                + "superPower=?, superStatus=? WHERE superId=?";
        jdbc.update(UPDATE_HERO,
                superObj.getSuperName(),
                superObj.getSuperDescription(),
                superObj.getSuperPower(),
                superObj.getSuperStatus(),
                superObj.getSuperId());
    }

    @Override
    @Transactional
    public void deleteSuperById(int superId) {

        final String DELETE_HERO_FROM_HERO_ORG = "DELETE FROM super_org_bridge WHERE superId = ?";
        jdbc.update(DELETE_HERO_FROM_HERO_ORG, superId);

        final String DELETE_HERO_FROM_SIGHTING = "DELETE FROM sighting WHERE superId = ?";
        jdbc.update(DELETE_HERO_FROM_SIGHTING, superId);

        final String DELETE_HERO = "DELETE FROM Super WHERE superId = ?";
        jdbc.update(DELETE_HERO, superId);
    }

    @Override
    public List<Super> getSuperSightedForLocation(Location location) {
        return null;
    }

    @Override
    public List<Super> getOrganizationMembers(Organization org) {
        return null;
    }


    public static final class SuperMapper implements RowMapper<Super> {

        @Override
        public Super mapRow(ResultSet rs, int index) throws SQLException {
            Super superObj = new Super();
            superObj.setSuperId(rs.getInt("superId"));
            superObj.setSuperName(rs.getString("superName"));
            superObj.setSuperDescription(rs.getString("superDescription"));
            superObj.setSuperPower(rs.getString("superPower"));
            superObj.setSuperStatus(rs.getString("superStatus"));

            return superObj;
        }
    }
}
