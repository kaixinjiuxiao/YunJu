package com.yunju.app.entity;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/5/10 0010
 * Describe:
 */
public class FacilityData {
    private String str;
    private List<DateBean> list;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public List<DateBean> getList() {
        return list;
    }

    public void setList(List<DateBean> list) {
        this.list = list;
    }

    public static class DateBean{
        private String name;
        private String iconurl;
        private String pname;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIconurl() {
            return iconurl;
        }

        public void setIconurl(String iconurl) {
            this.iconurl = iconurl;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        @Override
        public String toString() {
            return "DateBean{" +
                    "name='" + name + '\'' +
                    ", iconurl='" + iconurl + '\'' +
                    ", pname='" + pname + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "FacilityData{" +
                "str='" + str + '\'' +
                ", list=" + list +
                '}';
    }
}
