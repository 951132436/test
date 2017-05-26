package com.itheima.bos.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.bos.dao.BaseDao;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	
	//使用模板对象
	//需要注入SessionFactory对象
	@Resource(name="sessionFactory")  
	public void setMySessionFactory(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
	}
	
	private Class clzzzzzzz;
	//在无参构造中将T具体化
	@SuppressWarnings("rawtypes")
	public BaseDaoImpl() {
		//获取当前运行类class
		Class clzz = this.getClass();  //子类CustomerDaoImpl<Customer>
		//获取父类参数化类型
		Type type = clzz.getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		//获取实际类型参数
		Type[] types = pt.getActualTypeArguments();
		clzzzzzzz =  (Class) types[0];
	}
	
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}

	public T findOne(Serializable id) {
		return (T) this.getHibernateTemplate().get(clzzzzzzz, id);
	}

	public List findAll() {
		return this.getHibernateTemplate().find("from "+clzzzzzzz.getSimpleName());
	}

	/**
	  * @Description:
	  * @param queryName
	  * @param objects 
	*/
	public void executeQuery(String queryName, Object... objects) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		//通过session对象获取到Query对象
		//参数：写映射文件中查询的name的值
		Query query = session.getNamedQuery(queryName);
		int index = 0;
		for (Object object : objects) {
			//设置参数
			query.setParameter(index++, object);
		}
		//执行查询
		query.executeUpdate();
	}
	
	/**
	  * @Description:将PageBean两个属性：total,rows设置完成
	  * @param pageBean 
	*/
	public void pageQuery(PageBean pageBean) {
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
		//当多表查询设置封装结果：ROOT_ENTITY:将查询到关联表的信息封装到根实体对应的属性中。
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		int firstResult = (currentPage-1)*pageSize;
		int maxResults = pageSize;
		//查询当前页的记录：mysql: select * from bc_staff limit ?,?
		//oracle sql: select * from (select rownum r,t1.* from bc_staff t1) t2 where t2.r>3 and t2.r<=6
		//firstResult:起始位置索引
		//maxResults：每页显示的记录数
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	/**
	  * @Description:数据库没有记录-保存 如有有-更新
	  * @param t 
	*/
	public void saveOrUpdate(T t) {
		this.getHibernateTemplate().saveOrUpdate(t);
	}

	/**
	  * @Description:根据离线查询对象，查询数据库记录
	  * @param detachedCriteria
	  * @return 
	*/
	public List findByDetachedCriteria(DetachedCriteria detachedCriteria) {
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

}
