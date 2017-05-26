package com.itheima.bos.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;

public class BosUtils {

	/**
	  * @Description: 获取HttpSession
	  * @return
	  * @return HttpSession
	 */
	public static HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	}
	
	/**
	  * @Description: 获取到当前用户对象
	  * @return
	  * @return User
	 */
	public static User getLoginUser(){
		return (User) getSession().getAttribute("loginUser");
	}
}
