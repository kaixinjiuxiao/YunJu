package com.yunju.app.entity;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/6/6 0006
 * Describe:
 */
public class GoldCoinData {

    /**
     * status : 1
     * msg : [{"id":4,"type":0,"out_trade_no":"","state":0,"money":"100.00","remark":"申请提现","addtime":"2018-06-06 15:27:25"},{"id":1,"type":1,"out_trade_no":"","state":255,"money":"111.00","remark":"123123","addtime":"2018-06-06 10:20:55"}]
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
         * id : 4
         * type : 0
         * out_trade_no :
         * state : 0
         * money : 100.00
         * remark : 申请提现
         * addtime : 2018-06-06 15:27:25
         */

        private int id;
        private int type;
        private String out_trade_no;
        private int state;
        private String money;
        private String remark;
        private String addtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
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
