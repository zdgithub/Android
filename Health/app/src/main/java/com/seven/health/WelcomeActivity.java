package com.seven.health;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/8/17.
 */

public class WelcomeActivity extends AppCompatActivity{
    Handler handler;
    private SharedPreferences setting;
    private SharedPreferences login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.welcome_page);
        handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                //跳转到下一个页面
                goTonext();
            }
        };
        handler.postDelayed(r, 2000);
    }


    //判断是否第一次登录
    private void goTonext(){
        //退出全屏模式
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setting = getSharedPreferences("isFirstRun", Context.MODE_PRIVATE);

        //第一次启动时默认为true,之后改为false
        Boolean isfirst = setting.getBoolean("isFirst",true);
        //Log.e("isFirst1 = ",isfirst.toString());
        //setting.edit().putBoolean("isFirst",true).commit();

        login = getSharedPreferences("isLogin",Context.MODE_PRIVATE);
        Boolean islogin = login.getBoolean("isLogin",false);



        if(isfirst){
            setting.edit().putBoolean("isFirst", false).commit();
            startActivity(new Intent(WelcomeActivity.this,RegisterActivity.class));
        }else if(islogin){  //用户登录成功过
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        }else{  //用户还没登录
            startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
        }

        finish();


    }
}
