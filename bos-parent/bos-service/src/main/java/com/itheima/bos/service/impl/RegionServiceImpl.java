package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.RegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.RegionService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionDao regionDao;

	/**
	  * @Description:批量保存
	  * @param list 
	*/
	public void saveBatch(List<Region> list) {
		for (Region region : list) {
			regionDao.saveOrUpdate(region);
		}
	}

	public void pageQuery(PageBean pageBean) {
			regionDao.pageQuery(pageBean);
	}
	
	public List<Region> findAll() {
		return regionDao.findAll();
	}

	public List<Region> findByQ(String q) {
		return regionDao.findByQ(q);
	}

	@Override
	public void save(Region model) {
		regionDao.save(model);
	}

	@Override
	public void deleteIds(String ids) {
		//先遍历获取到id
		String[] split = ids.split(",");
		for(String regionId :split){
			regionDao.executeQuery("region_delete", regionId);
		}
	}

	@Override
	public Region findById(String id) {
		return regionDao.findOne(id);
	}

	@Override
	public void edit(Region region) {
		regionDao.update(region);
	}

	@Override
	public void search(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}




}
