package com.dv.smtm.Model;

/**
 * Created by eunhye on 2016-10-23.
 */
public class DailyVo {
    private int daily_seq;
    private int staff_id;
    private String start_time;
    private String end_time;
    private int over_time;

    public int getStaff_id() {
        return staff_id;
    }

    public int getDaily_seq() {
        return daily_seq;
    }

    public int getOver_time() {
        return over_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public void setDaily_seq(int daily_seq) {
        this.daily_seq = daily_seq;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setOver_time(int over_time) {
        this.over_time = over_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}


