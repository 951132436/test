package com.itheima.bos.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

public interface StaffService {

	void save(Staff model);

	void pageQuery(PageBean pageBean);

	void deleteBatch(String ids);

	Staff findOne(String id);

	void update(Staff staff);

	List<Staff> findNotDelStaffs();

	void restoreBatch(String ids);

}
