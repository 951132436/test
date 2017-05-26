package com.itheima.bos.web.action;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.UserAdvice;
import com.itheima.bos.service.UserAdviceService;
import com.itheima.bos.utils.BosUtils;
@Controller
@Scope("prototype")
public class UserAdviceAction extends BaseAction<UserAdvice> {
	@Autowired
	private UserAdviceService userAdviceService;
	//用户意见提交
	
	public String saveAdvice() throws Exception {
		model.setFromuser(BosUtils.getLoginUser().getUsername());
		model.setAdviceContent(model.getAdviceContent());
		Date adviceTime =adviceTime();
		model.setAdviceTime(adviceTime);
		String ip=getIp();
		model.setUserIp(ip);
		userAdviceService.save(model);
		return NONE;
	}
	
	/**
	 * 意见查询
	 */
	public String pageQuery() throws Exception {
		userAdviceService.pageQuery(pageBean);
		this.java2JSon(pageBean, new String[]{});
		return NONE;
	}
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	/**
	  * @Description: 批量删除意见
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String deleteBatch() throws Exception {
		//通过代码级别控制权限
		Subject subject = SecurityUtils.getSubject();
		subject.checkPermission("advice_delete");
		userAdviceService.deleteBatch(ids);
		return LIST;
	}
		
	private static Date adviceTime() throws ParseException{
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		String date =dateFormat.format(now); 
		return dateFormat.parse(date);		
	}
	
	public String getIp() {
		InetAddress netAddress = getInetAddress();  
		if(null==netAddress){	
			return null;  
		}  
		return netAddress.getHostAddress(); //get the ip address  
	}
	
	/**
	 * 获取本地主机对象
	 * @return
	 */
	public static InetAddress getInetAddress(){  
        try{  
            return InetAddress.getLocalHost();  
        }catch(UnknownHostException e){  
           e.printStackTrace();
        }  
        return null;  
	}
}
