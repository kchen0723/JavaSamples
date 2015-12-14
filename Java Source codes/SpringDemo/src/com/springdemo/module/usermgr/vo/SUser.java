package com.springdemo.module.usermgr.vo;

import java.util.Date;

import com.springdemo.core.Saveable;

public class SUser implements Saveable{
    
	private Integer id;
	private String userName;
	private String pwd;
	private Date signUpTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Date getSignUpTime() {
		return signUpTime;
	}
	public void setSignUpTime(Date signUpTime) {
		this.signUpTime = signUpTime;
	}
	private static String[] keyColumns = {"ID"};
	private static String TABLENAME = "SUSER";
	public String[] getKeyColumns() {
		return keyColumns;
	}
	public String getTableName() {
		return TABLENAME;
	}
	
}
