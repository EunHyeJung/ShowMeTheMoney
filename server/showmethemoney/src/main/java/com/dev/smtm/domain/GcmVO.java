package com.dev.smtm.domain;

public class GcmVO {
	String device_id;
	int user_id;

	public GcmVO() {
		// TODO Auto-generated constructor stub
	}

	public GcmVO(String device_id, int user_id) {
		this.device_id = device_id;
		this.user_id = user_id;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
