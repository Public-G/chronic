package com.github.chronic.service.impl.profession;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.dao.common.CommonDao;
import com.github.chronic.dao.profession.PolicyDao;
import com.github.chronic.pojo.Area;
import com.github.chronic.pojo.File;
import com.github.chronic.pojo.Policy;
import com.github.chronic.service.impl.common.CommonService;
import com.github.chronic.service.profession.JoinService;
import com.github.chronic.service.profession.PolicyService;
import com.github.chronic.service.util.DetachedCriteriaUtil;
import com.github.chronic.service.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PolicyServiceImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/26 23:08
 * @Version 1.0
 **/
@Service
public class PolicyServiceImpl extends CommonService implements PolicyService {

    @Autowired
    private PolicyDao policyDao;

    private List<Policy> policyList;


    @Override
    public JsonBean findPolicy(Integer curr, Policy policy) {
        String year = policy.getYear();
        if (StringUtils.isEmpty(year)) {
            detachedCriteria = DetachedCriteria.forClass(Policy.class);
            policyList = policyDao.findPolicyByPage(curr, limit);
        } else {
            detachedCriteria = DetachedCriteriaUtil.setRequire(Policy.class, "year", year);
            policyList = policyDao.findPolicyByPage(curr, limit, year);
        }
        count = ((CommonDao<Policy>) policyDao).findCount(detachedCriteria);
        return new JsonBean(limit, curr, count, policyList);
    }

    @Override
    public boolean savePolicy(Policy policy) {
        Integer rowResult = ((CommonDao<Policy>) policyDao).saveT(policy);
        if (rowResult != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePolicy(Policy policy) {
        if ( ((CommonDao<Policy>) policyDao).updateT(policy) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteBatchOrSingle(String checkids) {
        List<Integer> plid = split(checkids);
        detachedCriteria = DetachedCriteriaUtil.setIn(Policy.class, "plid", plid);
        ((CommonDao<Policy>) policyDao).deleteBatchOrSingle(detachedCriteria);
    }
}
