package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.StaffDao;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.StaffService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
	
	@Autowired
	private StaffDao staffDao;

	public void save(Staff model) {
		staffDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}

	/**
	  * @Description:
	  * @param ids 
	*/
	public void deleteBatch(String ids) {
		String[] strings = ids.split(",");
		for (String staffId : strings) {
			//调用dao修改取派员记录
			staffDao.executeQuery("staff_delete", staffId);
		}
	}

	public Staff findOne(String id) {
		return staffDao.findOne(id);
	}

	public void update(Staff staff) {
		staffDao.update(staff);
	}

	/**
	  * @Description:
	  * @return 查询未删除的取派员的信
	*/
	public List<Staff> findNotDelStaffs() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		//ne not equels
//		detachedCriteria.add(Restrictions.ne("deltag", "1"));
		return staffDao.findByDetachedCriteria(detachedCriteria);
	}
	public void restoreBatch(String ids) {
		String[] strings = ids.split(",");
		for (String id : strings) {
			staffDao.executeQuery("staff_restire", id);
		}
	}

}
