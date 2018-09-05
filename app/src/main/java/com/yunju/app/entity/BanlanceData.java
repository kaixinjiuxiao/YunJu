package com.yunju.app.entity;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/6/5 0005
 * Describe:
 */
public class BanlanceData {

    /**
     * status : 1
     * data : [{"money":"200.00","week":"星期一","addtime":"2018-06-04 16:39:50","remark":"订金退回"},{"money":"-200.00","week":"星期一","addtime":"2018-06-04 16:39:38","remark":"订房消费"},{"money":"200.00","week":"星期一","addtime":"2018-06-04 16:20:30","remark":"订金退回"},{"money":"200.00","week":"星期一","addtime":"2018-06-04 16:18:08","remark":"订金退回"},{"money":"-200.00","week":"星期一","addtime":"2018-06-04 15:36:26","remark":"订房消费"},{"money":"-200.00","week":"星期四","addtime":"2018-05-17 14:44:40","remark":"订房消费"},{"money":"-200.00","week":"星期四","addtime":"2018-05-17 14:18:57","remark":"订房消费"},{"money":"-200.00","week":"星期四","addtime":"2018-05-17 14:18:29","remark":"订房消费"}]
     * balace : 99600.00
     * today : 0
     * month : -400.00
     */

    private int status;
    private String balace;
    private int today;
    private String month;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBalace() {
        return balace;
    }

    public void setBalace(String balace) {
        this.balace = balace;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * money : 200.00
         * week : 星期一
         * addtime : 2018-06-04 16:39:50
         * remark : 订金退回
         */

        private String money;
        private String week;
        private String addtime;
        private String remark;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
