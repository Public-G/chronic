package com.github.chronic.dao.common;

import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface CommonDao<T> {

    List<T> findT(String hql, Integer firstResult, Integer maxResult);

    List<T> findT(String hql, Integer firstResult, Integer maxResult, Object... require);

    Long findCount(DetachedCriteria detachedCriteria);

    List<T> findFieldVerify(String entity, String fieldName, Object require);

    void deleteBatchOrSingle(DetachedCriteria detachedCriteria);

    Integer saveT(T entity);

    Integer updateT(T entity);
}
