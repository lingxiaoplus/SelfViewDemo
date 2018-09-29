package com.widget.lingxiao.selfwidget;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.widget.lingxiao.selfwidget.anim.RippleAnimation;

public class MainActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private int position = 1;
    private int colorAccent = Color.parseColor("#FF4081");
    private int colorWhite = Color.parseColor("#FFFFFF");
    private ColorDrawable drawableAccent,drawableWhite;
    private Button colorFullBtn,huajiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawableAccent = new ColorDrawable(colorAccent);
        drawableWhite = new ColorDrawable(colorWhite);
        final ConstraintLayout layout = findViewById(R.id.cons_root);
        mActionBar = getSupportActionBar();
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RippleAnimation.create(v).setDuration(3000).start();
                if (position == 1){
                    layout.setBackgroundColor(getApplicationContext()
                            .getResources()
                            .getColor(R.color.colorAccent));
                    if (null != mActionBar){
                        mActionBar.setBackgroundDrawable(drawableAccent);
                    }
                    changeColor(R.color.colorAccent);
                    position = 2;

                }else {
                    layout.setBackgroundColor(getApplicationContext()
                            .getResources()
                            .getColor(R.color.white));
                    if (null != mActionBar){
                        mActionBar.setBackgroundDrawable(drawableWhite);
                    }
                    changeColor(R.color.white);
                    position = 1;
                }

            }
        });
        colorFullBtn = findViewById(R.id.button_circle);
        colorFullBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ColorFullActivity.class));
            }
        });
        huajiBtn = findViewById(R.id.button_huaji);
        huajiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HuaJiActivity.class));
            }
        });
    }

    private void changeColor(int color){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(color));
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
