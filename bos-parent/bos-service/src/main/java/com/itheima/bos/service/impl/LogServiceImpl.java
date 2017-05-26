package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.LogDao;
import com.itheima.bos.domain.Log;
import com.itheima.bos.service.LogService;
import com.itheima.bos.utils.PageBean;
@Transactional
@Service(value="logServiceImpl")
public class LogServiceImpl implements LogService {
	/**
	 * 注入dao LogDaoImpl
	 */
	@Autowired
	private LogDao LogDaoImpl;
	//向 数据表总插入数据

	@Override
	public void insertLog(Log log) {
		log.setRemoteHost("0.0.0.1");
		LogDaoImpl.save(log);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		LogDaoImpl.pageQuery(pageBean);
	}
	
	@Override
	public void shanBatch(String ids) {
		String[] logId = ids.split(",");
		for (String id : logId) {
			LogDaoImpl.executeQuery("log_delete", id);
		}
	}
	
	
	
	
}
