package com.github.chronic.service.permission;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.pojo.Role;

public interface RoleManagerService {

    JsonBean findRoles(String role_name, Integer curr);

    boolean saveRole(Role role);

    boolean fieldVerify(Role role);

    void deleteBatchOrSingle(String checkids);

    boolean updateRole(Role role);
}
