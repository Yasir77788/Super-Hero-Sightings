package com.sg.superheroSightings.dao;

import com.sg.superheroSightings.dto.Location;
import com.sg.superheroSightings.dto.Super;
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
public class OrganizationDaoDBImpl implements OrganizationDao{

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    LocationDao locationDao;

    @Override
    public Organization getOrganizationById(int orgId) {
        try {
            final String SELECT_ORG_BY_ID = "SELECT * FROM Organization WHERE orgId = ?";
            Organization org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrganizationMapper(), orgId);
            org.setLocation(getLocationForAnOrg(orgId));
            org.setMembers(getMembersForAnOrg(orgId));
            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForAnOrg(int orgId){
        final String SELECT_Location_FOR_ORG = "SELECT l.* FROM Location l "
                + "JOIN Organization o ON o.locationId = l.locationId WHERE o.orgId = ?";
        return jdbc.queryForObject(SELECT_Location_FOR_ORG, new LocationDaoDBImpl.LocationMapper(), orgId);

    }

    private List<Super> getMembersForAnOrg(int orgId) {
        final String SELECT_MEM_FOR_ORG = "SELECT s.* FROM Super s JOIN Super_Org_bridge"
                + " sob ON sob.superId = s.superId WHERE sob.orgId=?";
        return jdbc.query(SELECT_MEM_FOR_ORG, new SuperDaoDBImpl.SuperMapper(), orgId);
    }


    @Override
    @Transactional
    public Organization addOrganization(Organization org) {
        final String INSERT_ORG = "INSERT INTO Organization(orgName, orgDescription, orgPhone, "
                + "orgEmail, heroOrVillainOrg, locationId) VALUES(?,?,?,?,?,?)";
        jdbc.update(INSERT_ORG,
                org.getOrgName(),
                org.getOrgDescription(),
                org.getOrgPhone(),
                org.getOrgEmail(),
                org.getHeroOrVillainOrg(),
                org.getLocation().getLocationId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setOrgId(newId);
        insertOrgSuper(org);
        return org;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGS = "SELECT * FROM Organization";
        return jdbc.query(GET_ALL_ORGS, new OrganizationMapper());
    }


    @Override
    @Transactional
    public void updateOrganization(Organization org) {
        final String UPDATE_ORG = "UPDATE Organization SET orgName=?, orgDescription=?, orgPhone=?,"
                + "orgEmail=?, heroOrVillainOrg=?, locationId=? WHERE orgId=?";
        jdbc.update(UPDATE_ORG,
                org.getOrgName(),
                org.getOrgDescription(),
                org.getOrgPhone(),
                org.getOrgEmail(),
                org.getHeroOrVillainOrg(),
                org.getLocation().getLocationId(),
                org.getOrgId());
        final String DELETE_SUPER_ORG_BRIDGE = "DELETE FROM Super_Org_Bridge WHERE orgId=?";
        jdbc.update(DELETE_SUPER_ORG_BRIDGE, org.getOrgId());
        insertOrgSuper(org);
    }

    // helper method
    private void insertOrgSuper(Organization org) {
        final String INSERT_SUPER_ORG_BRIDGE = "INSERT INTO "
                + "Super_Org_Bridge(superId, orgId) VALUES(?,?)";
        for(Super sp: org.getMembers()) {
            jdbc.update(INSERT_SUPER_ORG_BRIDGE,
                    sp.getSuperId(),
                    org.getOrgId());
        }
    }

    @Override
    public void deleteOrganizationById(int orgId) {
        final String DELETE_ORG_FROM_SUPER_ORG_BRIDGE = "DELETE FROM Super_Org_Bridge WHERE orgId=?";
        jdbc.update(DELETE_ORG_FROM_SUPER_ORG_BRIDGE, orgId);

        final String DELETE_ORG = "DELETE FROM Organization WHERE orgId = ?";
        jdbc.update(DELETE_ORG, orgId);
    }

    @Override
    public List<Organization> getOrgsForSuper(Super super1) {

        final String SELECT_ORGS_FOR_SUPER = "SELECT o.* FROM Organization o JOIN "
                + "Super_Org_Bridge sb ON sb.orgId = o.orgId WHERE sb.superId = ?";
        List<Organization> orgList = jdbc.query(SELECT_ORGS_FOR_SUPER,
                new OrganizationMapper(), super1.getSuperId());
        associateLocationAndSupers(orgList);
        return orgList;
    }

    private void associateLocationAndSupers(List<Organization> orgList) {

        for (Organization org : orgList) {
            org.setLocation(getLocationForOrg(org.getOrgId()));
            org.setMembers(getSupersForOrg(org.getOrgId()));
        }
    }

    private List<Super> getSupersForOrg(int orgId) {

        final String SELECT_SUPERS_FOR_ORG = "SELECT s.* FROM Super s "
                + "JOIN super_org_bridge sb ON sb.superId = s.superId WHERE sb.orgId = ?";
        return jdbc.query(SELECT_SUPERS_FOR_ORG, new SuperDaoDBImpl.SuperMapper(), orgId);
    }

    private Location getLocationForOrg(int orgId) {
        final String SELECT_LOCATION_FOR_ORG = "SELECT l.* FROM location l "
                + "JOIN Organization o ON o.locationId = l.locationId WHERE o.orgId = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_ORG, new LocationDaoDBImpl.LocationMapper(), orgId);
        
    }


    public final class OrganizationMapper implements RowMapper<Organization> {
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();

            org.setOrgId(rs.getInt("orgID"));
            org.setOrgName(rs.getString("orgName"));
            org.setOrgDescription(rs.getString("orgDescription"));
            org.setOrgPhone(rs.getString("orgPhone"));
            org.setOrgEmail(rs.getString("orgEmail"));
            org.setHeroOrVillainOrg(rs.getString("heroOrVillainOrg"));
            org.setLocation(locationDao.getLocationById(rs.getInt("locationId")));
            org.setMembers(getMembersForAnOrg(org.getOrgId()));

            return org;
        }
    }


}
