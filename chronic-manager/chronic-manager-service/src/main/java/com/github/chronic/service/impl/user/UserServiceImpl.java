package com.github.chronic.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.github.chronic.dao.user.UserDao;
import com.github.chronic.pojo.User;
import com.github.chronic.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public User login(User user) {
		// MD5加密
		user.setPassword_md5(DigestUtils.md5DigestAsHex(user.getPassword_md5().getBytes()));
		List<User> list = userDao.findUserByNameAndPassword(user.getUser_name(), user.getPassword_md5());
		return list.size() == 1 ? list.get(0) : null;
	}

}
