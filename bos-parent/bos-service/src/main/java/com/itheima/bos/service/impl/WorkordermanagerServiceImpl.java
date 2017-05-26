package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.WorkordermanagerDao;
import com.itheima.bos.domain.Workordermanager;
import com.itheima.bos.service.WorkordermanagerService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class WorkordermanagerServiceImpl implements WorkordermanagerService {

	@Autowired
	private WorkordermanagerDao workordermanagerDao;

	public void save(Workordermanager model) {
		workordermanagerDao.save(model);
	}

	@Override
	public void batchImport(List<Workordermanager> list) {
		for (Workordermanager workordermanager : list) {
			workordermanagerDao.save(workordermanager);
		}
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		workordermanagerDao.pageQuery(pageBean);
	}
}
