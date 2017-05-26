package com.itheima.bos.web.action;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.User;
import com.itheima.bos.service.UserService;
import com.itheima.bos.utils.BosUtils;
import com.itheima.bos.utils.MD5Utils;

@Controller
@Scope("prototype")  //通过注解创建对象首字母小写，规范：驼峰命名法   userAction
public class UserAction extends BaseAction<User> {

	@Autowired
	private UserService userService;
	
	
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	private String mobileNum;
	

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	/**
	  * @Description: 用户登录
	  * 1、接收页面提交参数
	  * 2、调用业务层查询数据库
	  * 3、存用户信息到session
	  * 4、跳转页面
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String login() throws Exception {
		//判断验证码是否正确
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//判断验证码\用户名，密码是否为空
//		if(StringUtils.isNotBlank(checkcode)){
//			if(checkcode.equals(code)){
				//验证码正确
				//使用shiro框架认证
				//1、获取subject对象
				Subject subject = SecurityUtils.getSubject(); //当前“用户”对象，状态：未认证状态
				//创建用户名密码令牌：设置页面上输入的用户名密码。
				AuthenticationToken token = new UsernamePasswordToken(model.getUsername(), MD5Utils.md5(model.getPassword()));
				//调用用户对象的login方法。调用安全管理器--安全管理器调用realm中获取认证信息的方法
				try {
					subject.login(token);
				} catch (Exception e) {
					//TODO 根据异常类型判断 用户名，密码错误
//					if(e instanceof IncorrectCredentialsException){
						//设置提示信息
						this.addActionError("用户名或密码错误");
//					}
					return LOGIN;
				}
				//shiro框架认证通过后，从用户对象中获取主角信息
				User user = (User) subject.getPrincipal();
				//将用户的信息存入session
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return INDEX;
//			}else{
//				//提示错误
//				this.addActionError("验证码输入错误");
//				return LOGIN;
//			}
//		}else{
//			//验证码为空
//			//提示错误
//			this.addActionError("验证码不能为空");
//			return LOGIN;
//		}
	}
	
	/**
	 * @Description: 用户登录
	 * 1、接收页面提交参数
	 * 2、调用业务层查询数据库
	 * 3、存用户信息到session
	 * 4、跳转页面
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public String login1() throws Exception {
		//判断验证码是否正确
		String code = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//判断验证码\用户名，密码是否为空
//		if(StringUtils.isNotBlank(checkcode)){
//			if(checkcode.equals(code)){
		//验证码正确
		User user = userService.login(model.getUsername(), model.getPassword());
		if(user!=null){
			//登陆成功，跳首页
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return INDEX;
		}else{
			//提示错误
			this.addActionError("用户名或者密码错误");
			return LOGIN;
		}
//			}else{
//				//提示错误
//				this.addActionError("验证码输入错误");
//				return LOGIN;
//			}
//		}else{
//			//验证码为空
//			//提示错误
//			this.addActionError("验证码不能为空");
//			return LOGIN;
//		}
	}
	
	/**
	  * @Description: 退出系统
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String logout() throws Exception {
		//清空session
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	
	
	/**
	  * @Description: 修改当前登陆用户密码
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String updatePwd() throws Exception {
		String flag = "1";
		//调用业务层修改密码
		User user = BosUtils.getLoginUser();
		user.setPassword(model.getPassword());
		try {
			userService.updatePwd(user.getId(), model.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			flag = "0";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(flag);
		return NONE;
	}
	/**
	 * @Description: 根据用户名查找用户
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public String findUserByUsername() throws Exception {
		String flag = "0";
		User user= userService.findUserByUsername(model.getUsername());
		if(user!=null){
			flag="1";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().write(flag);
		return NONE;
	}
	
	/**
	 * @Description: 忘记密码后：根据用户名修改用户密码
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public String updatePwdByUsername() throws Exception {
		userService.updatePwdByUsername(model);
		return NONE;
	}
	
	private String[] rolesIds;
	public void setRolesIds(String[] rolesIds) {
		this.rolesIds = rolesIds;
	}

	/**
	  * @Description: 保存用户、用户关联角色
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String save() throws Exception {
		userService.save(model, rolesIds);
		return LIST;
	}
	
	public String pageQuery() throws Exception {
		userService.pageQuery(pageBean);
		this.java2JSon(pageBean, new String[]{"currentPage","pageSize","detachedCriteria", "roles", "noticebills"});
		return NONE;
	}
	/**
	 * 发送短信 SendMessage
	 */
	public String sendMessage() throws Exception {
		//调用发送短信的接口 将发送的信息保存到session中 来一个随机数
		SendMsg_webchinese.sendMessage();
		System.out.println("短信发送成功");
		return NONE;
	}
	
	public String saveMessage() throws Exception {
		//获取信息 和session 中的密码进行比对.
		String attribute = (String) ServletActionContext.getRequest().getSession().getAttribute("mobileRandom");
		//进行session 的对比好吧
		if(attribute.equals(mobileNum)){
			//然后return "1";
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().print("1");  
			
		}else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().print("0");
			
		}
		return NONE;
	}
}
