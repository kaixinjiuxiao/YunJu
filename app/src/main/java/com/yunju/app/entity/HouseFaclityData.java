package com.yunju.app.entity;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/6/29 0029
 * Describe:
 */
public class HouseFaclityData {

    private String name;
    private List<DataBean> data;

    public HouseFaclityData(String name, List<DataBean> data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private int iconRes;
        private String name;
        private boolean isSelected;

        public DataBean(int id, int iconRes, String name, boolean isSelected) {
            this.id = id;
            this.iconRes = iconRes;
            this.name = name;
            this.isSelected = isSelected;
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

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
