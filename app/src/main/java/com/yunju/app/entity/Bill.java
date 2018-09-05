package com.yunju.app.entity;

import java.io.Serializable;

public class Bill implements Serializable{
    private int id;
    private String date;
    private String title;
    private String content;
    private double money;

    public Bill(int id, String date, String title, String content, double money) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
