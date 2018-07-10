package com.github.chronic.dao.user;

import java.util.List;

import com.github.chronic.pojo.User;

public interface UserDao {
	List<User> findUserByNameAndPassword(String user_name, String password_md5);
}
