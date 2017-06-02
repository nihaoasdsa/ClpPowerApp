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
 * Created by 008 on 2017/6/2 0002.
 */
public class MyDialog extends Dialog implements View.OnClickListener {


    public interface DialogBtnClickListener {
        void LeftBtnOnClick(View v);

        void RightBtnOnClick(View v);
    }

    private Context context;
    private DialogBtnClickListener listener;
    private TextView tv;
    private String titleStr;
    private String tvStr;
    private String leftBtnStr;
    private String rightBtnStr;
    private TextView title;
    private Button btn_ok, btn_cancel;

    /**
     * @param context
     * @param titleStr    标题
     * @param tvStr       提示内容
     * @param leftBtnStr  左边按钮文字
     * @param rightBtnStr 右边按钮文字
     * @param listener    左右按钮点击事件
     */
    public MyDialog(Context context, String tvStr,
                    String leftBtnStr, String rightBtnStr,
                    DialogBtnClickListener listener) {
        super(context, R.style.alertdialog);
        this.context = context;
        this.listener = listener;
        this.titleStr = titleStr;
        this.tvStr = tvStr;
        this.leftBtnStr = leftBtnStr;
        this.rightBtnStr = rightBtnStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alart);
        initView();
    }

    private void initView() {
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        btn_cancel.setText(leftBtnStr);
        if (InputTextCheck.isEmpty(leftBtnStr)) {
            btn_cancel.setVisibility(View.GONE);
        }
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        btn_ok.setText(rightBtnStr);
        if (InputTextCheck.isEmpty(rightBtnStr)) {
            btn_ok.setVisibility(View.GONE);
        }
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(tvStr);
        setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                listener.RightBtnOnClick(v);
                dismiss();
                break;
            case R.id.btn_cancel:
                listener.LeftBtnOnClick(v);
                dismiss();
                break;
        }
    }
}
