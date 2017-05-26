package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

public interface SubareaService {

	void save(Subarea model);

	void pageQuery(PageBean pageBean);

	List<Subarea> findAll();

	List<Subarea> findNotAssociationList();

	List<Subarea> findListByDecidedzoneId(String decidedzoneId);

	List<Object> findListGroupByProvince();

	Subarea findOne(String id);

	void update(Subarea subarea);

	void deleteBatch(String ids);

	void updateOrId(String ids);

}
