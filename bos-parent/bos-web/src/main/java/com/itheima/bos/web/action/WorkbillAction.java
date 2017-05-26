package com.itheima.bos.web.action;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.WorkbillService;

@Controller
@Scope("prototype")
public class WorkbillAction extends BaseAction<Workbill> {
	@Resource
	private WorkbillService workbillService;
	
	/**
	 * 分页查找工单数据
	 * @return
	 * @throws Exception
	 */
	public String findByWhereForPage() throws Exception {
		workbillService.findByWhereForPage(pageBean);
		java2JSon(pageBean, new String[]{"pageNumber","pageSize","detachedCriteria","decidedzones","workbills","noticebills","user"});
		return NONE;
	}
	
}
