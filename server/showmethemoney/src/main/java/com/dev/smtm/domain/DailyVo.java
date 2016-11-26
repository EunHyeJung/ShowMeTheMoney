package com.dev.smtm.domain;

public class DailyVo {
	private int daily_seq;
	private int staff_id;
	private String start_time;
	private String end_time;
	private int over_time;
	

	public int getDaily_seq() {
		return daily_seq;
	}


	public void setDaily_seq(int daily_seq) {
		this.daily_seq = daily_seq;
	}


	public int getStaff_id() {
		return staff_id;
	}


	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}


	public String getStart_time() {
		return start_time;
	}


	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}


	public String getEnd_time() {
		return end_time;
	}


	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}


	public int getOver_time() {
		return over_time;
	}


	public void setOver_time(int over_time) {
		this.over_time = over_time;
	}


	@Override
	public String toString() {
		return "DailyVo [daily_seq=" + daily_seq +", staff_id=" + staff_id + ", start_time=" 
				+ start_time + ", end_time=" + end_time + ", over_time=" + over_time +"]";
	}
	
}
