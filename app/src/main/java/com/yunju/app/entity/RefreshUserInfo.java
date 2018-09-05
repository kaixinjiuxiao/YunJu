package com.yunju.app.entity;

/**
 * @author: captain
 * Time:  2018/5/29 0029
 * Describe:
 */
public class RefreshUserInfo {
    private String message;

    public RefreshUserInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
