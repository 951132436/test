package com.itheima.bos.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;

public interface RegionService {

	void saveBatch(List<Region> list);

	void pageQuery(PageBean pageBean);

	List<Region> findAll();

	List<Region> findByQ(String q);

	void save(Region model);

	void deleteIds(String ids);

	Region findById(String id);

	void edit(Region region);

	void search(PageBean pageBean);

	

	

	

}
