package com.itheima.bos.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserAdvice implements java.io.Serializable {
	private String id;
	private String fromuser;
	private String adviceContent;
	private String userIp;
	private Date adviceTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFromuser() {
		return fromuser;
	}
	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}
	public String getAdviceContent() {
		return adviceContent;
	}
	public void setAdviceContent(String adviceContent) {
		this.adviceContent = adviceContent;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public Date getAdviceTime() {
		return adviceTime;
	}
	public String getAdviceTimeString() {		
		if(adviceTime!=null){
			return new SimpleDateFormat("yyyy-MM-dd").format(adviceTime).toString();
		}
		return "暂无数据";
	}
	public void setAdviceTime(Date adviceTime) {
		this.adviceTime = adviceTime;
	}
}
