package com.example.clppowerapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.clppowerapp.ClpPowerutils.InputTextCheck;
import com.example.clppowerapp.ClpPowerutils.PowerConstants;
import com.example.clppowerapp.ClpPowerutils.SharedPreferenceUtils;
import com.example.clppowerapp.R;
import com.example.clppowerapp.view.HeaderBar;
import com.example.clppowerapp.view.VolleyListenerInterface;
import com.example.clppowerapp.view.VolleyRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//登录页面，jiangpan
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private HeaderBar headerView;
    private EditText et_accountname;
    private EditText et_pwd;
    private String accountname = "";
    // --------登录相关------
    private String pwd = "";
    private Button login_land;
    private Button button_clear_account, button_clear_psw;//清除按钮
    private CheckBox rem_pw;//记住密码
    private SharedPreferences sp;
    /**
     * 用于加载的进度跳
     */
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        doLogin();
    }

    private void init() {
        //获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        headerView = (HeaderBar) findViewById(R.id.header);
        headerView.setTitle("登录");
        headerView.disappear();
        et_accountname = (EditText) findViewById(R.id.account);
        et_pwd = (EditText) findViewById(R.id.password);
        login_land = (Button) findViewById(R.id.login_land);
        button_clear_account = (Button) findViewById(R.id.button_clear_account);
        button_clear_psw = (Button) findViewById(R.id.button_clear_psw);
        rem_pw = (CheckBox) findViewById(R.id.cb_mima);
        login_land.setOnClickListener(this);
        button_clear_psw.setOnClickListener(this);
        button_clear_account.setOnClickListener(this);
        //et监听事件
        et_accountname.addTextChangedListener(mLoginInputWatcher);
        et_pwd.addTextChangedListener(mPassWordInputWatcher);
        //判断记住多选框的状态
        if (sp.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            rem_pw.setChecked(true);
            et_accountname.setText(sp.getString("USER_NAME", ""));
            et_pwd.setText(sp.getString("PASSWORD", ""));

        }
        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rem_pw.isChecked()) {
                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();
                } else {
                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();
                }

            }
        });
    }

    //et的监听事件
    private TextWatcher mLoginInputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (et_accountname.getText().toString() != null && et_accountname.getText().toString().equals("")) {
                button_clear_account.setVisibility(View.INVISIBLE);//显示
            } else {
                button_clear_account.setVisibility(View.VISIBLE);//隐藏

            }
        }
    };
    private TextWatcher mPassWordInputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (et_pwd.getText().toString() != null && et_pwd.getText().toString().equals("")) {
                button_clear_psw.setVisibility(View.INVISIBLE);//隐藏
            } else {
                button_clear_psw.setVisibility(View.VISIBLE);//显示
            }
        }
    };

    private void doLogin() {
        accountname = et_accountname.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_land:
                accountname = et_accountname.getText().toString().trim();
                pwd = et_pwd.getText().toString().trim();
                if (!accountname.equals("") && !pwd.equals("")) {
                    //多选框
                    if (rem_pw.isChecked()) {
                        //记住用户名和密码
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", accountname);
                        editor.putString("PASSWORD", pwd);
                        editor.commit();

                    }
                    dialog = ProgressDialog.show(LoginActivity.this, null,
                            "正在登录...", true, true);
                    LoginRes();
                } else {
                    //空状态判断
                    if (InputTextCheck.isEmpty(accountname)) {
                        Toast.makeText(LoginActivity.this, PowerConstants.Per_NOT_NULL, Toast.LENGTH_LONG).show();

                        //用户名
                    } else if (InputTextCheck.isEmpty(pwd)) {
                        Toast.makeText(LoginActivity.this, PowerConstants.PASS_WORD, Toast.LENGTH_LONG).show();
                    } else {

                    }

                }

                break;
        }
    }

    private void LoginRes() {
        Map<String, String> map = new HashMap<>();
        map.put("username",accountname);
        map.put("password",pwd);
        VolleyRequestUtil.RequestPost(this, PowerConstants.LOGIN, "Login", map, new VolleyListenerInterface(LoginActivity.this, VolleyListenerInterface.mListener, VolleyListenerInterface.mErrorListener) {
            @Override
            public void onMySuccess(String result) {
                Log.e("resss", "onMySuccess: " + result);
                try {
                    JSONObject object=new JSONObject(result);
                    if( object.getString("result").equals("0")){
                        Toast.makeText(LoginActivity.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                    }else if( object.getString("result").equals("1")){
                        dialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        SharedPreferenceUtils.setSharedPreference("id",object.getString("id"),LoginActivity.this);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(LoginActivity.this,PowerConstants.NET_ERROR,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Toast.makeText(LoginActivity.this,"服务器错误！",Toast.LENGTH_SHORT).show();
            }
        });


    }


}
