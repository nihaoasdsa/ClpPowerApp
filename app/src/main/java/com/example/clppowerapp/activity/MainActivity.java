package com.example.clppowerapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.clppowerapp.R;
import com.example.clppowerapp.adapter.ClpPowerAllInforAdapter;
import com.example.clppowerapp.bean.Bean;
import com.example.clppowerapp.view.HeaderBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//主页面
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv;
    private ClpPowerAllInforAdapter allInforAdapter;
    private List<Bean> list;
    private int checkNum;
    private LinearLayout ll_detele;//删除
    private LinearLayout ll_add;//增加
    private TextView btn_select_all;
   private Button cb_all;
    private HeaderBar headerView;
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initdata();
    }
    //初始化数据信息
    private void initdata() {
        list = new ArrayList<>();
        headerView = (HeaderBar) findViewById(R.id.header);
        headerView.setTitle("电力数据采集");
        headerView.disappear();
        lv = (ListView) findViewById(R.id.lv_infor);
        ll_add = (LinearLayout) findViewById(R.id.ll_add);
        ll_detele = (LinearLayout) findViewById(R.id.ll_detele);
        btn_select_all = (TextView) findViewById(R.id.btn_select_all);
        cb_all=(Button) findViewById(R.id.cb_all);
        ll_add.setOnClickListener(this);
        ll_detele.setOnClickListener(this);
        btn_select_all.setOnClickListener(this);
        // 默认显示的数据
        List<Bean> list = new ArrayList<Bean>();
        list.add(new Bean("新数据1"));
        list.add(new Bean("新数据1"));
        list.add(new Bean("新数据1"));
        list.add(new Bean("新数据1"));
        allInforAdapter = new ClpPowerAllInforAdapter(this);
        allInforAdapter.setData(list);
        lv.setAdapter(allInforAdapter);
        // 绑定listView的监听器
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // 判断view是否相同
                if (arg1.getTag() instanceof ClpPowerAllInforAdapter.ViewHolder) {
                    // 如果是的话，重用
                    ClpPowerAllInforAdapter.ViewHolder holder = (ClpPowerAllInforAdapter.ViewHolder) arg1.getTag();
                    // 自动触发
                    holder.cb_selector.toggle();

                }

            }
        });
    }
    private void PopWindowData(){

    }



    //监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 添加数据
            case R.id.ll_add:
            //    allInforAdapter.addData(new Bean("新增数据"));
                // 通知刷新适配器
             //   allInforAdapter.notifyDataSetChanged();
                break;
            // 全选数据
            case R.id.btn_select_all:
                // 全选——全不选
                Map<Integer, Boolean> isCheck = allInforAdapter.getMap();
                if (btn_select_all.getText().equals("全选")) {
                    allInforAdapter.initCheck(true);
                    Log.e("全选数据",isCheck+"");
                    // 通知刷新适配器
                    allInforAdapter.notifyDataSetChanged();
                    btn_select_all.setText("全不选");
                    cb_all.setBackgroundDrawable(getResources().getDrawable(R.mipmap.check_true));
                } else if (btn_select_all.getText().equals("全不选")) {
                    allInforAdapter.initCheck(false);
                    // 通知刷新适配器
                    allInforAdapter.notifyDataSetChanged();
                    btn_select_all.setText("全选");
                    cb_all.setBackgroundDrawable(getResources().getDrawable(R.mipmap.check_false));
                }
                break;
            // 删除数据
            case R.id.ll_detele:
                // 拿到所有数据
                Map<Integer, Boolean> isCheck_delete = allInforAdapter.getMap();
                // 获取到条目数量，map.size = list.size,所以
                int count = allInforAdapter.getCount();
                // 遍历
                for (int i = 0; i < count; i++) {
                    // 删除有两个map和list都要删除 ,计算方式
                    int position = i - (count - allInforAdapter.getCount());
                    // 判断状态 true为删除
                    if (isCheck_delete.get(i) != null && isCheck_delete.get(i)) {
                        // listview删除数据
                        isCheck_delete.remove(i);
                        allInforAdapter.removeData(position);
                    }
                }
                allInforAdapter.notifyDataSetChanged();
                break;

        }
    }
}