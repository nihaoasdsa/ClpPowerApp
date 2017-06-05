package com.example.clppowerapp.bean;

import java.util.List;

/**
 * Created by 008 on 2017/6/5 0005.
 */
public class HomeBean {

    private List<XianlumingxiBean> xianlumingxi;

    public List<XianlumingxiBean> getXianlumingxi() {
        return xianlumingxi;
    }
    public void setXianlumingxi(List<XianlumingxiBean> xianlumingxi) {
        this.xianlumingxi = xianlumingxi;
    }

    public static class XianlumingxiBean {
        /**
         * id : 1
         * mingcheng : (中压)955天才线路
         */

        private String id;
        private String mingcheng;
        private Boolean checked=false;

        public Boolean getChecked() {
            return checked;
        }

        public void setChecked(Boolean checked) {
            this.checked = checked;
        }

        public String getId() {
            return id;

        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMingcheng() {
            return mingcheng;
        }

        public void setMingcheng(String mingcheng) {
            this.mingcheng = mingcheng;
        }
    }
}
