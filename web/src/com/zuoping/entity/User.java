package com.zuoping.entity;

public class User {
	private String username;
	private String tel;
	private String password;
	
	public User(){}
	
	public User(String username, String password, String tel) {
		this.username = username;
		this.tel = tel;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}