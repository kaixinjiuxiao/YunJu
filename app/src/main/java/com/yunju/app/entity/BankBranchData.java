package com.yunju.app.entity;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/5/28 0028
 * Describe:
 */
public class BankBranchData {

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 102100004863,中国工商银行北京分行珠市口支行前门西大街分理处
         * text : 中国工商银行北京分行珠市口支行前门西大街分理处
         */

        private String id;
        private String text;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
