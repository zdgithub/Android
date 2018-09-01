package com.seven.health.aboutpersonal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seven.health.R;
import com.seven.health.bean.User;
import com.seven.health.util.OkhttpUtil;

/**
 * Created by HP on 2018/8/21.
 */

public class PersonalInformationActivity extends AppCompatActivity {

    private TextView name;
    private TextView sex;
    private TextView birthday;
    private TextView tel;
    private TextView email;
    private Button edit;

    private SharedPreferences prefs;

    private LayoutInflater inflater;
    private User user = new User();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    break;
                case 200: //处理get请求响应的数据
                    Gson gson = new Gson();
                    user = gson.fromJson(msg.obj.toString(),User.class);
                    initData();

                    break;
                case 202:
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(PersonalInformationActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.personal_information);

        inflater=getLayoutInflater();

        initView();

        prefs = getSharedPreferences("myaccount", Context.MODE_PRIVATE);
        String savedPhone = prefs.getString("phone","");
        httpGet("/profile?phone="+savedPhone);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                Intent intent=new Intent(PersonalInformationActivity.this,PersonalInfoEditActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    private void initView(){
        name=(TextView)findViewById(R.id.personal_infor_name);
        sex=(TextView)findViewById(R.id.personal_infor_sex);
        birthday=(TextView)findViewById(R.id.personal_infor_birthday);
        tel=(TextView)findViewById(R.id.personal_infor_phone);
        email=(TextView)findViewById(R.id.personal_infor_email);
        edit=(Button)findViewById(R.id.person_infor_edit);
    }


    private void initData(){
        if(user.getNickname()!=null){
            name.setText(user.getNickname());
        }
        if(user.getSex()!=null){
            sex.setText(user.getSex());
        }
        if(user.getBirthday()!=null){
            birthday.setText(user.getBirthday());
        }
        if(user.getPhone()!=null){
            tel.setText(user.getPhone());
        }
        if(user.getEmail()!=null){
            email.setText(user.getEmail());
        }
    }



    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }

    @Override
    public void onBackPressed(){
//        Intent intent=new Intent(PersonalInformationActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
//        intent.putExtra("id",3);
//        startActivity(intent);
        this.finish();
    }

}
