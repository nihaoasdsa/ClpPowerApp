package com.example.clppowerapp.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clppowerapp.R;

//公共title,jiangpan
public class HeaderBar extends LinearLayout {
    Context context;
    /**
     * 返回的按钮
     */
    private TextView img;
    /**
     * 头部的信息
     */
    private TextView title;
    /**
     * 退出程序的按钮
     */
    private ImageView otherimg;
    private View line;
    private View extras;//向下的箭头
    private OnClickListener extrasOnClickListener;

    public HeaderBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.headerbar, this, true);
        this.context = context;
        init();
    }

    public HeaderBar(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.headerbar, this, true);
        this.context = context;
        init();
    }

    public HeaderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.headerbar, this, true);
        this.context = context;
        init();
    }

    public void init() {
        line = findViewById(R.id.headbar_line);
        img = (TextView) findViewById(R.id.iv_return_headbar);
        title = (TextView) findViewById(R.id.headbar_selectadd_title);
        otherimg = (ImageView) findViewById(R.id.headbar_img_right);
        extras = findViewById(R.id.extras);
        // 添加点击事件
        img.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Activity activity = (Activity) HeaderBar.this.context;
                activity.finish();
            }
        });
        if (img.getVisibility() != View.VISIBLE)
            line.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) HeaderBar.this.context;
                    activity.finish();
                }
            });
    }


    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setTitleSize(float size) {
        this.title.setTextSize(size);
    }

    public void setTitleColor(int color) {
        this.title.setTextColor(color);
    }

    public void disappear() {
        img.setVisibility(GONE);
        line.setVisibility(GONE);
    }

    /**
     * 替换返回按钮的图片
     *
     * @param resid
     */
    public void setImage(int resid) {
        this.img.setText(resid);
    }

    /**
     * 替换退出按钮的图片
     *
     * @param resId
     */
    public void setOtherimgDrawable(int resId) {
        this.otherimg.setImageResource(resId);
    }

    /**
     * 设置退出按钮的显示效果:可见
     */
    public void setOtherimgVisible() {
        this.otherimg.setVisibility(VISIBLE);
    }

    /**
     * 设置返回按钮的显示效果:不可见
     */
    public void setImageinvisible() {
        this.img.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置退出按钮的事件监听
     *
     * @param listener
     */
    public void setOtherimgClickListener(OnClickListener listener) {
        this.otherimg.setOnClickListener(listener);
    }

    /**
     * 设置向下箭头的监听
     *
     * @param extrasOnClickListener
     */
    public void setExtrasOnClickListener(OnClickListener extrasOnClickListener) {
        extras.setOnClickListener(extrasOnClickListener);
    }

    /**
     * 设置向下箭头是否显示
     *
     * @param isShow
     */
    public void setExtrasShow(boolean isShow) {
        if (isShow)
            extras.setVisibility(View.VISIBLE);
        else
            extras.setVisibility(View.GONE);
    }

    /**
     * 设置返回按钮的点击事件
     * @param listener
     */
    public void setBack(OnClickListener listener){
        line.setOnClickListener(listener);

    }

    // onFinishInflate 当View中所有的子控件均被映射成xml后触发
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

}