package com.github.chronic.dao.permission;

import com.github.chronic.pojo.Role;

import java.util.List;

public interface RoleManagerDao {

    List<Role> findRoleByPage(Integer curr, Integer limit);

    List<Role> findRoleByPage(String role_name, Integer curr, Integer limit);
}
