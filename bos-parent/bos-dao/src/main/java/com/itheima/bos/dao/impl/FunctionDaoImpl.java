package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.FunctionDao;
import com.itheima.bos.domain.Function;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao {

	/**
	  * @Description:根据用户ID查询用户的权限
	  * @param id
	  * sql:select af.*
			from t_user u left join user_role r on r.user_id = u.id left join auth_role ar on r.role_id = ar.id
			left join role_function rf on rf.role_id = ar.id left join auth_function af on rf.function_id = af.id
			where u.id = '8a7e81775be61f62015be62995d30000'
	  * @return 
	*/
	public List<Function> findFunctinsByUserId(String id) {
		String hql = "select distinct f from Function f left join f.roles r left join r.users u where u.id = ?";
		return (List<Function>) this.getHibernateTemplate().find(hql, id);
	}

	/**
	  * @Description:查询所有的菜单数据
	  * @return 
	*/
	public List<Function> findAllMenus() {
		String hql = "from Function f where f.generatemenu = '1' order by f.zindex";
		return (List<Function>) this.getHibernateTemplate().find(hql);
	}

	/**
	  * @Description:根据用户的id查询对应的菜单
	  * @param id
	  * select af.*
		from t_user u left join user_role r on r.user_id = u.id left join auth_role ar on r.role_id = ar.id
		left join role_function rf on rf.role_id = ar.id left join auth_function af on rf.function_id = af.id
		where u.id = '8a7e81775be61f62015be62995d30000' and af.generatemenu = '1' order by af.zindex
	  * @return 
	*/
	public List<Function> findMenusByUserId(String userId) {
		String hql = "select distinct f from Function f left join f.roles r left join r.users u where u.id = ? "
				+ "and f.generatemenu = '1' order by f.zindex";
		return (List<Function>) this.getHibernateTemplate().find(hql, userId);
	}

	@Override
	public List<Function> findAjaxByRoleid(String roleId) {
		String hql = "select distinct f from Function f left join f.roles r  where r.id = ?";
		return (List<Function>) this.getHibernateTemplate().find(hql, roleId);
	}

}
