package com.yunju.app.entity;

import java.util.List;

/**
 * @author: captain
 * Time:  2018/5/15 0015
 * Describe:
 */
public class AA {

    private String bank_user;


    private int status;
    private String msg;
    private List<DataBean> data;
    /**
     * id : 33
     * uid : 1
     * lid : 1
     * hname : 万家丽商务酒店
     * money : 200.00
     * current_price : 180.00
     * original_price : 300.00
     * deposit_price : 0.00
     * least_day : 1
     * img : [{"imgurl":"http://test.yj.hxzcmall.com/./Uploads/house_img_tmp/20180419/057ee76f08ef4603cfe11320ab3442f7.JPEG","imgremark":"描述1"},{"imgurl":"http://test.yj.hxzcmall.com/./Uploads/house_img_tmp/20180419/3cf027f8d54bb24c94e52ac567e42819.JPEG","imgremark":"描述2123123123"}]
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
     * xy_point : 0.000000
     * layout : {"卧室":"3","客厅":"1","卫生间":"1","厨房":"1","书房":"1","阳台":"1"}
     * detail : 房屋配有落地窗、品牌家电
     * rules : ["接待儿童","带宠物入住"]
     * bedtype : [{"bedtype":"大床","bedlong":"2x1.8米","bednum":"1张"},{"bedtype":"大床","bedlong":"2x1.8米","bednum":"1张"}]
     * hid : 0
     * living_room : 3
     * bednum : 2
     * deposit_remark : 无需押金
     * facilities_limit : [{"iconurl":"http://test.yj.hxzcmall.com//static/images/icon_tv.png","name":"电视"},{"iconurl":"http://test.yj.hxzcmall.com//static/images/icon_elevator.png","name":"电梯"},{"iconurl":"http://test.yj.hxzcmall.com//static/images/icon_viewingterrace.png","name":"观景露台"}]
     * facilities_data : {"居家":[{"name":"电视","iconurl":"http://test.yj.hxzcmall.com//static/images/icon_tv.png","pname":"居家"},{"name":"电热水壶","iconurl":"http://test.yj.hxzcmall.com//static/images/icon_kettle.png","pname":"居家"},{"name":"洗衣机","iconurl":"http://test.yj.hxzcmall.com//static/images/icon_washingmachine.png","pname":"居家"}],"特色及其他":[{"name":"电梯","iconurl":"http://test.yj.hxzcmall.com//static/images/icon_elevator.png","pname":"特色及其他"},{"name":"观景露台","iconurl":"http://test.yj.hxzcmall.com//static/images/icon_viewingterrace.png","pname":"特色及其他"}],"周边500米":[{"name":"超市","iconurl":"http://test.yj.hxzcmall.com//static/images/icon_supermarket.png","pname":"周边500米"}],"娱乐":[{"name":"卡拉OK","iconurl":"http://test.yj.hxzcmall.com//static/images/icon_karaoke.png","pname":"娱乐"}],"餐厨":[{"name":"燃气灶","iconurl":"http://test.yj.hxzcmall.com//static/images/icon_gasstove.png","pname":"餐厨"}],"卫浴":[{"name":"卫生纸","iconurl":"http://test.yj.hxzcmall.com//static/images/icon_tissues.png","pname":"卫浴"},{"name":"全天热水","iconurl":"http://test.yj.hxzcmall.com/","pname":"卫浴"}],"服务":[{"name":"行李寄存","iconurl":"http://test.yj.hxzcmall.com//static/images/icon_luggage.png","pname":"服务"}]}
     * facilities_count : 11
     * order_date : [{"check_in_time":"2018-06-20","check_out_time":"2018-06-22"},{"check_in_time":"2018-06-24","check_out_time":"2018-06-27"},{"check_in_time":"2018-06-19","check_out_time":"2018-06-20"}]
     * collect : 1
     * landlord : {"name":"测试房东","mobile":"15116470371"}
     */

    private int id;
    private int uid;
    private int lid;
    private String hname;
    private String money;
    private String current_price;
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
    private String xy_point;
    private LayoutBean layout;
    private String detail;
    private int hid;
    private String living_room;
    private int bednum;
    private String deposit_remark;
    private FacilitiesDataBean facilities_data;
    private int facilities_count;
    private int collect;
    private LandlordBean landlord;
    private List<ImgBean> img;
    private List<String> rules;
    private List<BedtypeBean> bedtype;
    private List<FacilitiesLimitBean> facilities_limit;
    private List<OrderDateBean> order_date;

    public String getBank_user() {
        return bank_user;
    }

    public void setBank_user(String bank_user) {
        this.bank_user = bank_user;
    }

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

    public String getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(String current_price) {
        this.current_price = current_price;
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

    public String getXy_point() {
        return xy_point;
    }

    public void setXy_point(String xy_point) {
        this.xy_point = xy_point;
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

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
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

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public LandlordBean getLandlord() {
        return landlord;
    }

    public void setLandlord(LandlordBean landlord) {
        this.landlord = landlord;
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

    public List<OrderDateBean> getOrder_date() {
        return order_date;
    }

    public void setOrder_date(List<OrderDateBean> order_date) {
        this.order_date = order_date;
    }

    public static class DataBean {
        /**
         * id : 102100004863,中国工商银行北京分行珠市口支行前门西大街分理处
         * text : 中国工商银行北京分行珠市口支行前门西大街分理处
         */

        private String id;
        private String text;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class CheckRuleBean {
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

    public static class RefundRuleBean {
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

    public static class LayoutBean {
        /**
         * 卧室 : 3
         * 客厅 : 1
         * 卫生间 : 1
         * 厨房 : 1
         * 书房 : 1
         * 阳台 : 1
         */

        private String 卧室;
        private String 客厅;
        private String 卫生间;
        private String 厨房;
        private String 书房;
        private String 阳台;

        public String get卧室() {
            return 卧室;
        }

        public void set卧室(String 卧室) {
            this.卧室 = 卧室;
        }

        public String get客厅() {
            return 客厅;
        }

        public void set客厅(String 客厅) {
            this.客厅 = 客厅;
        }

        public String get卫生间() {
            return 卫生间;
        }

        public void set卫生间(String 卫生间) {
            this.卫生间 = 卫生间;
        }

        public String get厨房() {
            return 厨房;
        }

        public void set厨房(String 厨房) {
            this.厨房 = 厨房;
        }

        public String get书房() {
            return 书房;
        }

        public void set书房(String 书房) {
            this.书房 = 书房;
        }

        public String get阳台() {
            return 阳台;
        }

        public void set阳台(String 阳台) {
            this.阳台 = 阳台;
        }
    }

    public static class FacilitiesDataBean {
        private List<居家Bean> 居家;
        private List<特色及其他Bean> 特色及其他;
        private List<周边500米Bean> 周边500米;
        private List<娱乐Bean> 娱乐;
        private List<餐厨Bean> 餐厨;
        private List<卫浴Bean> 卫浴;
        private List<服务Bean> 服务;

        public List<居家Bean> get居家() {
            return 居家;
        }

        public void set居家(List<居家Bean> 居家) {
            this.居家 = 居家;
        }

        public List<特色及其他Bean> get特色及其他() {
            return 特色及其他;
        }

        public void set特色及其他(List<特色及其他Bean> 特色及其他) {
            this.特色及其他 = 特色及其他;
        }

        public List<周边500米Bean> get周边500米() {
            return 周边500米;
        }

        public void set周边500米(List<周边500米Bean> 周边500米) {
            this.周边500米 = 周边500米;
        }

        public List<娱乐Bean> get娱乐() {
            return 娱乐;
        }

        public void set娱乐(List<娱乐Bean> 娱乐) {
            this.娱乐 = 娱乐;
        }

        public List<餐厨Bean> get餐厨() {
            return 餐厨;
        }

        public void set餐厨(List<餐厨Bean> 餐厨) {
            this.餐厨 = 餐厨;
        }

        public List<卫浴Bean> get卫浴() {
            return 卫浴;
        }

        public void set卫浴(List<卫浴Bean> 卫浴) {
            this.卫浴 = 卫浴;
        }

        public List<服务Bean> get服务() {
            return 服务;
        }

        public void set服务(List<服务Bean> 服务) {
            this.服务 = 服务;
        }

        public static class 居家Bean {
            /**
             * name : 电视
             * iconurl : http://test.yj.hxzcmall.com//static/images/icon_tv.png
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

        public static class 特色及其他Bean {
            /**
             * name : 电梯
             * iconurl : http://test.yj.hxzcmall.com//static/images/icon_elevator.png
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

        public static class 周边500米Bean {
            /**
             * name : 超市
             * iconurl : http://test.yj.hxzcmall.com//static/images/icon_supermarket.png
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

        public static class 娱乐Bean {
            /**
             * name : 卡拉OK
             * iconurl : http://test.yj.hxzcmall.com//static/images/icon_karaoke.png
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

        public static class 餐厨Bean {
            /**
             * name : 燃气灶
             * iconurl : http://test.yj.hxzcmall.com//static/images/icon_gasstove.png
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

        public static class 卫浴Bean {
            /**
             * name : 卫生纸
             * iconurl : http://test.yj.hxzcmall.com//static/images/icon_tissues.png
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

        public static class 服务Bean {
            /**
             * name : 行李寄存
             * iconurl : http://test.yj.hxzcmall.com//static/images/icon_luggage.png
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

    public static class LandlordBean {
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

    public static class ImgBean {
        /**
         * imgurl : http://test.yj.hxzcmall.com/./Uploads/house_img_tmp/20180419/057ee76f08ef4603cfe11320ab3442f7.JPEG
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

    public static class BedtypeBean {
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

    public static class FacilitiesLimitBean {
        /**
         * iconurl : http://test.yj.hxzcmall.com//static/images/icon_tv.png
         * name : 电视
         */

        private String iconurl;
        private String name;

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

    public static class OrderDateBean {
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
