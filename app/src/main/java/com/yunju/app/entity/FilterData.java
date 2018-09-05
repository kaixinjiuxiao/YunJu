package com.yunju.app.entity;

import java.io.Serializable;

public class FilterData implements Serializable{
    private int id;
    private int pId;
    private String itemText;

    public FilterData(int id, int pid, String itemtext){
        this.id=id;
        this.pId=pid;
        this.itemText=itemtext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }
}
