package com.github.chronic.dao.permission;

import com.github.chronic.pojo.Role;
import com.github.chronic.pojo.User;

import java.util.List;

public interface UserManagerDao {


    List<User> findUserByPage(Integer curr, Integer limit);

    List<User> findUserByPage(String user_name, Integer curr, Integer limit);

    Integer updateResetPwd(Integer user_id, String resetPwd);

    List<Role> findUnGrantRole(Integer user_id);

    List<Role> findGrantRole(Integer user_id, Integer curr, Integer limit);

    Long findGrantRoleCount(Integer user_id);

    boolean updateUserRole(String type, Integer user_id, Integer role_id);

    void updateUserLocation(Integer user_id, Integer locid, String job_id);

}
