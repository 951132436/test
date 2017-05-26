package com.itheima.bos.dao;

import com.itheima.bos.domain.User;

public interface UserDao extends BaseDao<User> {

	User login(String username, String password);

	void updatePwd(String userId, String password);

	User findUserByUsername(String username);

	void updatePwdByUsername(User model);

}
