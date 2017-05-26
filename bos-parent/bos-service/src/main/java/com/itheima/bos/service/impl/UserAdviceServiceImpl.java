package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.UserAdviceDao;
import com.itheima.bos.domain.UserAdvice;
import com.itheima.bos.service.UserAdviceService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class UserAdviceServiceImpl implements UserAdviceService {
	@Autowired	
	private UserAdviceDao userAdviceDao;
	/**
	 * 意见添加
	 */
	public void save(UserAdvice model) {
		userAdviceDao.save(model);
	}
	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		userAdviceDao.pageQuery(pageBean);
	}
	/**
	 * 批量删除意见
	 */
	public void deleteBatch(String ids) {
		String[] strings = ids.split(",");
		for (String adviceId : strings) {
			userAdviceDao.executeQuery("advice_delete", adviceId);
		}
	}
}
