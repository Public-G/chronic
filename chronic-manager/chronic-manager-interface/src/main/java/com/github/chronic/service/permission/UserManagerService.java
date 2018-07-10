package com.github.chronic.service.permission;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.pojo.User;

/**
 * @ClassName UserManagerService
 * @Description
 * @Author ZEALER
 * @Date 2018/6/17 0:45
 * @Version 1.0
 **/
public interface UserManagerService {

    JsonBean findUsers(String user_name, Integer curr);

    boolean fieldVerify(User user);

    boolean saveUser(User user);

    void deleteBatchOrSingle(String checkids);

    boolean updateResetPwd(Integer user_id);

    boolean updateUser(User user);

    JsonBean findUserRole(Integer user_id, Integer curr);

    boolean updateUserRole(String type, Integer user_id, Integer role_id);

    boolean updateUserLocation(Integer user_id, Integer locid);


}
