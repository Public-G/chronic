package com.github.chronic.service.impl.permission;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.dao.common.CommonDao;
import com.github.chronic.dao.permission.LocationDao;
import com.github.chronic.dao.permission.UserManagerDao;
import com.github.chronic.pojo.Location;
import com.github.chronic.pojo.Role;
import com.github.chronic.pojo.User;
import com.github.chronic.service.impl.common.CommonService;
import com.github.chronic.service.permission.UserManagerService;
import com.github.chronic.service.util.DetachedCriteriaUtil;
import com.github.chronic.service.util.StringUtils;
import com.github.chronic.service.util.UUIDUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName UserManagerServiceImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/17 0:46
 * @Version 1.0
 **/
@Service
public class UserManagerServiceImpl extends CommonService implements UserManagerService {

    @Autowired
    private UserManagerDao userManagerDao;

    @Autowired
    private LocationDao locationDao;

    private List<User> userList;

    @Value("${resetPwd}")
    private String resetPassword;

    /**
     * @return com.github.chronic.bean.JsonBean
     * @Description 用户信息
     * @Param [user_name, curr]
     **/
    @Override
    public JsonBean findUsers(String user_name, Integer curr) {

        if (StringUtils.isEmpty(user_name)) {
            detachedCriteria = DetachedCriteria.forClass(User.class);
            userList = userManagerDao.findUserByPage(curr, limit);
        } else {
            detachedCriteria = DetachedCriteriaUtil.setRequire(User.class, "user_name", user_name);
            userList = userManagerDao.findUserByPage(user_name, curr, limit);
        }

        count = ((CommonDao<User>) userManagerDao).findCount(detachedCriteria);

        return new JsonBean(limit, curr, count, userList);

    }


    /**
     * @return boolean
     * @Description 字段唯一性校验
     * @Param [user_name]
     **/
    @Override
    public boolean fieldVerify(User user) {
        List<User> verify = null;
        if(!StringUtils.isEmpty(user.getUser_name())){
            verify = ((CommonDao<User>) userManagerDao).findFieldVerify("User", "user_name", user.getUser_name());
        }
        if (!StringUtils.isEmpty(user.getCellphone())){
            verify = ((CommonDao<User>) userManagerDao).findFieldVerify("User", "cellphone", user.getCellphone());
        }
        if (verify != null && verify.size() > 0) {
            return true;
        }
        return false;
    }


    /**
     * @return boolean
     * @Description 添加用户
     * @Param [user]
     **/
    @Override
    public boolean saveUser(User user) {
        user.setCreate_time(new Date());
        user.setPassword_md5(DigestUtils.md5DigestAsHex(user.getPassword_md5().getBytes()));
        Integer rowResult = ((CommonDao<User>) userManagerDao).saveT(user);
        if (rowResult != null) {
            return true;
        }
        return false;
    }


    /**
     * @return void
     * @Description 删除
     * @Param [checkids]
     **/
    @Override
    public void deleteBatchOrSingle(String checkids) {
        List<Integer> user_id = split(checkids);
        detachedCriteria = DetachedCriteriaUtil.setIn(User.class, "user_id", user_id);
        ((CommonDao<User>) userManagerDao).deleteBatchOrSingle(detachedCriteria);

    }

    @Override
    public boolean updateResetPwd(Integer user_id) {
        String resetPwd = DigestUtils.md5DigestAsHex(resetPassword.getBytes());
        Integer resetPwdResult = userManagerDao.updateResetPwd(user_id, resetPwd);
        if (resetPwdResult > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        if (((CommonDao<User>) userManagerDao).updateT(user) > 0) {
            return true;
        }
        return false;
    }

    /**
    * @Description 用户角色信息
    * @Param [user_id, curr]
    * @return com.github.chronic.bean.JsonBean
    **/
    @Override
    public JsonBean findUserRole(Integer user_id, Integer curr) {
        // 查询用户未分配的角色
        List<Role> unGrantRoleList = userManagerDao.findUnGrantRole(user_id);

        // 查询用户已分配的角色
        List<Role> grantRoleList = userManagerDao.findGrantRole(user_id, curr, limit);

        count = userManagerDao.findGrantRoleCount(user_id);

        List<List<Role>> roleList = new ArrayList<>();
        roleList.add(grantRoleList);
        roleList.add(unGrantRoleList);

        return new JsonBean(limit, curr, count, roleList);

    }

    /**
    * @Description 分配与取消分配
    * @Param [type, user_id, role_id]
    * @return boolean
    **/
    @Override
    public boolean updateUserRole(String type, Integer user_id, Integer role_id) {
        return userManagerDao.updateUserRole(type, user_id, role_id);
    }

    /**
    * @Description 分配或更新用户所属经办点
    * @Param [user_id, locid]
    * @return boolean
    **/
    @Override
    public boolean updateUserLocation(Integer user_id, Integer locid) {
        boolean flag = false;
        String job_id = UUIDUtil.generateShortUUID();
        try {
            userManagerDao.updateUserLocation(user_id, locid, job_id);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }
}
