package com.dv.smtm.Model;

import java.io.Serializable;

/**
 * Created by Sue on 16. 8. 13..
 */
public class StaffDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    int staff_id;
    String name;
    int store_id;
    int user_id;
    int grade;
    int hourly_wage;
    int status;

    public StaffDTO(int store_id,int  user_id){
        this.store_id = store_id;
        this.user_id = user_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setHourly_wage(int hourly_wage) {
        this.hourly_wage = hourly_wage;
    }

    public int getHourly_wage() {
        return hourly_wage;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}