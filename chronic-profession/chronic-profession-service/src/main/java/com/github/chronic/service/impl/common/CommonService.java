package com.github.chronic.service.impl.common;


import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CommonService
 * @Description
 * @Author ZEALER
 * @Date 2018/6/17 21:52
 * @Version 1.0
 **/
@Component
public class CommonService{

    @Value("${limit}")
    protected Integer limit;

    protected DetachedCriteria detachedCriteria;

    protected Long count;

    @Transactional(readOnly = true)
    public List<Integer> split(String checkids){
        String[] split = checkids.split(",");
        List<Integer> list = new ArrayList<>();
        for (String s : split) {
            Integer i = 0;
            try {
                i = Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
            }
            list.add(i);
        }
        return list;
    }
}
