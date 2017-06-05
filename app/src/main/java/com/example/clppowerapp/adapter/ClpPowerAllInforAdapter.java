package com.example.clppowerapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clppowerapp.R;
import com.example.clppowerapp.activity.MainActivity;
import com.example.clppowerapp.bean.Bean;
import com.example.clppowerapp.bean.HomeBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 008 on 2017/5/27 0027.
 * 首页数据的适配器
 */
public class ClpPowerAllInforAdapter extends BaseAdapter {
    // 数据集
    private ListView lv_data;
    //定义一个数据源的引用
    private List<HomeBean.XianlumingxiBean> list;
    private Context context;
    public ClpPowerAllInforAdapter(Context context,ListView lv_data,List<HomeBean.XianlumingxiBean>list1) {
        if (context instanceof MainActivity) {
            this.context = context;
            this.lv_data=lv_data;
            this.list=list1;
         //   list=((MainActivity)this.context).getData();
        }
    }


    @Override
    public int getCount() {
        return list!=null &&list.size()>0 ?list.size() :0;
    }

    @Override
    public Object getItem(int position) {
        return list!=null &&list.size()>0 ?list.get(position) :0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.home_list_item,null);
            viewHolder.ll_edit= (LinearLayout) convertView.findViewById(R.id.ll_edit);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.cb_selector= (CheckBox) convertView.findViewById(R.id.item_cb);
            //设置标签
            convertView.setTag(viewHolder);
        }else {
            //取出标签
            viewHolder= (ViewHolder) convertView.getTag();
        }
        // 拿到对象
        HomeBean.XianlumingxiBean bean = list.get(position);
        viewHolder.tv_name.setText(list.get(position).getMingcheng());
    //    viewHolder.cb_selector.setChecked(bean.getChecked());
        //listView单个条目事件监听
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder viewHodler= (ViewHolder) view.getTag();
                //切换条目上复选框的选中状态
                viewHodler.cb_selector.toggle();
                list.get(position).setChecked(viewHodler.cb_selector.isChecked());
                parent.getItemAtPosition(position);
            }
        });
        return convertView;
    }

    public  class  ViewHolder{
        public TextView tv_name;//名字
        public CheckBox cb_selector;
        public LinearLayout ll_edit;//编辑按钮
    }


}
