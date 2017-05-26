package com.itheima.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Log;
import com.itheima.bos.service.LogService;
import com.itheima.bos.utils.PageBean;
@Controller(value="logAction")
@Scope("prototype")
public class LogAction extends BaseAction<Log>{
	/**
	 * 注入service
	 * 
	 */
	@Autowired
	private LogService service;
	
	
	//方法
	public String pageQuery() throws Exception {
		//分页查询
		service.pageQuery(pageBean);
		this.java2JSon(pageBean, new String[]{"currentPage","pageSize","detachedCriteria"});
		return NONE;
	}
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}

	//删除
	public String shanBatch() throws Exception {
		service.shanBatch(ids);
		return "list";
	}
	
}
