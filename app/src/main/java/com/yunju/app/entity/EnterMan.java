package com.yunju.app.entity;

import java.io.Serializable;

/**
 * Author : Captain
 * Time : 2017/12/4
 * Describe :
 */

public class EnterMan implements Serializable{
    //{"id":11,"uid":11,"realname":"测试","mobile":"15000000000","id_card":"147258369369741258","addtime":"2018-05-14 11:22:58"}
    private String id;
    private String name;
    private String phone;
    private String address;
    private boolean isSelected;
    public EnterMan(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public EnterMan(String id, String name, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
