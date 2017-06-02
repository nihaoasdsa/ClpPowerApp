package com.example.clppowerapp.activity;

import android.app.Activity;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.clppowerapp.R;
import com.example.clppowerapp.adapter.ClpPowerAllInforAdapter;
import com.example.clppowerapp.bean.Bean;
import com.example.clppowerapp.common.MyDialog;
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
        list.add(new Bean("新数据2"));
        list.add(new Bean("新数据3"));
        list.add(new Bean("新数据4"));
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
    private void PopWindowData(Context context, View view){
        if (popupWindow==null) {
            //得到LayoutInflater对象
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View vpopwindow = inflater.inflate(R.layout.popwindow_layout, null, false);

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
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
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
                final Map<Integer, Boolean> isCheck_delete = allInforAdapter.getMap();

                Log.e("数据1",allInforAdapter.getMap()+"---------");
                    // 获取到条目数量，map.size = list.size,所以
                    int count = allInforAdapter.getCount();
                    // 遍历
                    for (int i = 0; i < count; i++) {


                        // 删除有两个map和list都要删除 ,计算方式
                        int position = i - (count - allInforAdapter.getCount());
                        // 判断状态 true为删除
                        if (isCheck_delete.get(i) != null && isCheck_delete.get(i)) {
                            Log.e("数据",i+"---------");
                            Log.e("数据",position+"---------");
//                            new   MyDialog(MainActivity.this, "请选择要删除任务工程项", "取消", "确定", new MyDialog.DialogBtnClickListener() {
//                        @Override
//                        public void LeftBtnOnClick(View v) {
//
//                        }
//                        @Override
//                        public void RightBtnOnClick(View v) {
//                        }
//
//
//                    }).show();
                            // listview删除数据
                        //    isCheck_delete.remove(i);
                         //   allInforAdapter.removeData(position);
                        }
                    }
                    allInforAdapter.notifyDataSetChanged();


                break;

        }
    }
    //删除选择的数据
    private void Delete(){



    }
}