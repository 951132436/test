package com.itheima.bos.service;

import com.itheima.bos.domain.Log;
import com.itheima.bos.utils.PageBean;

public interface LogService {

	void insertLog(Log log);

	void pageQuery(PageBean pageBean);

	void shanBatch(String ids);

}
