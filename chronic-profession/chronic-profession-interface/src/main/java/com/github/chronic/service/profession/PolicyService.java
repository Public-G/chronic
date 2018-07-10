package com.github.chronic.service.profession;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.pojo.Policy;

public interface PolicyService {
    JsonBean findPolicy(Integer curr, Policy policy);

    boolean savePolicy(Policy policy);

    boolean updatePolicy(Policy policy);

    void deleteBatchOrSingle(String checkids);


}
