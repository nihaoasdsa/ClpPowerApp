package com.example.clppowerapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.ClpPowerAllInforAdapter;

//主页面
public class MainActivity extends Activity {
    private ListView lv;
  private ClpPowerAllInforAdapter allInforAdapter;
    private List<String> list;
    private int checkNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initdata();
    }
    private void initdata(){
        list=new ArrayList<>();
     lv= (ListView) findViewById(R.id.lv_infor);
        for (int i=0;i<30;i++){
            list.add("中压"+ ""+i);
        }
        allInforAdapter=new ClpPowerAllInforAdapter(this,list);
        lv.setAdapter(allInforAdapter);
        // 绑定listView的监听器
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
                ClpPowerAllInforAdapter.ViewHolder holder = (ClpPowerAllInforAdapter.ViewHolder) arg1.getTag();
                // 改变CheckBox的状态
                holder.cb_selector.toggle();
                // 将CheckBox的选中状况记录下来
                ClpPowerAllInforAdapter.getIsSelector().put(arg2, holder.cb_selector.isChecked());
                // 调整选定条目
                if (holder.cb_selector.isChecked() == true) {
                    checkNum++;
                } else {
                    checkNum--;
                }
                Toast.makeText(MainActivity.this,"已选中" + checkNum + "项",Toast.LENGTH_SHORT).show();

            }
        });

}
}
