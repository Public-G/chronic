package com.github.chronic.service.permission;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.pojo.Location;

import java.util.List;

public interface LocationService {
    JsonBean findLocation(String org_name, Integer curr);

    boolean saveLocation(Location location);

    boolean fieldVerify(Location location);

    void deleteBatchOrSingle(String checkids);

    boolean updateLocation(Location location);


    List<Location> findAllLocation();

}
