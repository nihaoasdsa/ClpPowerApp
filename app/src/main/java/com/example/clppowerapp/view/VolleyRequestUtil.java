package com.example.clppowerapp.view;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.clppowerapp.activity.MyApplication;

import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/12/19.
 */
public class VolleyRequestUtil {
    public static StringRequest stringRequest;
//    public static MultipartRequest multipartRequest;
    public static Context context;
    public static int  MY_SOCKET_TIMEOUT_MS=1000*60;//

    /*
    * 获取GET请求内容
    * 参数：
    * context：当前上下文；
    * url：请求的url地址；
    * tag：当前请求的标签；
    * volleyListenerInterface：VolleyListenerInterface接口；
    * */
    public static void RequestGet(Context context, String url, String tag, VolleyListenerInterface volleyListenerInterface) {
        // 清除请求队列中的tag标记请求
        MyApplication.getRequestQueue().cancelAll(tag);
        // 创建当前的请求，获取字符串内容
        stringRequest = new StringRequest(Request.Method.GET, url, volleyListenerInterface.responseListener(), volleyListenerInterface.errorListener());
        // 为当前请求添加标记
        stringRequest.setTag(tag);
        // 将当前请求添加到请求队列中
        MyApplication.getRequestQueue().add(stringRequest);
        // 重启当前请求队列
        MyApplication.getRequestQueue().start();
    }

    /*
    * 获取POST请求内容（请求的代码为Map）
    * 参数：
    * context：当前上下文；
    * url：请求的url地址；
    * tag：当前请求的标签；
    * params：POST请求内容；
    * volleyListenerInterface：VolleyListenerInterface接口；
    * */
    public static void RequestPost(Context context, String url, String tag, final Map<String, String> params, VolleyListenerInterface volleyListenerInterface) {
        Log.e("URL====","URL===》》》"+url);
        // 清除请求队列中的tag标记请求
        MyApplication.getRequestQueue().cancelAll(tag);
        // 创建当前的POST请求，并将请求内容写入Map中
        stringRequest = new StringRequest(Request.Method.POST, url, volleyListenerInterface.responseListener(), volleyListenerInterface.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        // 为当前请求添加标记
        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将当前请求添加到请求队列中
        MyApplication.getRequestQueue().add(stringRequest);
        // 重启当前请求队列
        MyApplication.getRequestQueue().start();
    }

//    public static void RequestMultipart(Context context, String url, String tag, List<File> files, final Map<String, String> params, VolleyListenerInterface volleyListenerInterface) {
//        // 清除请求队列中的tag标记请求
//        App.getRequestQueue().cancelAll(tag);
//        // 创建当前的POST请求，并将请求内容写入Map中
////        stringRequest = new StringRequest(Request.Method.POST, url, volleyListenerInterface.responseListener(), volleyListenerInterface.errorListener()){
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                return params;
////            }
////        };
//        multipartRequest = new MultipartRequest(url,volleyListenerInterface.responseListener(), volleyListenerInterface.errorListener(),"pic",files,params);
//        // 为当前请求添加标记
//        stringRequest.setTag(tag);
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                MY_SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        // 将当前请求添加到请求队列中
//        App.getRequestQueue().add(multipartRequest);
//        // 重启当前请求队列
//        App.getRequestQueue().start();
//    }
}
