package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.FunctionDao;
import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.RoleService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	// 方式一：查询数据库获取权限对象
	// @Autowired
	// private FunctionDao functionDao;
	// Function function = functionDao.findOne(functionId);
	
	/**
	 * 修改角色 2让角色关联权限
	 */
	public void update(Role model, String functionIds) {
		roleDao.saveOrUpdate(model);
		// 角色关联权限
		if (StringUtils.isNotBlank(functionIds)) {
			String[] ids = functionIds.split(",");
			for (String functionId : ids) {
				// 手动构建一个权限对象，状态：托管态（ID，session）
				Function function = new Function(functionId);
				// 通过角色关联权限
				model.getFunctions().add(function);
				// 由于Function已经放弃维护外键关系
				// function.getRoles().add(model);
			}
		}
		
	}


	/**
	 * @Description:获取要修改的角色的数据
	 * @param model
	 * @param functionIds
	 */
	public Role toEdit(String roleId) {
		return roleDao.findOne(roleId);
	}
	
	/**
	 * @Description:1、保存角色 2、让角色关联权限
	 * @param model
	 * @param functionIds
	 */
	public void save(Role model, String functionIds) {
		roleDao.save(model);
		// 角色关联权限
		if (StringUtils.isNotBlank(functionIds)) {
			String[] ids = functionIds.split(",");
			for (String functionId : ids) {
				// 手动构建一个权限对象，状态：托管态（ID，session）
				Function function = new Function(functionId);
				// 通过角色关联权限
				model.getFunctions().add(function);
				// 由于Function已经放弃维护外键关系
				// function.getRoles().add(model);
			}
		}
	}

	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	public List<Role> findAll() {
		return roleDao.findAll();
	}
	/**
	 * 角色修改
	 */
	@Override
	public void edit(Role model, String functionIds) {
		//级联删除 将原来中间表中的数据清空.
		roleDao.saveOrUpdateOrDelete(model);
				String[] split = functionIds.split(",");
				for (String string : split) {
					Function function = new Function(string);
					model.getFunctions().add(function);
					
					
				}
	}
}
