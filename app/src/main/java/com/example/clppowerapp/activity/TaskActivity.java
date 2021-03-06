package com.example.clppowerapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clppowerapp.ClpPowerutils.PowerConstants;
import com.example.clppowerapp.ClpPowerutils.SharedPreferenceUtils;
import com.example.clppowerapp.R;
import com.example.clppowerapp.bean.TaskYunxingdanweiBean;
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

//任务页面，jiangpan
public class TaskActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private HeaderBar headerView;
    private Spinner s_task_chengnongwang, s_task_dianyadengji, s_task_yunxingdanwei, s_task_weihubanzu;
    private String s_chengnongwang, s_dianyadengji, s_yunxingdanwei, s_weihubanzu;
    private TextView t_task_renwuleixing, t_task_zichandanwei, t_task_zichanxingzhi;
    private List<String> chengnongwang_list = new ArrayList<String>();//城农网数据
    private List<String> dianyadengji_list = new ArrayList<String>();//电压等级数据
    private List<String> yunxingdanwei_list = new ArrayList<String>();//运行单位数据
    private List<String> weihubanzu_list = new ArrayList<String>();//维护班组数据
    private Button b_task_save;
    private String danwei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_task);
        findId();
        init();
        listener();
    }


    public void findId() {
        headerView = (HeaderBar) findViewById(R.id.header);
        s_task_chengnongwang = (Spinner) findViewById(R.id.s_task_chengnongwang);
        s_task_dianyadengji = (Spinner) findViewById(R.id.s_task_dianyadengji);
        s_task_yunxingdanwei = (Spinner) findViewById(R.id.s_task_yunxingdanwei);
        s_task_weihubanzu = (Spinner) findViewById(R.id.s_task_weihubanzu);
        t_task_renwuleixing = (TextView) findViewById(R.id.t_task_renwuleixing);
        t_task_zichandanwei = (TextView) findViewById(R.id.t_task_zichandanwei);
        t_task_zichanxingzhi = (TextView) findViewById(R.id.t_task_zichanxingzhi);
        b_task_save = (Button) findViewById(R.id.b_task_save);
    }

    public void init() {
        String renwuleixing = SharedPreferenceUtils.getSharePreenceKeybyString(TaskActivity.this, "zhongya");
        t_task_renwuleixing.setText(renwuleixing);
        headerView.setTitle("任务");
        //城农网
        chengnongwang_list.add("农网");
        chengnongwang_list.add("城网");
        ArrayAdapter<String> chengnongwang_adapter = new ArrayAdapter<String>(TaskActivity.this,
                android.R.layout.simple_spinner_item, chengnongwang_list);
        s_task_chengnongwang.setAdapter(chengnongwang_adapter);

        //电压等级
        dianyadengji_list.add("交流10KV");
        dianyadengji_list.add("交流20KV");
        dianyadengji_list.add("交流6KV");
        ArrayAdapter<String> dengyadengji_adapter = new ArrayAdapter<String>(TaskActivity.this,
                android.R.layout.simple_spinner_item, dianyadengji_list);
        s_task_dianyadengji.setAdapter(dengyadengji_adapter);

        //运行单位
        yunxindanwei_data();

        //维护班组
        weihubanzu_list.add("二道江供电所");
        weihubanzu_list.add("鸭园供电所");
        weihubanzu_list.add("环城供电所");
        weihubanzu_list.add("金厂供电所");
        weihubanzu_list.add("东热供电所");
        ArrayAdapter<String> weihubanzu_adapter = new ArrayAdapter<String>(TaskActivity.this,
                android.R.layout.simple_spinner_item, weihubanzu_list);
        s_task_weihubanzu.setAdapter(weihubanzu_adapter);

    }

    public void listener() {
        b_task_save.setOnClickListener(this);
        s_task_chengnongwang.setOnItemSelectedListener(this);
        s_task_dianyadengji.setOnItemSelectedListener(this);
        s_task_yunxingdanwei.setOnItemSelectedListener(this);
        s_task_weihubanzu.setOnItemSelectedListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_task_save:
                Toast.makeText(TaskActivity.this, "数据还未保存，是否确定退出", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (view.getId()) {
            case R.id.s_task_chengnongwang://城农网
                s_chengnongwang = s_task_chengnongwang.getSelectedItem().toString();
                break;
            case R.id.s_task_dianyadengji://电压等级
                s_dianyadengji = s_task_dianyadengji.getSelectedItem().toString();
                break;
            case R.id.s_task_yunxingdanwei://运行单位
                s_yunxingdanwei = s_task_yunxingdanwei.getSelectedItem().toString();
                break;
            case R.id.s_task_weihubanzu://维护班组
                s_weihubanzu = s_task_weihubanzu.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void yunxindanwei_data() {
        Map<String, String> map = new HashMap<>();
//        map.put("dianya","中压");
//        map.put("id",SharedPreferenceUtils.getSharePreenceKeybyString(TaskActivity.this,"id"));
        VolleyRequestUtil.RequestPost(TaskActivity.this, PowerConstants.TASK_YUNXINGDANWEI, "task_yunxingdanwei", map, new VolleyListenerInterface(this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("yunxingdanwei") != null) {
                        Gson gson = new Gson();
                        TaskYunxingdanweiBean taskYunxingdanweiBean = gson.fromJson(result, TaskYunxingdanweiBean.class);
                        List<TaskYunxingdanweiBean.YunxingdanweiBean> list = taskYunxingdanweiBean.getYunxingdanwei();
                        for (int i = 0; i < list.size(); i++) {
                            danwei = list.get(i).getDanwei();
                            yunxingdanwei_list.add(danwei);
                            ArrayAdapter<String> yunxingdanwei_adapter = new ArrayAdapter<String>(TaskActivity.this,
                                    android.R.layout.simple_spinner_item, yunxingdanwei_list);
                            s_task_yunxingdanwei.setAdapter(yunxingdanwei_adapter);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });

    }

}
