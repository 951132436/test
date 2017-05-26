package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Workordermanager;
import com.itheima.bos.utils.PageBean;

public interface WorkordermanagerService {

	void save(Workordermanager model);

	void batchImport(List<Workordermanager> list);

	void pageQuery(PageBean pageBean);

}
