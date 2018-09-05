package com.yunju.app.entity;

/**
 * @author: captain
 * Time:  2018/7/1 0001
 * Describe:
 */
public class EditBed {
    private String name;
    private boolean isChooice;

    public EditBed(String name, boolean isChooice) {
        this.name = name;
        this.isChooice = isChooice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChooice() {
        return isChooice;
    }

    public void setChooice(boolean chooice) {
        isChooice = chooice;
    }
}
