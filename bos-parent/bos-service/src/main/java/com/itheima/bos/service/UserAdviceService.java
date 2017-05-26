package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.UserAdvice;
import com.itheima.bos.utils.PageBean;

public interface UserAdviceService {

	void save(UserAdvice model);

	void pageQuery(PageBean pageBean);

	void deleteBatch(String ids);
}
