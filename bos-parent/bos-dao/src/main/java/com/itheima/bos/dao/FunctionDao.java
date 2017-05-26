package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.domain.Function;

public interface FunctionDao extends BaseDao<Function> {

	List<Function> findFunctinsByUserId(String id);

	List<Function> findAllMenus();

	List<Function> findMenusByUserId(String userId);

	List<Function> findAjaxByRoleid(String roleId);

}
