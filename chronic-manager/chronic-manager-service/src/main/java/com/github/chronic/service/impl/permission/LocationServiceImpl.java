package com.github.chronic.service.impl.permission;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.dao.common.CommonDao;
import com.github.chronic.dao.permission.LocationDao;
import com.github.chronic.pojo.Area;
import com.github.chronic.pojo.Location;
import com.github.chronic.pojo.Menu;
import com.github.chronic.pojo.Role;
import com.github.chronic.service.impl.common.CommonService;
import com.github.chronic.service.permission.LocationService;
import com.github.chronic.service.util.DetachedCriteriaUtil;
import com.github.chronic.service.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName LocationServiceImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/19 18:08
 * @Version 1.0
 **/
@Service
public class LocationServiceImpl extends CommonService implements LocationService {

    @Autowired
    private LocationDao locationDao;

    private List<Location> locationList;


    @Override
    public JsonBean findLocation(String org_name, Integer curr) {
        if (StringUtils.isEmpty(org_name)) {
            detachedCriteria = DetachedCriteria.forClass(Location.class);
            locationList = locationDao.findLocationByPage(curr, limit);
        } else {
            detachedCriteria = DetachedCriteriaUtil.setRequire(Location.class, "org_name", org_name);
            locationList = locationDao.findLocationByPage(org_name, curr, limit);
        }
        count = ((CommonDao<Location>) locationDao).findCount(detachedCriteria);
        JsonBean jsonBean = new JsonBean(limit, curr, count, locationList);
        return jsonBean;
    }


    @Override
    public boolean saveLocation(Location location) {
        Integer rowResult = ((CommonDao<Location>) locationDao).saveT(location);
        if (rowResult != null) {
            return true;
        }
        return false;

    }

    @Override
    public boolean fieldVerify(Location location) {
        List<Location> verify = null;
        if(!StringUtils.isEmpty(location.getOrg_code())){
            verify = ((CommonDao<Location>) locationDao).findFieldVerify("Location", "org_code", location.getOrg_code());
        }
        if (verify != null && verify.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteBatchOrSingle(String checkids) {
        List<Integer> locid = split(checkids);
        detachedCriteria = DetachedCriteriaUtil.setIn(Location.class, "locid", locid);
        ((CommonDao<Location>) locationDao).deleteBatchOrSingle(detachedCriteria);
    }

    @Override
    public boolean updateLocation(Location location) {
        if (((CommonDao<Location>) locationDao).updateT(location) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Location> findAllLocation() {
        return locationDao.findAllLocation();
    }
}
