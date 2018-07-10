package com.github.chronic.service.impl.permission;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.dao.common.CommonDao;
import com.github.chronic.dao.permission.RoleManagerDao;
import com.github.chronic.pojo.Role;
import com.github.chronic.service.impl.common.CommonService;
import com.github.chronic.service.permission.RoleManagerService;
import com.github.chronic.service.util.DetachedCriteriaUtil;
import com.github.chronic.service.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RoleManagerServiceImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/17 21:42
 * @Version 1.0
 **/
@Service
public class RoleManagerServiceImpl extends CommonService implements RoleManagerService {

    @Autowired
    private RoleManagerDao roleManagerDao;

    private List<Role> roleList;

    @Override
    public JsonBean findRoles(String role_name, Integer curr) {
        if (StringUtils.isEmpty(role_name)) {
            detachedCriteria = DetachedCriteria.forClass(Role.class);
            roleList = roleManagerDao.findRoleByPage(curr, limit);
        } else {
            detachedCriteria = DetachedCriteriaUtil.setRequire(Role.class, "role_name", role_name);
            roleList = roleManagerDao.findRoleByPage(role_name, curr, limit);
        }
        count = ((CommonDao<Role>) roleManagerDao).findCount(detachedCriteria);
        JsonBean jsonBean = new JsonBean(limit, curr, count, roleList);
        return jsonBean;
    }

    @Override
    public boolean saveRole(Role role) {
        Integer rowResult = ((CommonDao<Role>) roleManagerDao).saveT(role);
        if (rowResult != null) {
            return true;
        }
        return false;
    }


    @Override
    public boolean fieldVerify(Role role) {
        List<Role> verify = null;
        if(!StringUtils.isEmpty(role.getRole_name())){
            verify = ((CommonDao<Role>) roleManagerDao).findFieldVerify("Role", "role_name", role.getRole_name());
        }
        if(!StringUtils.isEmpty(role.getRole_label())){
            verify = ((CommonDao<Role>) roleManagerDao).findFieldVerify("Role", "role_label",role.getRole_label());
        }
        if (verify != null && verify.size() > 0) {
            return true;
        }
        return false;
    }


    @Override
    public void deleteBatchOrSingle(String checkids) {
        List<Integer> role_id = split(checkids);
        detachedCriteria = DetachedCriteriaUtil.setIn(Role.class, "role_id", role_id);
        ((CommonDao<Role>) roleManagerDao).deleteBatchOrSingle(detachedCriteria);
    }

    @Override
    public boolean updateRole(Role role) {
        if (((CommonDao<Role>) roleManagerDao).updateT(role) > 0) {
            return true;
        }
        return false;
    }
}
