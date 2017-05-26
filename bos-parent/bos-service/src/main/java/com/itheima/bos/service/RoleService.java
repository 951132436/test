package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Role;
import com.itheima.bos.utils.PageBean;

public interface RoleService {

	void save(Role model, String functionIds);

	void pageQuery(PageBean pageBean);

	List<Role> findAll();

	Role toEdit(String roleId);

	void update(Role model, String functionIds);

	void edit(Role model, String functionIds);
}
