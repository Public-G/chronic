package com.github.chronic.dao.impl.permission;

import com.github.chronic.dao.impl.common.CommonDaoImpl;
import com.github.chronic.dao.impl.util.HibernateUtil;
import com.github.chronic.dao.permission.AreaDao;
import com.github.chronic.pojo.Area;
import com.github.chronic.pojo.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName AreaDaoImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/21 22:14
 * @Version 1.0
 **/
@Repository
public class AreaDaoImpl extends CommonDaoImpl<Area> implements AreaDao {

    @Autowired
    private HibernateUtil hibernateUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer updateT(Area entity) {
        String hql = "update Area set area_code = ?, pid = ?, level = ?, area_name = ? where aid = ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, entity.getArea_code())
                .setParameter(1, entity.getPid()).setParameter(2, entity.getLevel())
                .setParameter(3, entity.getArea_name())
                .setParameter(4, entity.getAid()).executeUpdate();
    }

    @Override
    public List<Area> findAreaByPage(Integer curr, Integer limit) {
        String hql = "select new Area(aid, area_code, pid, area_name, level) from Area";
        return findT(hql, curr, limit);
    }

    @Override
    public List<Area> findAreaByPage(String area_name, Integer curr, Integer limit) {
        String hql = "select new Area(aid, area_code, pid, area_name, level) from Area where area_name like ?";
        return findT(hql, curr, limit, "%"+area_name+"%");
    }

    @Override
    public List<Area> findAreaByLevel(Integer levelPid) {
        String hql = "select new Area(aid, area_code, pid, area_name, level) from Area where level < ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, levelPid).list();
    }

    @Override
    public List<Area> findAreaByAreacode(String session_area_code) {
        String sqlQuery = "select area_code, pid, area_name from tb_area WHERE area_code IN ( SELECT area_code FROM tb_area WHERE FIND_IN_SET(area_code, queryChildrenAreaInfo(?)) )";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Area.class), session_area_code);
    }
}
