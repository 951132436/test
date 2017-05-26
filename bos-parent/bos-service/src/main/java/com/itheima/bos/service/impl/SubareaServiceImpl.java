package com.itheima.bos.service.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.DecidedzoneDao;
import com.itheima.bos.dao.SubareaDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {

	@Autowired
	private SubareaDao subareaDao;
	
//	@Autowired
//	private DecidedzoneDao decidedzoneDao;
	
	/**
	 * 分区批量删除
	 */
	public void deleteBatch(String ids) {
		String[] strings = ids.split(",");
		for (String subareaId : strings) {
			//根据ID获得分区
			Subarea subarea = subareaDao.findOne(subareaId);
			//调用dao删除分区员
			subareaDao.delete(subarea);
		}
	}
	/**
	 * 分区保存
	 */
	public void save(Subarea model) {
		subareaDao.save(model);
	}
	/**
	 * 分区分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}
	/**
	 * 分区查询左右
	 */
	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}

	/**
	  * @Description:
	  * sql:select * from bc_subarea s where s.decidedzone_id is null; 
	  * @return 
	*/
	public List<Subarea> findNotAssociationList() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//查询数据库中decidedzone_id为null
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByDetachedCriteria(detachedCriteria);
	}

	/**
	  * @Description:根据定区ID查询分区数据
	  * @param decidedzoneId
	  * @return 
	*/
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		//		Decidedzone decidedzone = decidedzoneDao.findOne(decidedzoneId);
//		Set subareas = decidedzone.getSubareas();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		return subareaDao.findByDetachedCriteria(detachedCriteria);
	}

	public List<Object> findListGroupByProvince() {
		return subareaDao.findListGroupByProvince();
	}

	public Subarea findOne(String id) {
		return subareaDao.findOne(id);
	}

	public void update(Subarea subarea) {
		subareaDao.update(subarea);
	}
	@Override
	public void updateOrId(String ids) {
		String[] split = ids.split(",");
		for (String id : split) {
			subareaDao.executeQuery("region2_delete", id);
			
		}
	}

	
}
