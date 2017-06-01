package com.example.clppowerapp.ClpPowerutils;

/**
 * Created by 008 on 2017/5/31 0031.
 */
public class InputTextCheck {

    /**
     * 非空验证
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        boolean bool = false;
        if (str == null || str.equals("")) {
            bool = true;
        }
        return bool;
    }

}
