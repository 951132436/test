package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Noticebill;

public interface NoticebillService {

	void save(Noticebill model);

	Noticebill findByid(String id);

	List<Noticebill> findnoassociations();

}
