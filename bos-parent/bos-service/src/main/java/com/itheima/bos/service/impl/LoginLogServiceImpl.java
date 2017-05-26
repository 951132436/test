package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.LoginLogDao;
import com.itheima.bos.domain.LoginLog;
import com.itheima.bos.service.LoginLogService;

@Transactional
@Service(value="loginLogServiceImpl")
public class LoginLogServiceImpl implements LoginLogService {
	/**
	 * 依赖注入
	 */
	@Resource(name="loginLogDaoImpl")
	private LoginLogDao dao;
	/**
	 * 保存数据 用
	 */
	@Override
	public void save(LoginLog log) {
		/*String currentTime = log.getCurrentTime();
		String remoteHost = log.getRemoteHost();
		String username = log.getUsername();*/
		/*dao.executeQuery("log.save", username,remoteHost,currentTime);*/
		dao.save(log);
		
	}
	
	//添加的方法
	

}
