package com.itheima.bos.web.action;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.StaffService;
import com.itheima.bos.utils.PageBean;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	
	@Autowired
	private StaffService staffService;
	
	
	/**
	  * @Description:取派员保存 
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String save() throws Exception {
		staffService.save(model);
		return LIST;
	}
	
	private int page;//当前页
	private int rows;//每页显示的记录数
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}


	/**
	  * @Description: 分页显示取派员数据：封装PageBean对象
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String pageQuery() throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		if(StringUtils.isNotBlank(model.getName())){
			detachedCriteria.add(Restrictions.like("name", model.getName(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(model.getTelephone())){
			detachedCriteria.add(Restrictions.like("telephone", model.getTelephone(),MatchMode.ANYWHERE));
		}
		pageBean.setDetachedCriteria(detachedCriteria);
		//调用业务层，业务层调用dao层，在dao中将PageBean两个属性设置完成
		staffService.pageQuery(pageBean);
		this.java2JSon(pageBean, new String[]{"currentPage","pageSize","detachedCriteria","decidedzones","workbills","noticebills"});

		return NONE;
	}
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	/**
	  * @Description: 批量删除
	  * @return
	  * @throws Exception
	  * @return String
	 */

	public String deleteBatch() throws Exception {
		//通过代码级别控制权限
		Subject subject = SecurityUtils.getSubject();
		subject.checkPermission("staff_delete");
		staffService.deleteBatch(ids);
		return LIST;
	}
	
	/**
	  * @Description: 修改取派员信息
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String update() throws Exception {
		//先查询
		Staff staff = staffService.findOne(model.getId());
		if(staff!=null){
			staff.setTelephone(model.getTelephone());
			staff.setName(model.getName());
			staff.setHaspda(model.getHaspda());
			staff.setStandard(model.getStandard());
			staff.setStation(model.getStation());
			staffService.update(staff);
		}
		return LIST;
	}
	
	/**
	  * @Description: 查询未删除的取派员的信息
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String listajax() throws Exception {
		List<Staff> list = staffService.findNotDelStaffs();
		this.java2JSon(list, new String[]{"decidedzones","noticebills","workbills"});
		return NONE;
	}
	public String restoreBatch() throws Exception {
		staffService.restoreBatch(ids);
		return LIST;
	}
	
}
