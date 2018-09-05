package com.yunju.app.entity;

import java.io.Serializable;

/**
 * Created by sm on 2018/4/24 0024.
 */

public class Section implements Serializable{
    private int id;
    private String text;

    public Section(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
