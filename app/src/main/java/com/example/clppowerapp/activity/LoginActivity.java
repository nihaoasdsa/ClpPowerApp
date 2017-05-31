package com.example.clppowerapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.clppowerapp.R;
import com.example.clppowerapp.view.HeaderBar;

//登录页面，jiangpan
public class LoginActivity extends Activity {
    private HeaderBar headerView;
    private AutoCompleteTextView et_accountname;
    private EditText et_pwd;
    private String accountname = "";
    // --------登录相关------
    private String pwd = "";
 //   private RequestParams params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        doLogin();
    }
    private void init() {
        headerView = (HeaderBar) findViewById(R.id.header);
        headerView.setTitle("登录");
        headerView.disappear();
        et_accountname = (AutoCompleteTextView)findViewById(R.id.login_et_account);
        et_pwd=(EditText)findViewById(R.id.login_et_pwd);
    }
    private void doLogin() {
        accountname = et_accountname.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();
    }

}
