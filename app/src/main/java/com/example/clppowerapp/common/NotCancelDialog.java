package com.example.clppowerapp.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.clppowerapp.ClpPowerutils.InputTextCheck;
import com.example.clppowerapp.R;

/**
 *  Created by 008 on 2017/6/5 0005.
 * */



public class NotCancelDialog extends Dialog implements View.OnClickListener{
    public interface DialogBtnClickListener {
        void RightBtnOnClick(View v);
    }

    private Context context;
    private DialogBtnClickListener listener;
    private TextView tv;
    private String titleStr;
    private String tvStr;
    private String rightBtnStr;
    private TextView tv_prompt;
    private Button btn_ok;

/**
     * @param context
     * @param titleStr    标题
     * @param tvStr       提示内容
     * @param rightBtnStr 按钮文字
     * @param listener    按钮点击事件

*/
    public NotCancelDialog(Context context, String title,String tvStr,
                     String rightBtnStr,
                    DialogBtnClickListener listener) {
        super(context, R.style.alertdialog);
        this.context = context;
        this.listener = listener;
        this.titleStr = title;
        this.tvStr = tvStr;
        this.rightBtnStr = rightBtnStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_cancel_dialog);
        initView();
    }

    private void initView() {
        btn_ok = (Button) findViewById(R.id.btn_ok1);
        btn_ok.setOnClickListener(this);
        btn_ok.setText(rightBtnStr);
        if (InputTextCheck.isEmpty(rightBtnStr)) {
            btn_ok.setVisibility(View.GONE);
        }
        tv = (TextView) findViewById(R.id.tv_not_name);
        tv_prompt= (TextView) findViewById(R.id.tv_prompt);
        tv_prompt.setText(titleStr);
        tv.setText(tvStr);
        setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok1:
                listener.RightBtnOnClick(v);
                dismiss();
                break;

        }
    }
}
