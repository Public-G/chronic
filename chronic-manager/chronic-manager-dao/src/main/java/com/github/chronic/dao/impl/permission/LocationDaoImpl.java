package com.github.chronic.dao.impl.permission;

import com.github.chronic.dao.impl.common.CommonDaoImpl;
import com.github.chronic.dao.impl.util.HibernateUtil;
import com.github.chronic.dao.permission.LocationDao;
import com.github.chronic.pojo.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName LocationDaoImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/19 18:12
 * @Version 1.0
 **/
@Repository
public class LocationDaoImpl extends CommonDaoImpl<Location> implements LocationDao {

    @Autowired
    private HibernateUtil hibernateUtil;


    @Override
    public Integer updateT(Location entity) {
        String hql = "update Location set org_code = ?, area_code = ?, org_name = ? where locid = ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, entity.getOrg_code())
                .setParameter(1, entity.getArea_code()).setParameter(2, entity.getOrg_name())
                .setParameter(3, entity.getLocid()).executeUpdate();
    }

    @Override
    public List<Location> findLocationByPage(Integer curr, Integer limit) {
        String hql = "select new Location(locid, org_code, area_code, org_name) from Location";
        return findT(hql, curr, limit);
    }

    @Override
    public List<Location> findLocationByPage(String org_name, Integer curr, Integer limit) {
        String hql = "select new Location(locid, org_code, area_code, org_name) from Location where org_name like ?";
        return findT(hql, curr, limit, "%"+org_name+"%");
    }

    @Override
    public List<Location> findAllLocation() {
        String hql = "select new Location(locid, org_code, area_code, org_name) from Location";
        return hibernateUtil.getSession().createQuery(hql).list();
    }

    @Override
    public List<Location> findLocationByLocid(Integer locid) {
        String hql = "select new Location(locid, org_code, area_code, org_name) from Location where locid = ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, locid).list();
    }


}
