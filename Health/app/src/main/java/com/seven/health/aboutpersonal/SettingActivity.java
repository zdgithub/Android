package com.seven.health.aboutpersonal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seven.health.LoginActivity;
import com.seven.health.R;

/**
 * Created by HP on 2018/8/20.
 */

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        setContentView(R.layout.personal_setting);
        Button exitsoft;
        TextView version;
        version = (TextView) findViewById(R.id.aboutversion);
        exitsoft =(Button)findViewById(R.id.exit);

        version.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AboutsoftActivity.class);
                startActivity(intent);

            }
        });

        exitsoft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //用户退出登录状态
                login = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                login.edit().putBoolean("isLogin",false).commit();

                Intent intent1 = new Intent(SettingActivity.this, LoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
                startActivity(intent1);
                finish();

            }
        });

    }

}
