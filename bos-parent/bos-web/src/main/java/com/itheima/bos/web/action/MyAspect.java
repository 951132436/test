package com.itheima.bos.web.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.domain.LoginLog;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.LoginLogService;
import com.itheima.bos.service.UserService;


public class MyAspect {
	//属性注入logservie
	@Autowired
	private LoginLogService logService;
	//记录日志的方法
	public void writeLog(){
		System.out.println("登录成功,登录时间...,远程主机...,用户名称...");
		//获取远程登录的主机ip
		String host = ServletActionContext.getRequest().getRemoteHost();
		//获取当前登录的用户 注入user
		String username = ServletActionContext.getRequest().getParameter("username");
		//获取系统的时间
		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
		//存到数据库中 创建一个表 
		//创建一个service 创建一个dao 注入service 然后调用dao 插入数据
		LoginLog log = new LoginLog(username, host, currentTime);
		logService.save(log);
		System.out.println("日志记录完成");
		
	}

}
