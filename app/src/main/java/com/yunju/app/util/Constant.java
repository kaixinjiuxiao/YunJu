package com.yunju.app.util;

/**
 * Created by sm on 2017/7/1.
 */

public class Constant {
    public static final int NETWORK_UNKNOWN=0;
    public static final int NETWORK_WIFI=1;
    public static final int NETWORK_2G=2;
    public static final int NETWORK_3G=3;
    public static final int NETWORK_4G=4;

    public static final String BASE = "http://test.yj.hxzcmall.com/";
    public static final String REGISTER = BASE+"appapi/login/register";
    public static final String LOGIN = BASE+"appapi/login/login_pwd";
    public static final String SMS_CODE = BASE+"appapi/login/send_sms";
    public static final String FIND_PASSWORD=BASE+"";
   public static final String USER_CENTER = BASE+"appapi/User/get_user_info";

    public static final String HOUSU_SEARCH = BASE+"appapi/House_info/search";
    public static final String  HOUSE_DETAILS = BASE+"appapi/Houseinfo/details";
    //收藏或取消
    public static final String COLLECT_OR_CANCEL = BASE+"appapi/Collect/action_collect";
    //收藏列表
    public static final String COLLECT_LIST = BASE+"appapi/Collect/get_collect";

    public static final String COMMIT_ORDER = BASE+"appapi/Order_info/create_order";
    public static final String SURE_ORDER = BASE+"appapi/Order_info/sure_order";
    public static final String ENTERMAN_LIST = BASE+"appapi/Order_info/occupant_get";
    public static final String ADD_ENTERMAN = BASE+"appapi/Order_info/occupant_add";
    public static final String ORDER_PAY = BASE+"appapi/Order_info/pay_order";

    public static final String ORDER_LIST = BASE+"appapi/My_order/order_list";
    public static final String ORDER_DETAILS = BASE+"appapi/My_order/order_details";
    public static final String RETREAT_ORDER = BASE+"appapi/My_order/retreat_house";
     public static final String CANCEL_ORDER = BASE+"appapi/My_Order/cancel_Order";

    public static final String USER_INFO=BASE+"appapi/User/get_user_detail";
    public static final String BANK_LIST=BASE+"Data/Json/bank_user.json";
    public static final String AREA_LIST=BASE+"Data/Json/area.json";
    public static final String BANK_BRANCH_LIST=BASE+"appapi/User/get_bank_info";
    public static final String NODIFIY_USER_INDO=BASE+"appapi/User/up_user_detail";
    public static final String BANLANCE_DETAILS = BASE+"appapi/User/user_balace_detail";
    public static final String INTEGRAL_DETAILS = BASE+"appapi/User/coindetail";
    public static final String COIN_DETAILS = BASE+"appapi/User/goldendetails";
    public static final String WITHDRAWL_COMMIT = BASE+"appapi/User/PutForward";
    public static final String WITHDRAWL_BANLANCE = BASE+"appapi/User/PutForwardType";

    public static final String USER_ID_AUTHER=BASE+"appapi/User/up_user_img";
    public static final String USER_ID_CHECK=BASE+"appapi/user/real_auth_check";
    public static final String USER_SHARE_FRIEND = BASE+"appapi/User/user_share";
    public static final String GET_OSS_DATA=BASE+"appapi/Aliyun/get_app_config";
    public static final String GET_OSS_BUCKET=BASE+"appapi/Aliyun/get_up_dir";
    public static final String OSS_CALLBACK_ADD=BASE+"Alioss/callback.php";


    //房东
    public static final String Examine_Result=BASE+"appapi/Houseinfo/getpapercheck";
    public static final String APPLY_COMMIT=BASE+"appapi/Houseinfo/papar_check";
    public static final String CREATE_NEW_HOUSE=BASE+"appapi/Houseinfo/newhouse";
    public static final String SAVE_LOCATION=BASE+"appapi/Houseinfo/savePosition";
    public static final String HOUSE_FACILITY =BASE+"appapi/Houseinfo/facilities";
    public static final String LANDLORD_INFO = BASE+"appapi/Houseinfo/beHouseOwner";
    public static final String CHECK_INFO=BASE+"appapi/Houseinfo/getPapercheck";
    public static final String SAVE_HOUSE_INFO = BASE+"appapi/Houseinfo/saveHouseDetail";
}
