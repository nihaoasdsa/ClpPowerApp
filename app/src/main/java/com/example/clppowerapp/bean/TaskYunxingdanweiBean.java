package com.example.clppowerapp.bean;

import java.util.List;

/**
 * Created by ypgt-007 on 2017/6/5.运行单位
 */

public class TaskYunxingdanweiBean {

    private List<YunxingdanweiBean> yunxingdanwei;

    public List<YunxingdanweiBean> getYunxingdanwei() {
        return yunxingdanwei;
    }

    public void setYunxingdanwei(List<YunxingdanweiBean> yunxingdanwei) {
        this.yunxingdanwei = yunxingdanwei;
    }

    public static class YunxingdanweiBean {
        /**
         * danwei : 工作
         */

        private String danwei;

        public String getDanwei() {
            return danwei;
        }

        public void setDanwei(String danwei) {
            this.danwei = danwei;
        }
    }
}
