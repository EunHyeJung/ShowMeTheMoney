package com.dev.smtm.domain;

public class StoreVO {
	private int store_id;
	private int user_id;
	private String store_name;
	private boolean over_wage;
	private boolean insurance;
	private int time_unit;

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

	public boolean isOver_wage() {
		return over_wage;
	}

	public void setOver_wage(boolean over_wage) {
		this.over_wage = over_wage;
	}

	public boolean isInsurance() {
		return insurance;
	}

	public void setInsurance(boolean insurance) {
		this.insurance = insurance;
	}

	public int getTime_unit() {
		return time_unit;
	}

	public void setTime_unit(int time_unit) {
		this.time_unit = time_unit;
	}

	@Override
	public String toString() {
		return "StoreVo [store_id=" + store_id + ", user_id=" + user_id + ", store_name=" + store_name + ", over_wage="
				+ over_wage + ", insurance=" + insurance + ", time_unit=" + time_unit + "]";
	}
}
