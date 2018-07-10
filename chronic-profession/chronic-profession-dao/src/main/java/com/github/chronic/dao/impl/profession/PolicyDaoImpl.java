package com.github.chronic.dao.impl.profession;

import com.github.chronic.dao.impl.common.CommonDaoImpl;
import com.github.chronic.dao.impl.util.HibernateUtil;
import com.github.chronic.dao.profession.PolicyDao;
import com.github.chronic.pojo.Policy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName PolicyDaoImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/26 23:09
 * @Version 1.0
 **/
@Repository
public class PolicyDaoImpl extends CommonDaoImpl<Policy> implements PolicyDao {

    @Autowired
    private HibernateUtil hibernateUtil;


    @Override
    public Integer updateT(Policy entity) {
        String hql = "update Policy set year = ?, capline = ?, scale = ? where plid = ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, entity.getYear())
                .setParameter(1, entity.getCapline()).setParameter(2, entity.getScale())
                .setParameter(3, entity.getPlid()).executeUpdate();
    }

    @Override
    public List<Policy> findPolicyByPage(Integer curr, Integer limit) {
        String hql = "select new Policy(plid, year, capline, scale) from Policy";
        return findT(hql, curr, limit);
    }

    @Override
    public List<Policy> findPolicyByPage(Integer curr, Integer limit, String year) {
        String hql = "select new Policy(plid, year, capline, scale) from Policy where year = ?";
        return findT(hql, curr, limit, year);
    }

    @Override
    public List<Policy> findPolicyByYear(String year) {
        String hql = "select new Policy(plid, year, capline, scale) from Policy where year = ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, year).list();
    }
}
