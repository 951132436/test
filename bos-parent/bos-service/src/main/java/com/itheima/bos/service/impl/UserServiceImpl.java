package com.itheima.bos.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.UserDao;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.UserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.utils.PageBean;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public User login(String username, String password) {
		//使用MD5加密
		password = MD5Utils.md5(password);
		return userDao.login(username, password);
	}

	public void updatePwd(String userId, String password) {
		password = MD5Utils.md5(password);
		userDao.executeQuery("user.updatepwd", password, userId);
	}

	/**
	  * @Description:用户关联角色
	  * @param model
	  * @param rolesIds 
	*/
	
	
	public void save(User model, String[] rolesIds) {
		model.setPassword(MD5Utils.md5(model.getPassword()));
		userDao.save(model);
		if(rolesIds.length>0){
			for (String roleId : rolesIds) {
				Role role = new Role(roleId);
				model.getRoles().add(role);//让用户管理角色
			}
		}
	}

	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

	@Override
	public User findUserByUsername(String username) {
		User user = userDao.findUserByUsername(username);
		return user;
	}

	@Override
	public void updatePwdByUsername(User model) {
		String password = MD5Utils.md5(model.getPassword());
		model.setPassword(password);
		userDao.updatePwdByUsername(model);
	}

}
