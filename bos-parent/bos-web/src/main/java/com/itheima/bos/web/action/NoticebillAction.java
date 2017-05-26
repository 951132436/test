package com.itheima.bos.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.NoticebillService;
import com.itheima.bos.service.StaffService;
import com.itheima.crm.Customer;
import com.itheima.crm.CustomerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {

	@Autowired
	private NoticebillService noticebillService;
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private CustomerService proxy;
	
	private String pickdateStr;
	
	
	private String staffId;
	
	private String id; 
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getPickdateStr() {
		return pickdateStr;
	}

	/**
	  * @Description: 根据电话号码查询客户信息
	  * @return
	  * @return String
	 */
	public String findCustomerByTel() {
		Customer customer = proxy.findCustomerByTel(model.getTelephone());
		//转json
		this.java2JSon(customer, new String[]{});
		return NONE;
	}
	
	/**
	  * @Description: 1、保存业务通知单
	  * 			  2、尝试自动分单
	  * @throws Exception
	  * @return String
	 */
	public String save() throws Exception {
		
		if(staffId!=null){			
			Staff staff = staffService.findOne(staffId);
			model = noticebillService.findByid(model.getId());
			model.setStaff(staff);
		}
		noticebillService.save(model);
		return LIST;
	}
	/**
	 * @Description: 查询未自动分配的工单
	 * @throws Exception
	 * @return String
	 */
	public String findnoassociations() throws Exception {
		List<Noticebill> list=noticebillService.findnoassociations();
		List<Noticebill> list2 = new ArrayList<>();
		for (Noticebill noticebill : list) {
			if(noticebill.getPickdate()!=null){
				pickdateStr=new SimpleDateFormat("yyyy-MM-dd").format(noticebill.getPickdate());
				noticebill.setPickdateStr(pickdateStr);
				list2.add(noticebill);
				System.out.println(noticebill);
			}
		}
		String []string=new String[]{
				"workbills","customerId",
				"customerName","arrivecity",
				"num","weight","volume","remark","ordertype","user","staff","pickdate"};
	/*String []string=new String[]{
				"workbills",
				"arrivecity",
				"ordertype","user","pickdate"};*/
		this.java2JSon(list2, string);
		return NONE;
	}
	/**
	 * @Description: 保存未自动分配的工单
	 * @throws Exception
	 * @return String
	 */
	public String findnoticebillById()  {
		System.out.println(model.getId());
		Noticebill noticebill = noticebillService.findByid(model.getId());
		
		
		String []excludes=new String[]{
				"workbills",
				"arrivecity",
				"ordertype","user","pickdate"};
		this.java2JSon(noticebill, excludes);
		
		return NONE;
	}
}
