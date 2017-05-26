package com.itheima.bos.dao.impl;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.LogDao;
import com.itheima.bos.domain.Log;
@Repository(value="logDaoImpl")
public class LogDaoImpl extends BaseDaoImpl<Log> implements LogDao{

}
