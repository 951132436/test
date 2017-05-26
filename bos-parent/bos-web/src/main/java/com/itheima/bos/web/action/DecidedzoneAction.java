package com.itheima.bos.web.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.service.DecidedzoneService;
import com.itheima.crm.Customer;
import com.itheima.crm.CustomerService;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {

	@Autowired
	private DecidedzoneService decidedzoneService;
	
	@Autowired
	private CustomerService proxy;
	
	
	//通过数组，接收分区的Id
	private String [] subareaId;
	public void setSubareaId(String[] subareaId) {
		this.subareaId = subareaId;
	}

	/**
	  * @Description: 定区的保存，让分区关联定区
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String save() throws Exception {
		decidedzoneService.save(model, subareaId);
		return LIST;
	}
	
	public String pageQuery() throws Exception {
		decidedzoneService.pageQuery(pageBean);
		this.java2JSon(pageBean, new String[]{"workbills","noticebills","subareas", "decidedzones", "currentPage","pageSize","detachedCriteria"});
		return NONE;
	}
	
	
	/**
	  * @Description: 查询未关联到定区的客户记录
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String findListNotAssociation() throws Exception {
		List<Customer> list = proxy.findListNotAssociation();
		//转json
		this.java2JSon(list, new String[]{});
		return NONE;
	}
	
	/**
	 * @Description: 查询关联到定区的客户记录
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public String findListHasAssociation() throws Exception {
		List<Customer> list = proxy.findListHasAssociation(model.getId());
		if(list!=null){
			//转json
			this.java2JSon(list, new String[]{});
		}else{
			String result = "{\"total\":0,\"rows\":[]}";
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(result);
		}
		return NONE;
	}
	
	//通过属性驱动获取页面提交的客户id
	private List<String> customerIds;
	public void setCustomerIds(List<String> customerIds) {
		this.customerIds = customerIds;
	}

	/**
	  * @Description: 客户管理定区
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String associationDecidedzon() throws Exception {
		proxy.associationDecidedzon(model.getId(), customerIds);
		return LIST;
	}
}
