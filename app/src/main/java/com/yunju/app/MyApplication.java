package com.yunju.app;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.yunju.app.service.LocationService;

public class MyApplication extends Application {

    private static MyApplication myApplication;
    public LocationService locationService;
    public Vibrator mVibrator;

    public static MyApplication getInstance(){
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;

        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
        // 实例化请求实现类
       // OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).build();
//        OkHttpService okHttpService = new OkHttpService(okHttpClient);
        //RequestService requestService = new RequestService(okHttpClient);
        // 设置EasyHttp的功能实现类为okHttpService，post提交方式为form表单，数据解析方式为GSON
        // 如果post提交数据的类型既不是form也不是json，则通过EasyRequest.Builder的requestBody()传入自定义的请求体
       // EasyHttp.getInstance().initHttpService(requestService, Constants.MEDIA_TYPE_FORM, DataParser.PARSE_GSON);
    }
}
