package com.dev.smtm.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
	int user_id;
	String email;
	String pwd;
	String name;
	String phone;
	int mode;
	List<UserStaffDTO> userStaffs;
	
	public UserDTO(){
		userStaffs = new ArrayList<UserStaffDTO>();
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public List<UserStaffDTO> getUserStaffs() {
		return userStaffs;
	}

	public void setUserStaffs(List<UserStaffDTO> userStaffs) {
		this.userStaffs = userStaffs;
	}
	
	
}
