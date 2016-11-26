package com.dv.smtm.Model;

import java.io.Serializable;

/**
 * Created by user on 2016-07-24.
 */
public class StoreDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int user_id;
    private String store_name;
    private int store_id;
    private int cnt_staffs;

    public StoreDTO() {

    }

    public StoreDTO( int store_id , String store_name) {
        this.store_id = store_id;
        this.store_name = store_name;
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

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public int getCnt_staffs() {
        return cnt_staffs;
    }

    public void setCnt_staffs(int cnt_staffs) {
        this.cnt_staffs = cnt_staffs;
    }
}



