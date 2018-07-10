package com.github.chronic.dao.impl.permission;

import com.github.chronic.dao.impl.common.CommonDaoImpl;
import com.github.chronic.dao.impl.util.HibernateUtil;
import com.github.chronic.dao.permission.UserManagerDao;
import com.github.chronic.pojo.Role;
import com.github.chronic.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UserManagerDaoImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/17 18:43
 * @Version 1.0
 **/
@Repository
public class UserManagerDaoImpl extends CommonDaoImpl<User> implements UserManagerDao{

    @Autowired
    private HibernateUtil hibernateUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findUserByPage(Integer curr, Integer limit) {
        /*select new User(user_id,  user_name, cellphone, job_id, email, create_time, location) */
        /*String hql = "from User";*/
        String sqlQuery = "SELECT u.user_id, u.user_name, u.cellphone, u.job_id, u.email, u.create_time, l.org_name FROM tb_user u LEFT JOIN tb_location l ON u.`locid` = l.`locid` LIMIT ?, ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class), (curr - 1) * limit, limit);
    }

    @Override
    public List<User> findUserByPage(String user_name, Integer curr, Integer limit) {
        String sqlQuery = "SELECT u.user_id, u.user_name, u.cellphone, u.job_id, u.email, u.create_time, l.org_name FROM tb_user u LEFT JOIN tb_location l ON u.`locid` = l.`locid` WHERE u.user_name = ? LIMIT ?, ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(User.class), user_name, (curr - 1) * limit, limit);
    }

    @Override
    public Integer updateResetPwd(Integer user_id, String resetPwd) {
        String hql = "update User set password_md5 = ? where user_id = ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, resetPwd).setParameter(1, user_id)
                .executeUpdate();
    }

    /**
    * @Description 用户未分配的角色
    * @Param [user_id]
    * @return java.util.List<com.github.chronic.pojo.Role>
    **/
    @Override
    public List<Role> findUnGrantRole(Integer user_id) {
        String sqlQuery = "SELECT r.role_id, r.role_name FROM tb_role r WHERE r.role_id NOT IN( SELECT r.role_id FROM tb_role r INNER JOIN tb_user_role ur ON r.role_id = ur.role_id WHERE ur.user_id=?)";
       return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Role.class), user_id);
    }

    /**
    * @Description 用户已分配的角色
    * @Param [user_id, curr, limit]
    * @return java.util.List<com.github.chronic.pojo.Role>
    **/
    @Override
    public List<Role> findGrantRole(Integer user_id, Integer curr, Integer limit) {
        String sqlQuery = "SELECT r.role_id, r.role_name, r.role_label FROM tb_role r INNER JOIN tb_user_role ur ON r.role_id = ur.role_id WHERE ur.user_id = ? LIMIT ?, ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Role.class), user_id, (curr - 1) * limit, limit);
    }

    /**
    * @Description 已分配的角色总数
    * @Param [user_id]
    * @return java.lang.Long
    **/
    @Override
    public Long findGrantRoleCount(Integer user_id) {
        String sqlQuery = "SELECT * FROM tb_role r INNER JOIN tb_user_role ur ON r.role_id = ur.role_id WHERE ur.user_id = ?";
        return Long.valueOf(jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Role.class), user_id).size());

        /*return (long) hibernateUtil.getSession().createSQLQuery(sqlQuery).setParameter(0, user_id).list().size();*/
    }

    @Override
    public boolean updateUserRole(String type, Integer user_id, Integer role_id) {
        User user = hibernateUtil.getSession().get(User.class, user_id);
        Role role = hibernateUtil.getSession().get(Role.class, role_id);
        if(type.trim().equals("add")){
            return user.getRoles().add(role);
        }
        if(type.trim().equals("remove")){
            return user.getRoles().remove(role);
        }
        return false;
    }

    @Override
    public void updateUserLocation(Integer user_id, Integer locid, String job_id) {
        String hql = "update User set job_id = ?, locid = ? where user_id = ?";
        hibernateUtil.getSession().createQuery(hql).setParameter(0, job_id)
                .setParameter(1, locid).setParameter(2, user_id)
                .executeUpdate();
    }

    @Override
    public Integer updateT(User user) {
        String hql = "update User set user_name = ?, cellphone = ?, email = ? where user_id = ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, user.getUser_name())
                .setParameter(1, user.getCellphone()).setParameter(2, user.getEmail()).setParameter(3, user.getUser_id())
                .executeUpdate();
    }
}
