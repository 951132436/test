package com.itheima.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.dao.FunctionDao;
import com.itheima.bos.dao.UserDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.FunctionService;
import com.itheima.bos.service.UserService;

/**
 * @Description: 自定义的realm
 * @Title: BosRealm.java
 * @date 2017年5月5日 下午12:02:55
 */

public class BosRealm extends AuthorizingRealm {
	
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FunctionDao functionDao;
	@Autowired
	private UserService service;
	
	
	//获取用户的认证信息
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("执行了用户认证操作");
		//shiro框架验证用户名密码方式：
		//1、根据用户名查询数据中用户真实密码
		UsernamePasswordToken userpwdToken =  (UsernamePasswordToken) token;
		//页面上输入用户名
		String username = userpwdToken.getUsername();
		User user = service.findUserByUsername(username);
		if(user == null){
			return null;  //框架抛出异常
		}
		//2、框架自己做密码比对(对页面输入的密码跟查询到真实密码比对)
		//参数1：主角,将来就可以从subject对象中获取用户对象
		//参数2：数据库中真实密码用于比对密码  如果比对失败：框架也会抛出异常
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		return info;
	}

	//对用户进行授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		List<Function> list = null;
		System.out.println("执行了用户授权操作");
		//简单授权信息
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//给用户授权
		//TODO 将来根据登陆用户查询数据库对应的权限
//		info.addStringPermission("staff_page");
		//获取到当前的用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		//admin用户系统内置账号，拥有所有的权限
		if(user.getUsername().equals("admin")){
			list = functionDao.findAll();
		}else{
			//其他用户根据用户ID进行查询
			list = functionDao.findFunctinsByUserId(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getCode());
			System.out.println(function.getCode());
		}
		return info;
	}

}
