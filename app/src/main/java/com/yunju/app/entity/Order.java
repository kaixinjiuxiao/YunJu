package com.yunju.app.entity;

import java.io.Serializable;

/**
 * Created by sm on 2018/3/23 0023.
 */

public class Order implements Serializable{
    private int id;
    private int status; //订单状态
    private String source; //来源
    private String date; //入住日期
    private String name;
    private int personNum; //人数
    private int stayNum; //入住天数
    private Tenement tenement; //入住酒店

    public Order(int id, int status, String source, String date, String name, int personNum, int stayNum, Tenement tenement) {
        this.id = id;
        this.status = status;
        this.source = source;
        this.date = date;
        this.name = name;
        this.personNum = personNum;
        this.stayNum = stayNum;
        this.tenement = tenement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public int getStayNum() {
        return stayNum;
    }

    public void setStayNum(int stayNum) {
        this.stayNum = stayNum;
    }

    public Tenement getTenement() {
        return tenement;
    }

    public void setTenement(Tenement tenement) {
        this.tenement = tenement;
    }
}
