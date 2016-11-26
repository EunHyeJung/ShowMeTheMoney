package com.dv.smtm.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eunhye on 2016-08-05.
 */
public class UserDTO {
    private int user_id;
    private String email;
    private String pwd;
    private String name;
    private String phone;
    private int mode;
    private List<UserStaffDTO> userStaffs = new ArrayList<UserStaffDTO>();

    public UserDTO(){}


    public UserDTO(int user_id, String email, String pwd, String name, String phone, int mode, List<UserStaffDTO> list){
        this.user_id = user_id;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
        this.mode = mode;
        this.userStaffs = list;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }


    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getMode() {
        return mode;
    }

    public List<UserStaffDTO> getUserStaffs() {
        return userStaffs;
    }

    public void setUserStaffs(List<UserStaffDTO> userStaffs) {
        this.userStaffs = userStaffs;
    }


}
