package com.dv.smtm.Model;

/**
 * Created by Sue on 16. 8. 9..
 */
public class LoginDTO {
    private String email;
    private String pwd;
    private String device_id;

    public LoginDTO(){}


    public LoginDTO(String email, String pwd, String device_id){
        this.email = email;
        this.pwd = pwd;
        this.device_id = device_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_id() {
        return device_id;
    }
}