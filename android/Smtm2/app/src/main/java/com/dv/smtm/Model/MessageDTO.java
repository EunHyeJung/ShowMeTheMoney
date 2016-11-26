package com.dv.smtm.Model;

/**
 * Created by eunhye on 2016-09-12.
 */
public class MessageDTO {
    String store_name;
    int grade;
    int hourly_wage;
    int status;


    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setHourly_wage(int hourly_wage) {
        this.hourly_wage = hourly_wage;
    }

    public int getHourly_wage() {
        return hourly_wage;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
