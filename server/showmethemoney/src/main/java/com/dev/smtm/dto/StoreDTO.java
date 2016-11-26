package com.dev.smtm.dto;

public class StoreDTO {
	private int store_id;
	private int user_id;
	private String store_name;
	private int cnt_staffs;


	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public int getCnt_staffs() {
		return cnt_staffs;
	}

	public void setCnt_staffs(int cnt_staffs) {
		this.cnt_staffs = cnt_staffs;
	}

}
