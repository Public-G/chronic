package com.github.chronic.dao.permission;


import com.github.chronic.pojo.Area;

import java.util.List;

public interface AreaDao {
    List<Area> findAreaByPage(Integer curr, Integer limit);

    List<Area> findAreaByPage(String area_name, Integer curr, Integer limit);

    List<Area> findAreaByLevel(Integer levelPid);

    List<Area> findAreaByAreacode(String session_area_code);

}
