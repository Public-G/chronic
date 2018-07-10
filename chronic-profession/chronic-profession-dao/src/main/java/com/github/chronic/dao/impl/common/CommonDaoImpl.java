package com.github.chronic.dao.impl.common;

import com.github.chronic.dao.common.CommonDao;
import com.github.chronic.dao.impl.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName CommonDaoImpl
 * @Description 通用接口的实现类
 * @Author ZEALER
 * @Date 2018/6/17 18:29
 * @Version 1.0
 **/
@Repository
public abstract class CommonDaoImpl<T> implements CommonDao<T>{

    @Autowired
    private HibernateUtil hibernateUtil;


    /**
    * @Description 分页查询
    * @Param [hql, firstResult, maxResult]
    * @return java.util.List<T>
    **/
    @Override
    public List<T> findT(String hql, Integer firstResult, Integer maxResult) {
        return hibernateUtil.getSession().createQuery(hql).setFirstResult((firstResult - 1) * maxResult)
                .setMaxResults(maxResult).list();
    }

    /**
    * @Description 带条件的分页查询
    * @Param [hql, require, firstResult, maxResult]
    * @return java.util.List<T>
    **/
    @Override
    public List<T> findT(String hql, Integer firstResult, Integer maxResult, Object... require) {
        Query query = hibernateUtil.getSession().createQuery(hql);
        for(int i = 0; i < require.length; i++){
            query.setParameter(i, require[i]);
        }
        return query.setFirstResult((firstResult - 1) * maxResult)
                .setMaxResults(maxResult).list();
    }

    /**
    * @Description 数据总数
    * @Param [detachedCriteria]
    * @return java.lang.Long
    **/
    @Override
    public Long findCount(DetachedCriteria detachedCriteria) {
        return (Long) detachedCriteria.getExecutableCriteria(hibernateUtil.getSession())
                .setProjection(Projections.rowCount()).uniqueResult();
    }


    private String findFieldHql(String entity, String fieldName) {
        return "from " + entity + " where " + fieldName + " = ?";
    }
    /**
     * @Description 字段唯一性校验
     * @Param [entity, fieldName, require]
     * @return java.util.List<T>
     **/
    @Override
    public List<T> findFieldVerify(String entity, String fieldName, Object require) {
        String hql = findFieldHql(entity, fieldName);
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, require).list();
    }

    /**
    * @Description 根据id 批量删除
    * @Param [detachedCriteria]
    * @return void
    **/
    @Override
    public void deleteBatchOrSingle(DetachedCriteria detachedCriteria) {
        Session session = hibernateUtil.getSession();
        List<T> list = detachedCriteria.getExecutableCriteria(session).list();
        for (T entity : list) {
            session.delete(entity);
        }
    }

    /**
    * @Description 添加
    * @Param [entity]
    * @return java.lang.Integer
    **/
    @Override
    public Integer saveT(T entity) {

        return (Integer) hibernateUtil.getSession().save(entity);
    }


}
