package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.DecidedzoneDao;
import com.itheima.bos.dao.SubareaDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.DecidedzoneService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {

	@Autowired
	private DecidedzoneDao decidedzoneDao;
	
	@Autowired
	private SubareaDao subareaDao;

	public void save(Decidedzone model, String[] subareaId) {
		//保存定区
		decidedzoneDao.save(model);
		//让分区关联定区:更新分区表中decidedzone_id字段值
		if(subareaId.length>0){
			for (String sId : subareaId) {
				//分区对象
				Subarea subarea = subareaDao.findOne(sId);
				//定区关联分区:由于映射文件中:inverse="true".一方（定区）放弃维护外键关系
//				model.getSubareas().add(subarea);
				//分区关联定区
				subarea.setDecidedzone(model);
			}
		}
	}

	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}
}
