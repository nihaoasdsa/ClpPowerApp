package com.example.clppowerapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clppowerapp.R;
import com.example.clppowerapp.bean.Bean;

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
    private List<Bean> list = new ArrayList<Bean>();
    // 上下文
    private Context mContext;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> isCheck = new HashMap<Integer, Boolean>();

    // 构造方法
    public ClpPowerAllInforAdapter(Context mContext) {
        super();
        this.mContext = mContext;
        // 默认为不选中
        initCheck(false);
    }

    // 初始化map集合
    public void initCheck(boolean flag) {
        // map集合的数量和list的数量是一致的
        for (int i = 0; i < list.size(); i++) {
            // 设置默认的显示
            isCheck.put(i, flag);
        }
    }

    // 设置数据
    public void setData(List<Bean> data) {
        this.list = data;
    }

    // 添加数据
    public void addData(Bean bean) {
        // 下标 数据
        list.add(0, bean);
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
            convertView=LayoutInflater.from(mContext).inflate(R.layout.home_list_item,null);
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
        Bean bean = list.get(position);

        viewHolder.tv_name.setText(bean.getTitle().toString());
        // 勾选框的点击事件
        viewHolder.cb_selector
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        // 用map集合保存
                        isCheck.put(position, isChecked);
                      //  if ()
                        Log.e("数据1",position+"---------");
                    }
                });
        // 设置状态
        if (isCheck.get(position) == null) {
            isCheck.put(position, false);
        }
        viewHolder.cb_selector.setChecked(getMap().get(position));
        return convertView;
    }

    public  class  ViewHolder{
        public TextView tv_name;//名字
        public CheckBox cb_selector;
        public LinearLayout ll_edit;//编辑按钮
    }
    // 全选按钮获取状态
    public Map<Integer, Boolean> getMap() {
        // 返回状态
        return isCheck;
    }

    // 删除一个数据
    public void removeData(int position) {
        list.remove(position);
    }

}
