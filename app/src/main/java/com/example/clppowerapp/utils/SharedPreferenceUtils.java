package com.example.clppowerapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 008 on 2017/5/31 0031.
 */
public class SharedPreferenceUtils {
    private static String SharePreferenkey="zhongdian";
    /**
     *获取存储的信息
     *给他一个私有的限制
     * */
    private static SharedPreferences getShared(Context context){
        return context.getSharedPreferences(SharePreferenkey,Context.MODE_PRIVATE);
    }

    /**
     * 修改添加单个（String类型的）
     * value 值
     * key 建
     * editor 编辑 放值
     * **/
    public static  void  setSharedPreference(String key,String value,Context context){
          SharedPreferences.Editor editor=getShared(context).edit();
          editor.putString(key,value);
          editor.commit();
      }
    /**
     * 获取String值
     * key 值
     * **/
    public static  String getSharePreenceKeybyString(Context context,String key){
        return getShared(context).getString(key,"");
    }
    /**
     * 修改添加单个（int类型的）
     * value 值
     * key 建
     * editor 编辑 放值
     * **/
    public static  void  setSharedPreference(String key,int value,Context context){
        SharedPreferences.Editor editor=getShared(context).edit();
        editor.putInt(key,value);
        editor.commit();
    }
    /**
     * 获取Int值
     * key 值
     * **/
    public static  int getSharePreenceKeybyInt(Context context,String key){
        return getShared(context).getInt(key,0);
    }
    /**
     * 修改或者添加单个参数（boolean类型）
     * @param value 值
     * @param  key 键
     * @param
     * **/

    public static void setSharedPreferencesKey(String key,boolean value ,Context context) {
        SharedPreferences.Editor editor=getShared(context).edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    /**
     * 获取存储值(返回boolean类型)
     *
     * @param context
     * @param key 键
     * @return
     */

    public static boolean getSharePreenceKeybyboolean(Context context,String str, boolean key){
        return getShared(context).getBoolean(str,false);
    }
}
