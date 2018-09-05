package com.yunju.app.entity;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/5/17 0017
 * Describe:
 */
public class OrderListResponse {

    /**
     * status : 1
     * msg : success
     * data : [{"hname":"万家丽商务酒店","himg":"http://test.yj.hxzcmall.com/./Uploads/house_img_tmp/20180419/057ee76f08ef4603cfe11320ab3442f7.JPEG","check_in_time":"2018-05-19","check_out_time":"2018-05-20","trade":1,"id":45,"order_sn":"1201805171444384598","trade_remark":"待入住"},{"hname":"万家丽商务酒店","himg":"http://test.yj.hxzcmall.com/./Uploads/house_img_tmp/20180419/057ee76f08ef4603cfe11320ab3442f7.JPEG","check_in_time":"2018-05-19","check_out_time":"2018-05-20","trade":0,"id":44,"order_sn":"1201805171438154957","trade_remark":"待支付"},{"hname":"万家丽商务酒店","himg":"http://test.yj.hxzcmall.com/./Uploads/house_img_tmp/20180419/057ee76f08ef4603cfe11320ab3442f7.JPEG","check_in_time":"2018-05-18","check_out_time":"2018-05-19","trade":1,"id":42,"order_sn":"1201805171131365202","trade_remark":"待入住"}]
     */

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
         * hname : 万家丽商务酒店
         * himg : http://test.yj.hxzcmall.com/./Uploads/house_img_tmp/20180419/057ee76f08ef4603cfe11320ab3442f7.JPEG
         * check_in_time : 2018-05-19
         * check_out_time : 2018-05-20
         * trade : 1
         * id : 45
         * order_sn : 1201805171444384598
         * trade_remark : 待入住
         */

        private String hname;
        private String himg;
        private String check_in_time;
        private String check_out_time;
        private int trade;
        private int id;
        private String order_sn;
        private String trade_remark;

        public String getHname() {
            return hname;
        }

        public void setHname(String hname) {
            this.hname = hname;
        }

        public String getHimg() {
            return himg;
        }

        public void setHimg(String himg) {
            this.himg = himg;
        }

        public String getCheck_in_time() {
            return check_in_time;
        }

        public void setCheck_in_time(String check_in_time) {
            this.check_in_time = check_in_time;
        }

        public String getCheck_out_time() {
            return check_out_time;
        }

        public void setCheck_out_time(String check_out_time) {
            this.check_out_time = check_out_time;
        }

        public int getTrade() {
            return trade;
        }

        public void setTrade(int trade) {
            this.trade = trade;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getTrade_remark() {
            return trade_remark;
        }

        public void setTrade_remark(String trade_remark) {
            this.trade_remark = trade_remark;
        }
    }
}
