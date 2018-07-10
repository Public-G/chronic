package com.github.chronic.service.permission;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.pojo.Area;

import java.util.List;

public interface AreaService {
    List<Area> findAreaByLevel();

    JsonBean findArea(String area_name, Integer curr);

    boolean fieldVerify(Area area);

    boolean saveArea(Area area);

    void deleteBatchOrSingle(String checkids);

    boolean updateArea(Area area);


    List<Area> findAreaByAreacode(String session_area_code);

}
