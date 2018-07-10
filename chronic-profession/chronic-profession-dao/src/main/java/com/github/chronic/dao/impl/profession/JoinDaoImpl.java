package com.github.chronic.dao.impl.profession;

import com.github.chronic.dao.impl.common.CommonDaoImpl;
import com.github.chronic.dao.impl.util.HibernateUtil;
import com.github.chronic.dao.profession.JoinDao;
import com.github.chronic.pojo.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName JoinDaoImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/25 11:07
 * @Version 1.0
 **/
@Repository
public class JoinDaoImpl extends CommonDaoImpl<Join> implements JoinDao {

    @Autowired
    private HibernateUtil hibernateUtil;


    @Override
    public List<Join> findJoinById(Integer jid) {
        String hql = "select new Join(join_id, invoice, create_time) from Join where jid = ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, jid).list();
    }

    @Override
    public Long findCountByFamilyid(String family_id) {
        String hql = "select count(*) from Join j where j.file.family_id = ?";
        return (Long) hibernateUtil.getSession().createQuery(hql).setParameter(0, family_id).uniqueResult();
    }

    @Override
    public Integer updateT(Join entity) {
        return null;
    }
}
