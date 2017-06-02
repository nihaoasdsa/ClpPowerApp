package com.example.clppowerapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.clppowerapp.R;

import java.util.ArrayList;

//任务页面，jiangpan
public class TaskActivity extends Activity implements View.OnClickListener {
    private Spinner s_task_chengnongwang, s_task_dengyadengji, s_task_yunxingdanwei, s_task_weihubanzu;
    private String s_chengnongwang,s_dengyadengji,s_yunxingdanwei,s_weihubanzu;
    private ArrayList<String> chengnongwang_list = new ArrayList<String>();//城农网数据
    private ArrayList<String> dengyadengji_list = new ArrayList<String>();//电压等级数据
    private ArrayList<String> yunxingdanwei_list = new ArrayList<String>();//运行单位数据
    private ArrayList<String> weihubanzu_list = new ArrayList<String>();//维护班组数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        findId();
        init();
    }

    public void findId() {
//        SharedPreferenceUtils.getSharePreenceKeybyString();
        s_task_chengnongwang = (Spinner) findViewById(R.id.s_task_chengnongwang);
        s_task_dengyadengji = (Spinner) findViewById(R.id.s_task_dengyadengji);
        s_task_yunxingdanwei = (Spinner) findViewById(R.id.s_task_yunxingdanwei);
        s_task_weihubanzu = (Spinner) findViewById(R.id.s_task_weihubanzu);
    }

    public void init() {
        //城农网
        chengnongwang_list.add("农网");
        chengnongwang_list.add("城网");
        chengnongwang_list.add("农村");
        chengnongwang_list.add("城市");
        chengnongwang_list.add("特殊边缘山区");
        ArrayAdapter<String> chengnongwang_adapter = new ArrayAdapter<String>(TaskActivity.this,
                android.R.layout.simple_spinner_item, chengnongwang_list);
        s_task_chengnongwang.setAdapter(chengnongwang_adapter);
        s_task_chengnongwang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s_chengnongwang = s_task_chengnongwang.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //电压等级
        dengyadengji_list.add("交流380V(含400V)");
        dengyadengji_list.add("交流110KV");
        dengyadengji_list.add("交流66KV");
        dengyadengji_list.add("交流35KV");
        dengyadengji_list.add("交流10KV");
        ArrayAdapter<String> dengyadengji_adapter = new ArrayAdapter<String>(TaskActivity.this,
                android.R.layout.simple_spinner_item, dengyadengji_list);
        s_task_dengyadengji.setAdapter(dengyadengji_adapter);
        s_task_dengyadengji.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s_dengyadengji = s_task_dengyadengji.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //运行单位
        yunxingdanwei_list.add("国网通化市城郊供电公司（农网）");
        yunxingdanwei_list.add("国网辉南县供电公司（农网）");
        yunxingdanwei_list.add("国网柳河县供电公司（农网）");
        yunxingdanwei_list.add("国网梅河口市供电公司（农网）");
        yunxingdanwei_list.add("国网集安市供电公司（农网）");
        ArrayAdapter<String> yunxingdanwei_adapter = new ArrayAdapter<String>(TaskActivity.this,
                android.R.layout.simple_spinner_item, yunxingdanwei_list);
        s_task_yunxingdanwei.setAdapter(yunxingdanwei_adapter);
        s_task_yunxingdanwei.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s_yunxingdanwei = s_task_yunxingdanwei.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //维护班组
        weihubanzu_list.add("二道江供电所");
        weihubanzu_list.add("鸭园供电所");
        weihubanzu_list.add("环城供电所");
        weihubanzu_list.add("金厂供电所");
        weihubanzu_list.add("东热供电所");
        ArrayAdapter<String> weihubanzu_adapter = new ArrayAdapter<String>(TaskActivity.this,
                android.R.layout.simple_spinner_item, weihubanzu_list);
        s_task_weihubanzu.setAdapter(weihubanzu_adapter);
        s_task_weihubanzu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s_weihubanzu = s_task_weihubanzu.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.:
//                break;
        }
    }
}
