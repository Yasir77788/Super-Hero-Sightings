package com.sg.superheroSightings.dao;

import com.sg.superheroSightings.dto.Super;
import com.sg.superheroSightings.dto.Location;
import com.sg.superheroSightings.dto.Organization;


import java.util.List;

public interface SuperDao {

      List<Super> getAllSupers();

      Super addSuper(Super superObj);

      Super getSuperById(int superId);

      void updateSuper(Super superObj);

      void deleteSuperById(int superId);

      List<Super> getSuperSightedForLocation(Location location);

      List<Super> getOrganizationMembers(Organization org);
}
