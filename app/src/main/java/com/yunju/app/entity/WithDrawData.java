package com.yunju.app.entity;

/**
 * @author: captain
 * Time:  2018/6/6 0006
 * Describe:
 */
public class WithDrawData {

    /**
     * status : 1
     * msg : {"id":11,"bank_user":"中国工商银行","bank_address":"中国工商银行中牟县支行","bank_card":"1472583998080808895","coin":"999999.00","push_coin":"0.00","lead_coin":"0.00","cj_coin":"0.00","balance":"99600.00","bank_name":"张三","integral":"888888.00","go_detail":0}
     */

    private int status;
    private MsgBean msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * id : 11
         * bank_user : 中国工商银行
         * bank_address : 中国工商银行中牟县支行
         * bank_card : 1472583998080808895
         * coin : 999999.00
         * push_coin : 0.00
         * lead_coin : 0.00
         * cj_coin : 0.00
         * balance : 99600.00
         * bank_name : 张三
         * integral : 888888.00
         * go_detail : 0
         */

        private int id;
        private String bank_user;
        private String bank_address;
        private String bank_card;
        private String coin;
        private String push_coin;
        private String lead_coin;
        private String cj_coin;
        private String balance;
        private String bank_name;
        private String integral;
        private int go_detail;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBank_user() {
            return bank_user;
        }

        public void setBank_user(String bank_user) {
            this.bank_user = bank_user;
        }

        public String getBank_address() {
            return bank_address;
        }

        public void setBank_address(String bank_address) {
            this.bank_address = bank_address;
        }

        public String getBank_card() {
            return bank_card;
        }

        public void setBank_card(String bank_card) {
            this.bank_card = bank_card;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getPush_coin() {
            return push_coin;
        }

        public void setPush_coin(String push_coin) {
            this.push_coin = push_coin;
        }

        public String getLead_coin() {
            return lead_coin;
        }

        public void setLead_coin(String lead_coin) {
            this.lead_coin = lead_coin;
        }

        public String getCj_coin() {
            return cj_coin;
        }

        public void setCj_coin(String cj_coin) {
            this.cj_coin = cj_coin;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public int getGo_detail() {
            return go_detail;
        }

        public void setGo_detail(int go_detail) {
            this.go_detail = go_detail;
        }
    }
}
