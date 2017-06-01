package com.example.clppowerapp.activity;

import android.app.Application;

//import com.zhy.http.okhttp.OkHttpUtils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by mapingping on 2016/12/14.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    public static RequestQueue volleyQueue;

    @Override
    public void onCreate() {
        super.onCreate();
          /* Volley配置 */
        // 建立Volley的Http请求队列
        volleyQueue = Volley.newRequestQueue(getApplicationContext());

    }

    // 开放Volley的HTTP请求队列接口
    public static RequestQueue getRequestQueue() {
        return volleyQueue;
    }
    // 单例模式获取唯一的MyApplication实例
    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

}

