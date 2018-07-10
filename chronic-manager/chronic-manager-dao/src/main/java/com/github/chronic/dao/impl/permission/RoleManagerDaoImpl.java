package com.github.chronic.dao.impl.permission;

import com.github.chronic.dao.impl.common.CommonDaoImpl;
import com.github.chronic.dao.impl.util.HibernateUtil;
import com.github.chronic.dao.permission.RoleManagerDao;
import com.github.chronic.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName RoleManagerDaoImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/17 21:44
 * @Version 1.0
 **/
@Repository
public class RoleManagerDaoImpl extends CommonDaoImpl<Role> implements RoleManagerDao{

    @Autowired
    private HibernateUtil hibernateUtil;

    @Override
    public List<Role> findRoleByPage(Integer curr, Integer limit) {
        String hql = "select new Role(role_id, role_name, role_label) from Role";
        return findT(hql, curr, limit);
    }

    @Override
    public List<Role> findRoleByPage(String role_name, Integer curr, Integer limit) {
        String hql = "select new Role(role_id, role_name, role_label) from Role where role_name = ?";
        return findT(hql, curr, limit, role_name);
    }

    @Override
    public Integer updateT(Role entity) {
        String hql = "update Role set role_name = ?, role_label = ? where role_id = ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, entity.getRole_name())
                .setParameter(1, entity.getRole_label()).setParameter(2, entity.getRole_id()).executeUpdate();
    }
}
