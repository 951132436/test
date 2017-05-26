package com.itheima.bos.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.DecidedzoneDao;
import com.itheima.bos.dao.NoticebillDao;
import com.itheima.bos.dao.WorkbillDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.NoticebillService;
import com.itheima.bos.utils.BosUtils;
import com.itheima.bos.utils.MsgUtils;
import com.itheima.crm.CustomerService;

@Service
@Transactional
public class NoticebillServiceImpl implements NoticebillService {

	@Autowired
	private NoticebillDao noticebillDao;
	
	@Autowired
	private CustomerService proxy; 
	
	@Autowired
	private DecidedzoneDao decidedzoneDao;
	
	@Autowired
	private WorkbillDao workbillDao;
	
	//用户名
	private static String uid = "sbd521";
	//接口安全秘钥
	private static String key = "53679f1dfd1b9a15d349";
		
	//手机号码，多个号码如13800000000,13800000001,13800000002
	private static String smsMob;
		
	//短信内容
	private static String smsText;
	
	/**
	  * @Description:1、保存业务通知单
	  * 			    2、尝试自动分单
	  * @param model 
	*/
	public void save(Noticebill model) {
		noticebillDao.save(model);
		User user = BosUtils.getLoginUser();
		model.setUser(user);
		//尝试自动分单
		//1、远程调用crm:根据取件地址查询客户的定区
		String decidedzoneId = proxy.findDecidedzoneIdByAddress(model.getPickaddress());
		if(StringUtils.isNotBlank(decidedzoneId)){
			//根据定区ID查询定区对象
			Decidedzone decidedzone = decidedzoneDao.findOne(decidedzoneId);
			//匹配到一个快递员，自动分单
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);
			//设置为自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			
			//给快递员产生工单
			Workbill workbill = new Workbill();
			workbill.setNoticebill(model);
			workbill.setType(Workbill.TYPE_01);
			workbill.setPickstate(Workbill.PICKSTATE_NO);
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));
			workbill.setAttachbilltimes(0);
			workbill.setRemark(model.getRemark());
			workbill.setStaff(staff);
			//保存工单
			workbillDao.save(workbill);
			//调用短信平台，给快递员发送短信，通知上门取件
			//UTF发送
			try {
				//给取派员发短信
				smsMob=staff.getTelephone();
				System.out.println(smsMob);
				//取件地址
				smsText=staff.getName()+"你好:请速去"+model.getPickaddress()+"取件!!!";
				MsgUtils.sendMsgGbk(uid, key, smsMob,smsText);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}else{
			//设置为人工分单
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
		
	}

	@Override
	public Noticebill findByid(String id) {
		
		return noticebillDao.findOne(id);
	}

	@Override
	public List<Noticebill> findnoassociations() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Noticebill.class);
		detachedCriteria.add(Restrictions.isNull("staff"));
		detachedCriteria.add(Restrictions.isNotNull("id"));
		return noticebillDao.findByDetachedCriteria(detachedCriteria);
	}
}
