package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;
import com.itheima.bos.service.FunctionService;
import com.itheima.bos.utils.BosUtils;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
	
	@Autowired
	private FunctionService functionService;

	/**
	  * @Description: 查询所有的权限数据，响应json数据，通过combobox展示
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String listajax() throws Exception {
		List<Function> list = functionService.findAll();
		this.java2JSon(list, new String[]{"parentFunction", "roles", "children"});
		return NONE;
	}
	
	/**
	 * @Description: 查询所有的权限数据，响应json数据，通过combotree展示
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public String listajaxForCombotree() throws Exception {
		List<Function> list = functionService.findListByPidNull();
		this.java2JSon(list, new String[]{"parentFunction", "roles"});
		return NONE;
	}
	
	/**
	 * @Description: 查询所有的权限数据，响应json数据，通过ztree展示
	 * @return  数据 [{id:1,name:'rose',pId:'0'},{id:2,name:'jack',pId:'1'},]
	 * @throws Exception
	 * @return String
	 */
	public String listajaxForZtree() throws Exception {
		List<Function> list = functionService.findListForZtree();
		this.java2JSon(list, new String[]{"parentFunction", "roles", "children"});
		return NONE;
	}
	
	/**
	  * @Description: 保存权限数据
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String save() throws Exception {
		//当页面中没有选择父功能节点 解决MySQL数据库外键字段不能为空字符串
		Function parentFunction = model.getParentFunction();
		if(parentFunction!=null && parentFunction.getId().equals("")){
			model.setParentFunction(null);
		}	
		functionService.save(model);
		return LIST;
	}
	
	/**
	  * @Description: 分页查询
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String pageQuery() throws Exception {
		//通过model模型驱动对象获取提交的页数值
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		functionService.pageQuery(pageBean);
		this.java2JSon(pageBean, new String[]{"currentPage","pageSize","detachedCriteria","parentFunction", "roles", "children"});
		return NONE;
	}
	
	/**
	  * @Description: 查询菜单数据
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String findMenus() throws Exception {
		List<Function> list = functionService.findMenus();
		this.java2JSon(list, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	public String findAjaxByPid() throws Exception {
		//funcioton表中查询 根据制定的id去查询
		List<Function> list = functionService.findAll();
		this.java2JSon(list, new String[]{"roles","children","parentFunction"});
		return NONE;
	}
	private String roleId;
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String findAjaxByRoleid() throws Exception {
		//funcioton表中查询 根据制定的id去查询
		List<Function> list = functionService.findAjaxByRoleid(roleId);
		this.java2JSon(list, new String[]{"roles","children","parentFunction"});
		return NONE;
	}
}
