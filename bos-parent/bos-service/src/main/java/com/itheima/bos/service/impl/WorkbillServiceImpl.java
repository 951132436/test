package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.WorkbillDao;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.WorkbillService;
import com.itheima.bos.utils.PageBean;
@Transactional
@Service
public class WorkbillServiceImpl implements WorkbillService {
	@Resource
	private WorkbillDao workbillDao;

	@Override
	public void findByWhereForPage(PageBean pageBean) {
		workbillDao.pageQuery(pageBean);
	}

	@Override
	public void save(Workbill model) {
		workbillDao.save(model);
	}

}
