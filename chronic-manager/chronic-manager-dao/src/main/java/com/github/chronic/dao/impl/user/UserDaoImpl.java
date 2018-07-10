package com.github.chronic.dao.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.chronic.dao.impl.util.HibernateUtil;
import com.github.chronic.dao.user.UserDao;
import com.github.chronic.pojo.User;

@Repository
public class UserDaoImpl implements UserDao {

   @Autowired
   private HibernateUtil hibernateUtil;

   @Override
   public List<User> findUserByNameAndPassword(String user_name, String password_md5) {
       String hql = "from User where user_name = ? and password_md5 = ?";
       return hibernateUtil.getSession().createQuery(hql)
               .setParameter(0, user_name)
               .setParameter(1, password_md5).list();
   }
}

