package com.itheima.bos.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.domain.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	@Override
	public void saveOrUpdateOrDelete(Role model) {
		// TODO Auto-generated method stub
				this.saveOrUpdate(model);
				//更新中间表
				String sql="delete from role_function where role_id ="+model.getId();
				Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
				session.createSQLQuery(sql);
		
	}


}
