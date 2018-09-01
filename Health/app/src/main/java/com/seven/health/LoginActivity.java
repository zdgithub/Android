package com.seven.health;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.health.util.OkhttpUtil;

public class LoginActivity extends AppCompatActivity {

    private TextView turning;
    private EditText etphone;
    private EditText etpassword;
    private Button btlogin;

    private SharedPreferences prefs;
    private SharedPreferences login;

    private String phoneNum;
    private String passwordNum;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    break;
                case 200: //处理get请求响应的数据
                    if(msg.obj.toString().equals("success")){
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                        //更新在本地保存的数据
                        prefs = getSharedPreferences("myaccount", Context.MODE_PRIVATE);
                        prefs.edit().putString("phone",phoneNum)
                                .putString("password",passwordNum)
                                .commit();

                        //成功登陆，进入MainActivity界面
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();

                        //本地存储用户登录成功过
                        login = getSharedPreferences("isLogin",Context.MODE_PRIVATE);
                        login.edit().putBoolean("isLogin",true).commit();

                    }else{
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case 202:
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(LoginActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.login);

        initView();
        initEvent();

    }


    private void initView() {
        etphone = (EditText) findViewById(R.id.login_telephone_edit);
        etpassword = (EditText) findViewById(R.id.login_password_edit);
        btlogin = (Button) findViewById(R.id.login);
        turning = (TextView) findViewById(R.id.turn_register);
    }

    private void initEvent() {
        turning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNum = etphone.getText().toString().trim();
                passwordNum = etpassword.getText().toString().trim();

                httpGet("/register?phone="+phoneNum+"&pwd="+passwordNum);


            }
        });
    }

    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }


    @Override
    public void onBackPressed() {

        ActivityCompat.finishAffinity(this);
        System.exit(0);

    }
}
