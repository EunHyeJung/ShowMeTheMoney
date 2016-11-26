package com.dev.smtm.domain;

public class StaffVO {
	private int staff_id;
	private int store_id;
	private int user_id;
	private int grade;
	private int hourly_wage;
	private int status;

	public StaffVO() {
		// TODO Auto-generated constructor stub
	}

	public int getStaff_id() { 
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

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

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getHourly_wage() {
		return hourly_wage;
	}

	public void setHourly_wage(int hourly_wage) {
		this.hourly_wage = hourly_wage;
	}
	
	private void setStatus(int status) {
		// TODO Auto-generated method stub
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "StaffVO [staff_id=" + staff_id + ", store_id=" + store_id + ", user_id=" + user_id + ", grade=" + grade
				+ ", hourly_wage=" + hourly_wage + "]";
	}


}
