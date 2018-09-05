package com.yunju.app.util;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.yunju.app.MyApplication;


/**
 * Toast统一管理类
 * Created by lenovo on 2017/7/2.
 */

public class ToastUtil {

    private static Toast toast;

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication
                    .getInstance(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void showShort(@StringRes int res) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getInstance(), res, Toast.LENGTH_SHORT);
        } else {
            toast.setText(res);
        }
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void showLong(@StringRes int res) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getInstance(), res, Toast.LENGTH_LONG);
        } else {
            toast.setText(res);
        }
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getInstance(), message, duration);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void show(@StringRes int res, int duration) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getInstance(), res, duration);
        } else {
            toast.setText(res);
        }
        toast.show();
    }
}
