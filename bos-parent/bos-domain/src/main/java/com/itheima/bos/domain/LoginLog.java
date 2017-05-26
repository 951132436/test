package com.itheima.bos.domain;

public class LoginLog {
	//三个属性
	private String id;
	private String username;
	private String remoteHost;
	private String currentTime;
	
	public LoginLog() {
	}
	
	public LoginLog(String username, String remoteHost, String currentTime) {
		super();
		this.username = username;
		this.remoteHost = remoteHost;
		this.currentTime = currentTime;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRemoteHost() {
		return remoteHost;
	}
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	

}
