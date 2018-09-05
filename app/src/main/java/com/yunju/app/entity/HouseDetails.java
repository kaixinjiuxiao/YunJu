package com.yunju.app.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author: captain
 * Time:  2018/5/9 0009
 * Describe:
 */
public class HouseDetails implements Serializable{

    /**
     * id : 33
     * uid : 1
     * lid : 1
     * hname : 万家丽商务酒店
     * money : 200.00
     * original_price : 300.00
     * deposit_price : 0.00
     * least_day : 1
     * img : [{"imgurl":"./Uploads/house_img_tmp/20180419/057ee76f08ef4603cfe11320ab3442f7.JPEG","imgremark":"描述1"},{"imgurl":"./Uploads/house_img_tmp/20180419/3cf027f8d54bb24c94e52ac567e42819.JPEG","imgremark":"描述2"}]
     * type : 1
     * people_number : 3
     * province : 湖南省
     * city : 长沙
     * region : 芙蓉区
     * address : 万家丽路
     * number : 8888
     * check_rule : {"check_in_time":"00:00","check_out_time":"23:00","reception_time":"00:00 - 20:00"}
     * refund_rule : {"refund_day":"1","refund_ratio":"100","refund_remark":"前1天"}
     * addtime : 2018-04-19 16:05:45
     * layout : {"卧室":"3","客厅":"1","卫生间":"1","厨房":"1","书房":"1","阳台":"1"}
     * detail : 房屋配有落地窗、品牌家电
     * rules : ["接待儿童","带宠物入住"]
     * bedtype : [{"bedtype":"大床","bedlong":"2x1.8米","bednum":"1张"},{"bedtype":"大床","bedlong":"2x1.8米","bednum":"1张"}]
     * living_room : 3
     * bednum : 2
     * deposit_remark : 无需押金
     * facilities_limit : [{"iconurl":"http://test.yj.hxzcmall.com//static/images/icon_tv.png","name":"电视"},{"iconurl":"http://test.yj.hxzcmall.com//static/images/icon_elevator.png","name":"电梯"},{"iconurl":"http://test.yj.hxzcmall.com//static/images/icon_viewingterrace.png","name":"观景露台"}]
     * facilities_data : {"居家":[{"name":"电视","iconurl":"/static/images/icon_tv.png","pname":"居家"},{"name":"电热水壶","iconurl":"/static/images/icon_kettle.png","pname":"居家"},{"name":"洗衣机","iconurl":"/static/images/icon_washingmachine.png","pname":"居家"}],"特色及其他":[{"name":"电梯","iconurl":"/static/images/icon_elevator.png","pname":"特色及其他"},{"name":"观景露台","iconurl":"/static/images/icon_viewingterrace.png","pname":"特色及其他"}],"周边500米":[{"name":"超市","iconurl":"/static/images/icon_supermarket.png","pname":"周边500米"}],"娱乐":[{"name":"卡拉OK","iconurl":"/static/images/icon_karaoke.png","pname":"娱乐"}],"餐厨":[{"name":"燃气灶","iconurl":"/static/images/icon_gasstove.png","pname":"餐厨"}],"卫浴":[{"name":"卫生纸","iconurl":"/static/images/icon_tissues.png","pname":"卫浴"},{"name":"全天热水","iconurl":"","pname":"卫浴"}],"服务":[{"name":"行李寄存","iconurl":"/static/images/icon_luggage.png","pname":"服务"}]}
     * facilities_count : 11
     */

    private int id;
    private int uid;
    private int lid;
    private String hname;
    private String money;
    private String original_price;
    private String deposit_price;
    private int least_day;
    private int type;
    private int people_number;
    private String province;
    private String city;
    private String region;
    private String address;
    private String number;
    private CheckRuleBean check_rule;
    private RefundRuleBean refund_rule;
    private String addtime;
    private LayoutBean layout;
    private String detail;
    private String living_room;
    private int bednum;
    private String deposit_remark;
    private FacilitiesDataBean facilities_data;
    private int facilities_count;
    private List<ImgBean> img;
    private List<String> rules;
    private List<BedtypeBean> bedtype;
    private List<FacilitiesLimitBean> facilities_limit;
    private LandlordBean landlord;
    private int collect;
    private List<OrderDateBean> order_date;

    public List<OrderDateBean> getOrder_date() {
        return order_date;
    }

    public void setOrder_date(List<OrderDateBean> order_date) {
        this.order_date = order_date;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

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

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getDeposit_price() {
        return deposit_price;
    }

    public void setDeposit_price(String deposit_price) {
        this.deposit_price = deposit_price;
    }

    public int getLeast_day() {
        return least_day;
    }

    public void setLeast_day(int least_day) {
        this.least_day = least_day;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPeople_number() {
        return people_number;
    }

    public void setPeople_number(int people_number) {
        this.people_number = people_number;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CheckRuleBean getCheck_rule() {
        return check_rule;
    }

    public void setCheck_rule(CheckRuleBean check_rule) {
        this.check_rule = check_rule;
    }

    public RefundRuleBean getRefund_rule() {
        return refund_rule;
    }

    public void setRefund_rule(RefundRuleBean refund_rule) {
        this.refund_rule = refund_rule;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public LayoutBean getLayout() {
        return layout;
    }

    public void setLayout(LayoutBean layout) {
        this.layout = layout;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getDeposit_remark() {
        return deposit_remark;
    }

    public void setDeposit_remark(String deposit_remark) {
        this.deposit_remark = deposit_remark;
    }

    public FacilitiesDataBean getFacilities_data() {
        return facilities_data;
    }

    public void setFacilities_data(FacilitiesDataBean facilities_data) {
        this.facilities_data = facilities_data;
    }

    public int getFacilities_count() {
        return facilities_count;
    }

    public void setFacilities_count(int facilities_count) {
        this.facilities_count = facilities_count;
    }

    public List<ImgBean> getImg() {
        return img;
    }

    public void setImg(List<ImgBean> img) {
        this.img = img;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    public List<BedtypeBean> getBedtype() {
        return bedtype;
    }

    public void setBedtype(List<BedtypeBean> bedtype) {
        this.bedtype = bedtype;
    }

    public List<FacilitiesLimitBean> getFacilities_limit() {
        return facilities_limit;
    }

    public void setFacilities_limit(List<FacilitiesLimitBean> facilities_limit) {
        this.facilities_limit = facilities_limit;
    }

    public LandlordBean getLandlord() {
        return landlord;
    }

    public void setLandlord(LandlordBean landlord) {
        this.landlord = landlord;
    }

    public static class CheckRuleBean implements Serializable {
        /**
         * check_in_time : 00:00
         * check_out_time : 23:00
         * reception_time : 00:00 - 20:00
         */

        private String check_in_time;
        private String check_out_time;
        private String reception_time;

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

        public String getReception_time() {
            return reception_time;
        }

        public void setReception_time(String reception_time) {
            this.reception_time = reception_time;
        }
    }

    public static class RefundRuleBean implements Serializable {
        /**
         * refund_day : 1
         * refund_ratio : 100
         * refund_remark : 前1天
         */

        private String refund_day;
        private String refund_ratio;
        private String refund_remark;

        public String getRefund_day() {
            return refund_day;
        }

        public void setRefund_day(String refund_day) {
            this.refund_day = refund_day;
        }

        public String getRefund_ratio() {
            return refund_ratio;
        }

        public void setRefund_ratio(String refund_ratio) {
            this.refund_ratio = refund_ratio;
        }

        public String getRefund_remark() {
            return refund_remark;
        }

        public void setRefund_remark(String refund_remark) {
            this.refund_remark = refund_remark;
        }
    }

    public static class LayoutBean implements Serializable{
        /**
         * 卧室 : 3
         * 客厅 : 1
         * 卫生间 : 1
         * 厨房 : 1
         * 书房 : 1
         * 阳台 : 1
         */
        @SerializedName("卧室")
        private String bedRoom;
        @SerializedName("客厅")
        private String livingRoom;
        @SerializedName("卫生间")
        private String tolite;
        @SerializedName("厨房")
        private String kitchen;
        @SerializedName("书房")
        private String studyRoom;
        @SerializedName("阳台")
        private String balcony;

        public String getBedRoom() {
            return bedRoom;
        }

        public void setBedRoom(String bedRoom) {
            this.bedRoom = bedRoom;
        }

        public String getLivingRoom() {
            return livingRoom;
        }

        public void setLivingRoom(String livingRoom) {
            this.livingRoom = livingRoom;
        }

        public String getTolite() {
            return tolite;
        }

        public void setTolite(String tolite) {
            this.tolite = tolite;
        }

        public String getKitchen() {
            return kitchen;
        }

        public void setKitchen(String kitchen) {
            this.kitchen = kitchen;
        }

        public String getStudyRoom() {
            return studyRoom;
        }

        public void setStudyRoom(String studyRoom) {
            this.studyRoom = studyRoom;
        }

        public String getBalcony() {
            return balcony;
        }

        public void setBalcony(String balcony) {
            this.balcony = balcony;
        }
    }

    public static class FacilitiesDataBean implements Serializable{
        @SerializedName("居家")
        private List<HomeBean>home;
        @SerializedName("特色及其他")
        private List<FeaturesAndOthers>featuresAndOthers;
        @SerializedName("周边500米")
        private List<Around>around;
        @SerializedName("娱乐")
        private List<Entertainment>entertainment;
        @SerializedName("餐厨")
        private List<DiningKitchen>diningKitchen;
        @SerializedName("卫浴")
        private List<Bathroom>bathroom;
        @SerializedName("服务")
        private List<OtherService>otherService;

        public List<HomeBean> getHome() {
            return home;
        }

        public void setHome(List<HomeBean> home) {
            this.home = home;
        }

        public List<FeaturesAndOthers> getFeaturesAndOthers() {
            return featuresAndOthers;
        }

        public void setFeaturesAndOthers(List<FeaturesAndOthers> featuresAndOthers) {
            this.featuresAndOthers = featuresAndOthers;
        }

        public List<Around> getAround() {
            return around;
        }

        public void setAround(List<Around> around) {
            this.around = around;
        }

        public List<Entertainment> getEntertainment() {
            return entertainment;
        }

        public void setEntertainment(List<Entertainment> entertainment) {
            this.entertainment = entertainment;
        }

        public List<DiningKitchen> getDiningKitchen() {
            return diningKitchen;
        }

        public void setDiningKitchen(List<DiningKitchen> diningKitchen) {
            this.diningKitchen = diningKitchen;
        }

        public List<Bathroom> getBathroom() {
            return bathroom;
        }

        public void setBathroom(List<Bathroom> bathroom) {
            this.bathroom = bathroom;
        }

        public List<OtherService> getOtherService() {
            return otherService;
        }

        public void setOtherService(List<OtherService> otherService) {
            this.otherService = otherService;
        }

        public static class HomeBean implements Serializable {
            /**
             * name : 电视
             * iconurl : /static/images/icon_tv.png
             * pname : 居家
             */

            private String name;
            private String iconurl;
            private String pname;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconurl() {
                return iconurl;
            }

            public void setIconurl(String iconurl) {
                this.iconurl = iconurl;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }
        }

        public static class FeaturesAndOthers implements Serializable{
            /**
             * name : 电梯
             * iconurl : /static/images/icon_elevator.png
             * pname : 特色及其他
             */

            private String name;
            private String iconurl;
            private String pname;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconurl() {
                return iconurl;
            }

            public void setIconurl(String iconurl) {
                this.iconurl = iconurl;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }
        }

        public static class Around implements Serializable {
            /**
             * name : 超市
             * iconurl : /static/images/icon_supermarket.png
             * pname : 周边500米
             */

            private String name;
            private String iconurl;
            private String pname;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconurl() {
                return iconurl;
            }

            public void setIconurl(String iconurl) {
                this.iconurl = iconurl;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }
        }

        public static class Entertainment implements Serializable{
            /**
             * name : 卡拉OK
             * iconurl : /static/images/icon_karaoke.png
             * pname : 娱乐
             */

            private String name;
            private String iconurl;
            private String pname;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconurl() {
                return iconurl;
            }

            public void setIconurl(String iconurl) {
                this.iconurl = iconurl;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }
        }

        public static class DiningKitchen implements Serializable {
            /**
             * name : 燃气灶
             * iconurl : /static/images/icon_gasstove.png
             * pname : 餐厨
             */

            private String name;
            private String iconurl;
            private String pname;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconurl() {
                return iconurl;
            }

            public void setIconurl(String iconurl) {
                this.iconurl = iconurl;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }
        }

        public static class Bathroom implements Serializable{
            /**
             * name : 卫生纸
             * iconurl : /static/images/icon_tissues.png
             * pname : 卫浴
             */

            private String name;
            private String iconurl;
            private String pname;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconurl() {
                return iconurl;
            }

            public void setIconurl(String iconurl) {
                this.iconurl = iconurl;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }
        }

        public static class OtherService implements Serializable{
            /**
             * name : 行李寄存
             * iconurl : /static/images/icon_luggage.png
             * pname : 服务
             */

            private String name;
            private String iconurl;
            private String pname;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconurl() {
                return iconurl;
            }

            public void setIconurl(String iconurl) {
                this.iconurl = iconurl;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }
        }
    }
    public static class LandlordBean implements Serializable{
        /**
         * name : 测试房东
         * mobile : 15116470371
         */

        private String name;
        private String mobile;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
    public static class ImgBean implements Serializable {
        /**
         * imgurl : ./Uploads/house_img_tmp/20180419/057ee76f08ef4603cfe11320ab3442f7.JPEG
         * imgremark : 描述1
         */

        private String imgurl;
        private String imgremark;

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getImgremark() {
            return imgremark;
        }

        public void setImgremark(String imgremark) {
            this.imgremark = imgremark;
        }
    }

    public static class BedtypeBean implements Serializable{
        /**
         * bedtype : 大床
         * bedlong : 2x1.8米
         * bednum : 1张
         */

        private String bedtype;
        private String bedlong;
        private String bednum;

        public String getBedtype() {
            return bedtype;
        }

        public void setBedtype(String bedtype) {
            this.bedtype = bedtype;
        }

        public String getBedlong() {
            return bedlong;
        }

        public void setBedlong(String bedlong) {
            this.bedlong = bedlong;
        }

        public String getBednum() {
            return bednum;
        }

        public void setBednum(String bednum) {
            this.bednum = bednum;
        }
    }

    public static class FacilitiesLimitBean implements Serializable{
        /**
         * iconurl : http://test.yj.hxzcmall.com//static/images/icon_tv.png
         * name : 电视
         */

        private String iconurl;
        private String name;

        public FacilitiesLimitBean(String iconurl, String name) {
            this.iconurl = iconurl;
            this.name = name;
        }

        public String getIconurl() {
            return iconurl;
        }

        public void setIconurl(String iconurl) {
            this.iconurl = iconurl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public static class OrderDateBean implements Serializable{
        /**
         * check_in_time : 2018-06-20
         * check_out_time : 2018-06-22
         */

        private String check_in_time;
        private String check_out_time;

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
    }
}
