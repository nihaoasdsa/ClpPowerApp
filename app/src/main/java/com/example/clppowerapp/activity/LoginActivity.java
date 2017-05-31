package com.example.clppowerapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.clppowerapp.R;
import com.example.clppowerapp.view.HeaderBar;

//登录页面，jiangpan
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private HeaderBar headerView;
    private EditText et_accountname;
    private EditText et_pwd;
    private String accountname = "";
    // --------登录相关------
    private String pwd = "";
    private Button login_land;
 //   private RequestParams params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_login);
        init();
        doLogin();
    }
    private void init() {
        headerView = (HeaderBar) findViewById(R.id.header);
        headerView.setTitle("登录");
        headerView.disappear();
        et_accountname = (EditText)findViewById(R.id.account);
        et_pwd=(EditText)findViewById(R.id.password);
        login_land= (Button) findViewById(R.id.login_land);
        login_land.setOnClickListener(this);
    }
    private void doLogin() {
        accountname = et_accountname.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.login_land:
               Intent intent=new Intent(LoginActivity.this,MainActivity.class);
               startActivity(intent);
            break;
        }
    }
}
