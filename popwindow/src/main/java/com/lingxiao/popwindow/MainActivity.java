package com.lingxiao.popwindow;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button_qq_bottom)
    Button btBottom;
    private PopupWindow mPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_qq_bottom)
    public void showBottomPop(){
        showPopupWindow();
    }

    @OnClick(R.id.button_simple_top)
    public void showSimpleTop(View view){
        PopwindowUtil popwindowUtil = new PopwindowUtil
                .PopupWindowBuilder(this)
                .setView(R.layout.pop_simple)
                .setFocusable(true)
                .setTouchable(true)
                .setOutsideTouchable(true)
                .create();
        popwindowUtil.showAsDropDown(view,0,-(view.getHeight()+popwindowUtil.getmHeight()+10));
    }

    @OnClick(R.id.button_simple_bottom)
    public void showSimpleBottom(View view){
        final PopwindowUtil popwindowUtil = new PopwindowUtil
                .PopupWindowBuilder(this)
                .setView(R.layout.pop_simple)
                .setFocusable(true)
                .setTouchable(true)
                .setOutsideTouchable(true)
                .create();
        popwindowUtil.showAsDropDown(view,0,10);

        popwindowUtil.setOnItemClick(R.id.tv_simple_one, new PopwindowUtil.ItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Toast.makeText(getApplicationContext(),"我是第一个",Toast.LENGTH_SHORT).show();
                popwindowUtil.dissmiss();
            }
        });
    }
    private void showPopupWindow() {
        backgroundAlpha(0.5f);
        //设置contentView
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop_pp_bottom, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //动画效果
        mPopWindow.setAnimationStyle(R.style.contextMenuAnim);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        TextView tv1 = contentView.findViewById(R.id.pop_update);
        TextView tv2 = contentView.findViewById(R.id.pop_feedback);
        TextView tv3 = contentView.findViewById(R.id.pop_quit);
        TextView tv4 = contentView.findViewById(R.id.pop_cancel);
        //显示PopupWindow
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        View rootview = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.activity_main, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}
