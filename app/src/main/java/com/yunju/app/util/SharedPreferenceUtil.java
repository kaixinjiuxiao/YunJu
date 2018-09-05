package com.yunju.app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yunju.app.MyApplication;

import java.util.ArrayList;
import java.util.List;


public class SharedPreferenceUtil {
    private static final String KEY_LOGIN_STATUS = "login_status";
    private static final String KEY_USER_ACCOUNT = "user_account";
    private static final String KEY_TOKEN_TYPE = "token_type";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";

    public static void saveLoginStatus(boolean loginStatus) {
        SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putBoolean(KEY_LOGIN_STATUS, loginStatus);
        editor.apply();
    }

    public static void removeAll(){
        SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.clear();
        editor.commit();
    }

    public static boolean getLoginStatus() {
        return getUserSharedPreferences().getBoolean(KEY_LOGIN_STATUS, false);
    }

    public static void saveUserAccount(String account) {
        saveString(KEY_USER_ACCOUNT, account);
    }

    public static String getUserAccount() {
        return getString(KEY_USER_ACCOUNT);
    }

    public static void saveAccessToken(String token) {
        saveString(KEY_ACCESS_TOKEN, token);
    }

    public static String getAccessToken() {
        return getString(KEY_ACCESS_TOKEN);
    }

    public static void saveTokenType(String tokenType) {
        saveString(KEY_TOKEN_TYPE, tokenType);
    }

    public static String getTokenType() {
        return getString(KEY_TOKEN_TYPE);
    }

    public static void saveRefreshToken(String rToken) {
        saveString(KEY_REFRESH_TOKEN, rToken);
    }

    public static String getRefreshToken() {
        return getString(KEY_REFRESH_TOKEN);
    }


    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply(); //代替commit()
    }

    private static String getString(String key) {
        return getUserSharedPreferences().getString(key, null);
    }

    static SharedPreferences getUserSharedPreferences() {
        return MyApplication.getInstance().getSharedPreferences("user_info", Context.MODE_PRIVATE);
    }

    static SharedPreferences getCacheSharedPreferences() {
        return MyApplication.getInstance().getSharedPreferences("cache_data", Context.MODE_PRIVATE);
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public static <T> void setDataList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0) {
            return;
        }
        SharedPreferences.Editor editor = getCacheSharedPreferences().edit();
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
//        editor.clear();
        editor.putString(tag, strJson);
        editor.apply();

    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public static <T> List<T> getDataList(String tag, Class<T> cls) {
        List<T> datalist = new ArrayList<T>();
        String strJson = getCacheSharedPreferences().getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        JsonArray arry = new JsonParser().parse(strJson).getAsJsonArray();
        for (JsonElement jsonElement : arry) {
            datalist.add(gson.fromJson(jsonElement, cls));
        }
        return datalist;

    }

    public static void saveJsonString(String key, String jsonStr) {
        SharedPreferences.Editor edit = getCacheSharedPreferences().edit();
        edit.putString(key, jsonStr);
        edit.apply();
    }

    public static String getJsonString(String key) {
        SharedPreferences sp = getCacheSharedPreferences();
        return sp.getString(key, null);
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:将16进制的数据转为数组
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch; // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); // //两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16; // // 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; // // A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); // /两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); // // 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; // // A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;// 将转化后的数放入Byte里
        }
        return retData;
    }

}
