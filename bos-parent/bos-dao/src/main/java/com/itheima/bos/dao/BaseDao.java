package com.itheima.bos.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.utils.PageBean;

public interface BaseDao<T> {

	public void save(T t);
	public void update(T t);
	public void saveOrUpdate(T t);
	public void delete(T t);
	
	public T findOne(Serializable id);
	public List findAll();
	public List findByDetachedCriteria(DetachedCriteria detachedCriteria);
	
	//执行命名查询
	public void executeQuery(String queryName, Object...objects);
	
	//分页查询
	void pageQuery(PageBean pageBean);
	
}
