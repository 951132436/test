package com.itheima.bos.dao.impl;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.LoginLogDao;
import com.itheima.bos.domain.LoginLog;
@Repository(value="loginLogDaoImpl")
public class LoginLogDaoImpl extends BaseDaoImpl<LoginLog> implements LoginLogDao{

}
