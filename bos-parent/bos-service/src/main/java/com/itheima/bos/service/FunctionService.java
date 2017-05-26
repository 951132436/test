package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Function;
import com.itheima.bos.utils.PageBean;

public interface FunctionService {

	List<Function> findAll();

	void save(Function model);

	void pageQuery(PageBean pageBean);

	List<Function> findListByPidNull();

	List<Function> findListForZtree();

	List<Function> findMenus();

	List<Function> findByWhere();

	List<Function> findAjaxByRoleid(String roleId);

}
