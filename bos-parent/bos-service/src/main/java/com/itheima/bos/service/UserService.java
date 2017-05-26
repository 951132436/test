package com.itheima.bos.service;

import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.PageBean;
@Transactional
public interface UserService {

	User login(String username, String password);

	void updatePwd(String id, String password);

	void save(User model, String[] rolesIds);

	void pageQuery(PageBean pageBean);

	User findUserByUsername(String username);

	void updatePwdByUsername(User model);

}
