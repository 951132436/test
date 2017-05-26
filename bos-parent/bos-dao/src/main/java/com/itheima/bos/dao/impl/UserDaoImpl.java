package com.itheima.bos.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.UserDao;
import com.itheima.bos.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	

	public User login(String username, String password) {
		String hql = "from User where username = ? and password=?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username, password);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	  * @Description:执行映射文件中命名查询
	  * @param userId
	  * @param password 
	*/
	public void updatePwd(String userId, String password) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		//通过session对象获取到Query对象
		//参数：写映射文件中查询的name的值
		Query query = session.getNamedQuery("user.updatepwd");
		query.setString(0, password);
		query.setString(1, userId);
		//执行查询
		query.executeUpdate();
	}

	/**
	  * @Description:根据用户名查询用户对象
	  * @param username
	  * @return 
	*/
	public User findUserByUsername(String username) {
		String hql = "from User where username = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updatePwdByUsername(User model) {
		//String hql = "update User set password = ? where username = ?";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		//通过session对象获取到Query对象
		//参数：写映射文件中查询的name的值
		Query query = session.getNamedQuery("user.updatepwdByUsername");
		query.setString(0, model.getPassword());
		query.setString(1, model.getUsername());
		//执行查询
		query.executeUpdate();
	}
	


}
