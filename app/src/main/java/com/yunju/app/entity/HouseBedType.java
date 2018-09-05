package com.yunju.app.entity;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/7/2 0002
 * Describe:
 */
public class HouseBedType {
    private String type;
    private List<String> mSizeList;

    public HouseBedType(String type, List<String> sizeList) {
        this.type = type;
        mSizeList = sizeList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getSizeList() {
        return mSizeList;
    }

    public void setSizeList(List<String> sizeList) {
        mSizeList = sizeList;
    }
}
