package com.yunju.app.entity;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/5/10 0010
 * Describe:
 */
public class AAA {

    /**
     * status : 1
     * msg : success
     * data : [{"address":"万家丽路","people_number":3,"bedtype":"[{\"bedtype\":\"\\u5927\\u5e8a\",\"bedlong\":\"2x1.8\\u7c73\",\"bednum\":\"1\\u5f20\"},{\"bedtype\":\"\\u5927\\u5e8a\",\"bedlong\":\"2x1.8\\u7c73\",\"bednum\":\"1\\u5f20\"}]","layout":"{\"\\u5367\\u5ba4\":\"3\",\"\\u5ba2\\u5385\":\"1\",\"\\u536b\\u751f\\u95f4\":\"1\",\"\\u53a8\\u623f\":\"1\",\"\\u4e66\\u623f\":\"1\",\"\\u9633\\u53f0\":\"1\"}","hname":"万家丽商务酒店","img":"[{\"imgurl\":\".\\/Uploads\\/house_img_tmp\\/20180419\\/057ee76f08ef4603cfe11320ab3442f7.JPEG\",\"imgremark\":\"\\u63cf\\u8ff01\"},{\"imgurl\":\".\\/Uploads\\/house_img_tmp\\/20180419\\/3cf027f8d54bb24c94e52ac567e42819.JPEG\",\"imgremark\":\"\\u63cf\\u8ff02123123123\"}]","id":33,"money":"200.00","living_room":"3","bednum":2,"img_logo":"./Uploads/house_img_tmp/20180419/057ee76f08ef4603cfe11320ab3442f7.JPEG"}]
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
         * address : 万家丽路
         * people_number : 3
         * bedtype : [{"bedtype":"\u5927\u5e8a","bedlong":"2x1.8\u7c73","bednum":"1\u5f20"},{"bedtype":"\u5927\u5e8a","bedlong":"2x1.8\u7c73","bednum":"1\u5f20"}]
         * layout : {"\u5367\u5ba4":"3","\u5ba2\u5385":"1","\u536b\u751f\u95f4":"1","\u53a8\u623f":"1","\u4e66\u623f":"1","\u9633\u53f0":"1"}
         * hname : 万家丽商务酒店
         * img : [{"imgurl":".\/Uploads\/house_img_tmp\/20180419\/057ee76f08ef4603cfe11320ab3442f7.JPEG","imgremark":"\u63cf\u8ff01"},{"imgurl":".\/Uploads\/house_img_tmp\/20180419\/3cf027f8d54bb24c94e52ac567e42819.JPEG","imgremark":"\u63cf\u8ff02123123123"}]
         * id : 33
         * money : 200.00
         * living_room : 3
         * bednum : 2
         * img_logo : ./Uploads/house_img_tmp/20180419/057ee76f08ef4603cfe11320ab3442f7.JPEG
         */

        private String address;
        private int people_number;
        private String bedtype;
        private String layout;
        private String hname;
        private String img;
        private int id;
        private String money;
        private String living_room;
        private int bednum;
        private String img_logo;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPeople_number() {
            return people_number;
        }

        public void setPeople_number(int people_number) {
            this.people_number = people_number;
        }

        public String getBedtype() {
            return bedtype;
        }

        public void setBedtype(String bedtype) {
            this.bedtype = bedtype;
        }

        public String getLayout() {
            return layout;
        }

        public void setLayout(String layout) {
            this.layout = layout;
        }

        public String getHname() {
            return hname;
        }

        public void setHname(String hname) {
            this.hname = hname;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getLiving_room() {
            return living_room;
        }

        public void setLiving_room(String living_room) {
            this.living_room = living_room;
        }

        public int getBednum() {
            return bednum;
        }

        public void setBednum(int bednum) {
            this.bednum = bednum;
        }

        public String getImg_logo() {
            return img_logo;
        }

        public void setImg_logo(String img_logo) {
            this.img_logo = img_logo;
        }
    }
}
