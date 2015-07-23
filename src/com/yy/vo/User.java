package com.yy.vo;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;

public class User extends EntityBase {

	@Column(column = "UserName")
	private String UserName;

	@Column(column = "Password")
	private String Password;

	@Column(column = "Name")
	private String Name;
	
	@Column(column = "Phone")
	private String Phone;

	@Column(column = "RegisterTime")
	private Date RegisterTime;

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public Date getRegisterTime() {
		return RegisterTime;
	}

	public void setRegisterTime(Date registerTime) {
		RegisterTime = registerTime;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}
