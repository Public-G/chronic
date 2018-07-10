package com.github.chronic.service.util;

import java.util.List;
import java.util.Map;
import java.util.Set;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;


/**
 * 离线查询工具类
 * @ClassName DetachedCriteriaUtil
 * @author ZEALER
 * @Date 2018年6月6日 下午2:52:04
 * @version 1.0.0
 */
public class DetachedCriteriaUtil {
    
    private static DetachedCriteria detachedCriteria;
    
    /**
     * 定义相等查询条件
     * @param require
     * @return
     */
    public static DetachedCriteria setRequire(Class<?> clazz, String field, Object require) {

        detachedCriteria = DetachedCriteria.forClass(clazz);
        
        detachedCriteria =  detachedCriteria.add(Restrictions.eq(field, require));

        return detachedCriteria;
    }


    /**
    * @Description 多条件相等
    * @Param
    * @return
    **/
    public static DetachedCriteria setRequirePlus(Class<?> clazz, Map<String, Object> require){

        detachedCriteria = DetachedCriteria.forClass(clazz);

        Set<Map.Entry<String,Object>> entrySet = require.entrySet();

        for (Object obj :entrySet) {
            Map.Entry entry = (Map.Entry) obj;
            detachedCriteria =  detachedCriteria.add(Restrictions.eq((String) entry.getKey(), entry.getValue()));
        }

        return detachedCriteria;
    }
    
    /**
     * 组装in语句
     * @param
     * @return
     */
    public static DetachedCriteria setIn(Class<?> clazz, String field, List<Integer> list) {

        detachedCriteria = DetachedCriteria.forClass(clazz);
        
        detachedCriteria.add(Restrictions.in(field, list));

        return detachedCriteria;
    }

}
