package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.FunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.FunctionService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private FunctionDao functionDao;

	public List<Function> findAll() {
		return functionDao.findAll();
	}

	public void save(Function model) {
		functionDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	/**
	  * @Description:查询pid是null的权限数据
	  * @return 
	*/
	public List<Function> findListByPidNull() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
		detachedCriteria.add(Restrictions.isNull("parentFunction"));
		return functionDao.findByDetachedCriteria(detachedCriteria);
	}

	public List<Function> findListForZtree() {
		return functionDao.findAll();
	}

	/**
	  * @Description:根据用户不同查询不同菜单
	  * 1、admin用户显示所有的菜单
	  * 2、其他用户根据用户ID查询
	  * @return 
	*/
	public List<Function> findMenus() {
		List<Function> list = null;
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if(user.getUsername().equals("admin")){
			list =  functionDao.findAllMenus();
		}else{
			list = functionDao.findMenusByUserId(user.getId());
		}
		return list;
	}
	
	@Override
	public List<Function> findByWhere() {
		return functionDao.findAll();
	}

	@Override
	public List<Function> findAjaxByRoleid(String roleId) {
		return functionDao.findAjaxByRoleid(roleId);
	}
}
