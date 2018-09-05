package com.yunju.app.entity;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/6/6 0006
 * Describe:
 */
public class IntegralData {

    /**
     * status : 1
     * msg : [{"id":100,"source":1,"money":"260.00","remark":"领导奖","addtime":"2018-06-06 09:48:44"},{"id":99,"source":0,"money":"2600.00","remark":"推广奖","addtime":"2018-06-06 09:48:44"},{"id":98,"source":4,"money":"-100.00","remark":"申请提现","addtime":"2018-06-06 09:48:44"},{"id":97,"source":1,"money":"2.00","remark":"领导奖","addtime":"2018-06-06 09:48:44"},{"id":96,"source":1,"money":"2.00","remark":"领导奖","addtime":"2018-06-06 09:48:44"},{"id":95,"source":1,"money":"2.00","remark":"领导奖","addtime":"2018-06-06 09:48:44"},{"id":94,"source":1,"money":"2.00","remark":"领导奖","addtime":"2018-06-06 09:48:44"},{"id":93,"source":1,"money":"2.00","remark":"领导奖","addtime":"2018-06-06 09:48:44"},{"id":92,"source":0,"money":"20.00","remark":"推广奖","addtime":"2018-06-06 09:48:44"},{"id":91,"source":1,"money":"260.00","remark":"领导奖","addtime":"2018-06-06 09:48:44"}]
     */

    private int status;
    private List<MsgBean> msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * id : 100
         * source : 1
         * money : 260.00
         * remark : 领导奖
         * addtime : 2018-06-06 09:48:44
         */

        private int id;
        private int source;
        private String money;
        private String remark;
        private String addtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
