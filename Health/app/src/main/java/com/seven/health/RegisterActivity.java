package com.seven.health;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mob.MobSDK;
import com.seven.health.util.OkhttpUtil;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText phone;
    private EditText code;
    private Button getcode;
    private EditText password;
    private EditText checkpwd;
    private Button register;
    private TextView login;

    private String phoneNum;
    private String pwdNum;

    private SharedPreferences prefs;


    public EventHandler eh; //事件接收器
    int i=60; //倒计时计数

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == -1){
                getcode.setText(i + "s后重新获取");

            }else if(msg.what == -2){
                getcode.setText("重新发送");
                getcode.setClickable(true);
                i = 60;

            }else if(msg.what == 202){
                String res = msg.obj.toString();
                Toast.makeText(RegisterActivity.this,res,Toast.LENGTH_SHORT).show();
                if(res.equals("注册成功")){
                    //将用户数据存在本地
                    prefs = getSharedPreferences("myaccount", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("phone",phoneNum);
                    editor.putString("password",pwdNum);
                    editor.commit();

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }

            }else{
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){  //提交验证码成功

                    JsonObject user = new JsonObject();
                    user.addProperty("phone",phoneNum);
                    user.addProperty("password",pwdNum);

                    httpPost("/register",user);  //在数据库中存入用户名和密码后

                }else if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){  //获取验证码成功
                    Toast.makeText(RegisterActivity.this,"验证码已经发送",Toast.LENGTH_SHORT).show();

                }else if(result == SMSSDK.RESULT_ERROR){
                    try{
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail"); //错误描述
                        int status = object.optInt("status"); //错误代码
                        if(status > 0 && !TextUtils.isEmpty(des)){
                            Toast.makeText(RegisterActivity.this, des, Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){

                    }
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.register);

        //在onCreate方法初始化SDK
        MobSDK.init(RegisterActivity.this);

        initView();
        initEvent();
        initSMS();

    }

    //注册回调监听，放到发送和验证前注册，注意这里是子线程需要传到主线程中去操作后续提示
    private void initSMS() {
        eh = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };

        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    private void initView() {
        phone = (EditText) findViewById(R.id.register_telephone_edit);
        code = (EditText) findViewById(R.id.register_verification_code);
        getcode = (Button) findViewById(R.id.register_get_code);
        password = (EditText) findViewById(R.id.register_password_edit);
        checkpwd = (EditText) findViewById(R.id.register_check_password);
        register = (Button) findViewById(R.id.register);
        login = (TextView)findViewById(R.id.turn_login);
    }

    private void initEvent() {
        getcode.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        phoneNum = phone.getText().toString().trim();

        switch(v.getId()){
            case R.id.register_get_code:
                if(TextUtils.isEmpty(phoneNum)){
                    Toast.makeText(this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    if(checkTel(phoneNum)){
                        //调用发送短信的接口
                        //发送成功后回调event == SMSSDK.EVENT_GET_VERIFICATION_CODE
                        SMSSDK.getVerificationCode("86",phoneNum);

                        getcode.setClickable(false);
                        //开始倒计时
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for(; i>0; i--){
                                    handler.sendEmptyMessage(-1);
                                    try{
                                        Thread.sleep(1000);  //1s
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                //倒计时结束后执行
                                handler.sendEmptyMessage(-2);
                            }
                        }).start();
                    }else{
                        Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.register:
                String codeNum = code.getText().toString().trim();
                pwdNum = password.getText().toString().trim();
                String checkNum = checkpwd.getText().toString().trim();

                if(TextUtils.isEmpty(phoneNum)){
                    Toast.makeText(this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(codeNum)){
                    Toast.makeText(this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                }else if(!pwdNum.equals(checkNum)){
                    Toast.makeText(this,"两次输入的密码不相符",Toast.LENGTH_SHORT).show();

                }else{
                    if(checkTel(phoneNum)){
                        //调用验证码验证接口
                        //成功后回调event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE
                        SMSSDK.submitVerificationCode("86",phoneNum,codeNum);

                    }else{
                        Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.turn_login:
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
                break;
            default:
                break;
        }
    }

    //正则匹配手机号码
    public boolean checkTel(String tel){
        Pattern p = Pattern.compile("^[1][3|4|5|7|8][0-9]{9}$");
        Matcher matcher = p.matcher(tel);
        return matcher.matches();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在完成短信验证之后，需要销毁回调监听接口
        SMSSDK.unregisterAllEventHandler();
    }

    private void httpPost(final String url, Object obj) {

        OkhttpUtil.okPost(url, obj, handler);
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(this);
        System.exit(0);

    }
}
