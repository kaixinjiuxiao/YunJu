package com.yunju.app.entity;


import java.io.Serializable;

public class Trace implements Serializable{
    private String status;
    private String remind;

    public Trace(String status, String remind) {
        this.status = status;
        this.remind = remind;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }
}
