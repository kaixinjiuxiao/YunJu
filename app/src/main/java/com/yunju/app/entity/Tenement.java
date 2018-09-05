package com.yunju.app.entity;

import java.io.Serializable;

public class Tenement implements Serializable{
    private int id;
    private String picture;
    private String title;
    private int num; //套数
    private double price; //价格
    private boolean isCollect;

    private Section section;

    public Tenement(){}
    public Tenement(int id, String picture, String title, int num, double price) {
        this.id = id;
        this.picture = picture;
        this.title = title;
        this.num = num;
        this.price = price;
    }

    public Tenement(int id, String picture, String title, int num, double price, boolean isCollect) {
        this.id = id;
        this.picture = picture;
        this.title = title;
        this.num = num;
        this.price = price;
        this.isCollect = isCollect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
