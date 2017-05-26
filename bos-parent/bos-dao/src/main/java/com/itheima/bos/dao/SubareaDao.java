package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.domain.Subarea;

public interface SubareaDao extends BaseDao<Subarea> {

	List<Object> findListGroupByProvince();


}
