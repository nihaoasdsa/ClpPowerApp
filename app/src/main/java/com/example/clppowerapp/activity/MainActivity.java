package com.example.clppowerapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.clppowerapp.ClpPowerutils.PowerConstants;
import com.example.clppowerapp.ClpPowerutils.SharedPreferenceUtils;

import com.example.clppowerapp.R;
import com.example.clppowerapp.adapter.ClpPowerAllInforAdapter;
import com.example.clppowerapp.bean.Bean;
import com.example.clppowerapp.bean.HomeBean;
import com.example.clppowerapp.common.MyDialog;
import com.example.clppowerapp.common.NotCancelDialog;
import com.example.clppowerapp.view.HeaderBar;
import com.example.clppowerapp.view.VolleyListenerInterface;
import com.example.clppowerapp.view.VolleyRequestUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//主页面
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv;
    private ClpPowerAllInforAdapter allInforAdapter;
    private List<HomeBean.XianlumingxiBean> list;
    private int checkNum;
    private LinearLayout ll_detele;//删除
    private LinearLayout ll_add;//增加
   private CheckBox cb_all;
    private HeaderBar headerView;
    private PopupWindow popupWindow;
   private  List<HomeBean.XianlumingxiBean> data=new ArrayList<>();

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

        cb_all=(CheckBox) findViewById(R.id.che_all);
        ll_add.setOnClickListener(this);
        ll_detele.setOnClickListener(this);
        HomeData();
        /**
         * 全选复选框设置事件监听
         */
        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (data.size()!=0) {//判断列表中是否有数据
                    if (isChecked) {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setChecked(true);
                        }
                        //通知适配器更新UI
                        allInforAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setChecked(false);
                        }
                        //通知适配器更新UI
                        allInforAdapter.notifyDataSetChanged();
                    }
                }else{//若列表中没有数据则隐藏全选复选框
                    cb_all.setVisibility(View.GONE);
                }
            }
        });
    }
    private void HomeData() {
        Map<String, String> map = new HashMap<>();
        map.put("dianya", "中压");
        map.put("id", SharedPreferenceUtils.getSharePreenceKeybyString(MainActivity.this, "id"));
        VolleyRequestUtil.RequestPost(MainActivity.this, PowerConstants.HOME, "home", map, new VolleyListenerInterface(this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("xianlumingxi") != null) {
                        Gson gson = new Gson();
                        HomeBean homeBean = gson.fromJson(result, HomeBean.class);
                        data = homeBean.getXianlumingxi();
                        allInforAdapter = new ClpPowerAllInforAdapter(MainActivity.this, lv, data);
                        lv.setAdapter(allInforAdapter);
                    }else {
                        Toast.makeText(MainActivity.this,PowerConstants.NOTHING,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(MainActivity.this,PowerConstants.NET_ERROR,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void PopWindowData(Context context, View view){
        if (popupWindow==null) {
            //得到LayoutInflater对象
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View vpopwindow = inflater.inflate(R.layout.popwindow_layout, null, false);
         TextView   group_menu1_playgame= (TextView) vpopwindow.findViewById(R.id.group_menu1_playgame);
            group_menu1_playgame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this,TaskActivity.class);
                    SharedPreferenceUtils.setSharedPreference("zhongya","中压",MainActivity.this);
                    startActivity(intent);
                }
            });
            popupWindow = new PopupWindow(vpopwindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        //设置popwindow的背景颜色
       WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.alpha=0.4f;//0~1的数值
        getWindow().setAttributes(lp);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation((View)view.getParent(), Gravity.CENTER, 0, 0);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener(){

            //在dismiss中恢复透明度
            public void onDismiss(){
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    //监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 添加数据
            case R.id.ll_add:
                PopWindowData(MainActivity.this,v);
                break;
            // 全选数据

            // 删除数据
            case R.id.ll_detele:
                //创建一个要删除内容的集合，不能直接在数据源data集合中直接进行操作，否则会报异常
                List<HomeBean.XianlumingxiBean> deleSelect = new ArrayList<>();

                //把选中的条目要删除的条目放在deleSelect这个集合中
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getChecked()) {
                        deleSelect.add(data.get(i));
                    }
                }
                //判断用户是否选中要删除的数据及是否有数据
                if (deleSelect.size() != 0 && data.size() != 0) {

                    //从数据源data中删除数据
                    data.removeAll(deleSelect);
                    //把deleSelect集合中的数据清空
                    deleSelect.clear();
                    //把全选复选框设置为false
                    cb_all.setChecked(false);
                    //通知适配器更新UI
                    allInforAdapter.notifyDataSetChanged();
                } else if (data.size() == 0) {
                    Toast.makeText(MainActivity.this, "没有要删除的数据", Toast.LENGTH_SHORT).show();
                } else if (deleSelect.size() == 0) {
                    ShowDialog();
                }


                break;

        }
    }
       private  void ShowDialog(){
        //判断用户是否选中要删除的数据及是否有数据
           new MyDialog(MainActivity.this, "请选中要删选的工程任务", "取消", "确定", new MyDialog.DialogBtnClickListener() {
               @Override
               public void LeftBtnOnClick(View v) {

               }

               @Override
               public void RightBtnOnClick(View v) {

               }
           });
//            new NotCancelDialog(MainActivity.this, "请选中要删选的工程任务", "确定", new NotCancelDialog.DialogBtnClickListener() {
//
//                @Override
//                public void RightBtnOnClick(View v) {
//
//                }
//            });
    }
}