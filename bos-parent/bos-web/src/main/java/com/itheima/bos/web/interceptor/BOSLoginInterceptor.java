package com.itheima.bos.web.interceptor;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BosUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class BOSLoginInterceptor extends MethodFilterInterceptor {

	/**
	  * @Description:判断用户是否登陆
	  * @param arg0
	  * @return
	  * @throws Exception 
	*/
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//获取当前用户
		User user = BosUtils.getLoginUser();
		if(user==null){
			//跳转到登陆页面
			return "login";
		}
		//放行
		return invocation.invoke();
	}

}
