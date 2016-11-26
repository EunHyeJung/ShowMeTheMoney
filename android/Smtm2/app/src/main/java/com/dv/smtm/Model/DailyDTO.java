package com.dv.smtm.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sue on 16. 8. 13..
 */
public class DailyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    int daily_seq;
    int date;
    String start_time;
    String end_time;

    public DailyDTO(int daily_seq, String start_time, String end_time) {
        this.daily_seq = daily_seq;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public void setDaily_seq(int daily_seq) {
        this.daily_seq = daily_seq;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setDate(int date) {
        this.date = date;
    }


    public int getDate() {
        return date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public int getDaily_seq() {
        return daily_seq;
    }

    @Override
    public String toString() {
        return "DailyDTO [daily_seq=" + daily_seq + ", start_time="
                + start_time + ", end_time=" + end_time + "]";
    }
}