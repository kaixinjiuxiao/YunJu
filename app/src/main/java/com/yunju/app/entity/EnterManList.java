package com.yunju.app.entity;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/5/14 0014
 * Describe:
 */
public class EnterManList {

    /**
     * status : 1
     * data : [{"id":11,"uid":11,"realname":"测试","mobile":"15000000000","id_card":"147258369369741258","addtime":"2018-05-14 11:22:58"},{"id":10,"uid":11,"realname":"测试2","mobile":"100000000","id_card":"1","addtime":"2018-05-12 20:16:55"},{"id":9,"uid":11,"realname":"测试","mobile":"15000000000","id_card":"430921100010001000","addtime":"2018-05-12 17:36:21"}]
     * msg : success
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
         * id : 11
         * uid : 11
         * realname : 测试
         * mobile : 15000000000
         * id_card : 147258369369741258
         * addtime : 2018-05-14 11:22:58
         */

        private int id;
        private int uid;
        private String realname;
        private String mobile;
        private String id_card;
        private String addtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
