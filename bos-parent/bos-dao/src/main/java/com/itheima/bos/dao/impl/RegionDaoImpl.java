package com.itheima.bos.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.RegionDao;
import com.itheima.bos.domain.Region;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao {

	/**
	  * @Description:根据输入条件，模糊查询
	  * @param q
	  * @return 
	*/
	public List<Region> findByQ(String q) {
		String hql = "from Region r where r.province like ? or r.city like ? or r.district like ? or"
				+ " r.citycode like ? or r.shortcode like ?";
		return (List<Region>) this.getHibernateTemplate().find(hql, "%"+q+"%" , "%"+q+"%", "%"+q+"%", "%"+q+"%", "%"+q+"%");
	}





}
