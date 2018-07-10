package com.github.chronic.dao.permission;

import com.github.chronic.pojo.Location;

import java.util.List;

public interface LocationDao {
    List<Location> findLocationByPage(Integer curr, Integer limit);

    List<Location> findLocationByPage(String org_name, Integer curr, Integer limit);

    List<Location> findAllLocation();

    List<Location> findLocationByLocid(Integer locid);

}
