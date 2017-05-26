package com.itheima.bos.service;

import com.itheima.bos.domain.Workbill;
import com.itheima.bos.utils.PageBean;

public interface WorkbillService {

	void findByWhereForPage(PageBean pageBean);

	void save(Workbill model);


}
