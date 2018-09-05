package com.yunju.app.entity;

import java.io.Serializable;

public class Facility implements Serializable{
    private int id;
    private int iconRes;
    private String name;
    private boolean isSelected;

    public Facility(int id, int iconRes, String name, boolean isSelected) {
        this.id = id;
        this.iconRes = iconRes;
        this.name = name;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
