package com.itheima.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.SubareaDao;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

@Repository
public class SubareaDaoImpl extends BaseDaoImpl<Subarea> implements SubareaDao {

	/**
	  * @Description:
	  * sql:select r.province,count(*)
				from bc_subarea s left join bc_region r on s.region_id = r.id
				group by r.province;
	  * @return 
	*/
	public List<Object> findListGroupByProvince() {
		String hql = "select r.province,count(*) from Subarea s left join s.region r group by r.province";
		return (List<Object>) this.getHibernateTemplate().find(hql);
	}

	

}