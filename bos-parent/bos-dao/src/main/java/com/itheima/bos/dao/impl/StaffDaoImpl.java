package com.itheima.bos.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.StaffDao;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

@Repository
public class StaffDaoImpl extends BaseDaoImpl<Staff> implements StaffDao {

	/**
	  * @Description:将PageBean两个属性：total,rows设置完成
	  * @param pageBean 
	*/
	/*public void pageQuery(PageBean pageBean) {
		//使用离线查询对象，查询总记录数
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//使用投影查询总记录数  sql:select count(*) from bc_staff;
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		if(list!=null && list.size()>0){
			Long total = list.get(0);
			pageBean.setTotal(total.intValue());
		}
		//TODO 查询当前页记录时候，离线查询对象没有清除
		detachedCriteria.setProjection(null);
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		int firstResult = (currentPage-1)*pageSize;
		int maxResults = currentPage*pageSize;
		//查询当前页的记录：mysql: select * from bc_staff limit ?,?
		//oracle sql: select * from (select rownum r,t1.* from bc_staff t1) t2 where t2.r>3 and t2.r<=6
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}*/
}
