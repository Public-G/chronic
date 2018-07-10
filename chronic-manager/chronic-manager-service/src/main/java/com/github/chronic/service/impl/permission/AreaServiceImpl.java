package com.github.chronic.service.impl.permission;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.dao.common.CommonDao;
import com.github.chronic.dao.permission.AreaDao;
import com.github.chronic.dao.permission.RoleManagerDao;
import com.github.chronic.pojo.Area;
import com.github.chronic.pojo.Role;
import com.github.chronic.service.impl.common.CommonService;
import com.github.chronic.service.permission.AreaService;
import com.github.chronic.service.util.DetachedCriteriaUtil;
import com.github.chronic.service.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AreaServiceImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/21 21:50
 * @Version 1.0
 **/
@Service
public class AreaServiceImpl extends CommonService implements AreaService {

    @Autowired
    private AreaDao areaDao;

    private List<Area> areaList;
    
    @Value("${levelPid}")
    private Integer levelPid;
    
    @Value("${areaLevel}")
    private Integer areaLevel;

    @Override
    public List<Area> findAreaByLevel() {
        return areaDao.findAreaByLevel(levelPid);
    }

    @Override
    public JsonBean findArea(String area_name, Integer curr) {
        if (StringUtils.isEmpty(area_name)) {
            detachedCriteria = DetachedCriteria.forClass(Area.class);
            areaList = areaDao.findAreaByPage(curr, limit);
        } else {
            detachedCriteria = DetachedCriteriaUtil.setRequire(Area.class, "area_name", area_name);
            areaList = areaDao.findAreaByPage(area_name, curr, limit);
        }
        count = ((CommonDao<Area>) areaDao).findCount(detachedCriteria);
        return new JsonBean(limit, curr, count, areaList);
    }

    @Override
    public boolean fieldVerify(Area area) {
        List<Area> verify = null;
        if(!StringUtils.isEmpty(area.getArea_code())){
            verify = ((CommonDao<Area>) areaDao).findFieldVerify("Area", "area_code", area.getArea_code());
        }
        if (verify != null && verify.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean saveArea(Area area) {
        Integer rowResult = ((CommonDao<Area>) areaDao).saveT(area);
        if (rowResult != null) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteBatchOrSingle(String checkids) {
        List<Integer> aid = split(checkids);
        detachedCriteria = DetachedCriteriaUtil.setIn(Area.class, "aid", aid);
        ((CommonDao<Area>) areaDao).deleteBatchOrSingle(detachedCriteria);
    }

    @Override
    public boolean updateArea(Area area) {
        if (((CommonDao<Area>) areaDao).updateT(area) > 0) {
            return true;
        }
        return false;
    }

    /**
    * @Description 行政区域树展示
    * @Param []
    * @return java.util.List<com.github.chronic.pojo.Area>
    **/
    @Override
    public List<Area> findAreaByAreacode(String session_area_code) {
        return areaDao.findAreaByAreacode(session_area_code);
    }
}
