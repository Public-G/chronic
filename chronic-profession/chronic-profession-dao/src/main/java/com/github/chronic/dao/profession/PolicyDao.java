package com.github.chronic.dao.profession;

import com.github.chronic.pojo.Policy;

import java.util.List;

public interface PolicyDao {
    List<Policy> findPolicyByPage(Integer curr, Integer limit);

    List<Policy> findPolicyByPage(Integer curr, Integer limit, String year);

    List<Policy> findPolicyByYear(String year);
}
