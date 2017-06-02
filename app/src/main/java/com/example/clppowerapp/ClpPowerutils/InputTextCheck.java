package com.example.clppowerapp.ClpPowerutils;

import java.util.List;

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
    public static boolean StringEmpty(Object value) {
        if (value == null) {
            return true;
        } else if ((value instanceof String)
                && (((String) value).trim().length() < 1)) {
            return true;
        } else if (value.getClass().isArray()) {
            if (0 == java.lang.reflect.Array.getLength(value)) {
                return true;
            }
        } else if (value instanceof List) {
            if (((List) value).isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
