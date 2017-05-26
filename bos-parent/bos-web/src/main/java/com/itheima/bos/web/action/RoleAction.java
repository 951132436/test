package com.itheima.bos.web.action;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Role;
import com.itheima.bos.service.RoleService;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	@Autowired
	private RoleService roleService;
	
	
	//通过属性驱动获取页面提交权限的id
	private String functionIds;
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	
	/**
	  * @Description: 修改角色，角色关联权限（向角色权限关系表中添加记录）
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String update() throws Exception {
		roleService.update(model, functionIds);
		return LIST;
	}
	/**
	  * @Description: 获取当前修改的对象的数据，跳转页面并回显
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String toEdit() throws Exception {
		String roleId = model.getId();
		Role role = roleService.toEdit(roleId);
		ServletActionContext.getRequest().getSession().setAttribute("role", role);
		return "edit";
	}
	/**
	  * @Description: 保存角色，角色关联权限（向角色权限关系表中添加记录）
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String save() throws Exception {
		roleService.save(model, functionIds);
		return LIST;
	}
	
	public String pageQuery() throws Exception {
		roleService.pageQuery(pageBean);
		this.java2JSon(pageBean, new String[]{"currentPage","pageSize","detachedCriteria", "functions", "users"});
		return NONE;
	}
	
	/**
	  * @Description: 查询权限数据
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String listajax() throws Exception {
		List<Role> list = roleService.findAll();
		this.java2JSon(list, new String[]{"functions", "users"});
		return NONE;
	}
	/**
	 *  editRole 
	 */
	public String editRole() throws Exception {
		//更改关联的信息
		roleService.edit(model,functionIds);
		return "list";
	}
	
}
