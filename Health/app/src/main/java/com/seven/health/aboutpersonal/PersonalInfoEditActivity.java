package com.seven.health.aboutpersonal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.health.R;
import com.seven.health.bean.User;
import com.seven.health.util.OkhttpUtil;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/8/25.
 */

public class PersonalInfoEditActivity extends AppCompatActivity {

    private EditText name;
    private TextView tel;  //电话号码无法修改
    private EditText email;

    private RadioGroup sex;
    private RadioButton man;
    private RadioButton woman;
    private RadioButton rb;

    private Button birth;
    private TextView bir;

    private Button submit;
    private LayoutInflater inflater;
    private User user = new User();

    private SharedPreferences prefs;
    private SharedPreferences nickprefs;

    private String names,tels,emails,sexes;
    private int year,month,day;
    private Calendar cal;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    break;
                case 200:
                    break;
                case 202:
                    Toast.makeText(PersonalInfoEditActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(PersonalInfoEditActivity.this,PersonalInformationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
                    startActivity(intent);
                    PersonalInfoEditActivity.this.finish();

                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(PersonalInfoEditActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.personal_information_edit);

        inflater=getLayoutInflater();

        user = (User) getIntent().getSerializableExtra("user");

        //获取当前日期
        getDate();

        initView();


        initEvent();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

    }

    private void initView(){
        name=(EditText)findViewById(R.id.personal_name_edit);
        tel= (TextView) findViewById(R.id.personal_phone);
        email=(EditText)findViewById(R.id.personal_email_edit);

        sex=(RadioGroup)findViewById(R.id.personal_sex_select);
        man=(RadioButton)findViewById(R.id.personal_man);
        woman=(RadioButton)findViewById(R.id.personal_woman);
        birth = (Button) findViewById(R.id.select_birth);
        bir = (TextView) findViewById(R.id.tv_birth);

        //tel.setText("666");

        submit=(Button)findViewById(R.id.person_infor_submit);

        if(user.getNickname()!=null){
            name.setText(user.getNickname());
        }
        if(user.getBirthday()!=null){
            bir.setText(user.getBirthday());
        }
        if(user.getPhone()!=null){
            tel.setText(user.getPhone());
        }
        if(user.getEmail()!=null){
            email.setText(user.getEmail());
        }
        if(user.getSex()!=null){
            if(user.getSex().equals("男")){
                sex.check(man.getId());
            }else if(user.getSex().equals("女")){
                sex.check(woman.getId());
            }
        }

        rb = (RadioButton) findViewById(sex.getCheckedRadioButtonId());


    }

    private void initEvent() {

        //获取性别
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                rb = (RadioButton) findViewById(group.getCheckedRadioButtonId());
            }
        });

        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd=new DatePickerDialog(PersonalInfoEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.i("test","当前日期："+year+"-"+month+"-"+dayOfMonth);
                        bir.setText(year+"-"+(++month)+"-"+dayOfMonth);
                    }
                },year,month,day);
                //调用日期（一定要记得）
                dpd.show();
            }
        });

    }

    public void dialog(){
        AlertDialog.Builder bulider =new AlertDialog.Builder(PersonalInfoEditActivity.this);
        bulider.setTitle("提示");
        bulider.setMessage("确认提交个人信息 ？");
        bulider.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();

                prefs = getSharedPreferences("myaccount", Context.MODE_PRIVATE);
                String savedPhone = prefs.getString("phone","");

                nickprefs = getSharedPreferences("nick",Context.MODE_PRIVATE);
                nickprefs.edit().putString("nickname",name.getText().toString()).commit();


                //更新数据到数据库
                User newuser = new User();
                newuser.setNickname(name.getText().toString());
                Log.e("nickname=",newuser.getNickname());
                newuser.setSex(rb.getText().toString());
                newuser.setPhone(savedPhone);
                newuser.setBirthday(bir.getText().toString());
                Log.e("birthday=",newuser.getBirthday());
                newuser.setEmail(email.getText().toString());

                httpPost("/profile",newuser);




            }
        });
        bulider.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        bulider.create().show();
    }

    //获取当前日期
    private void getDate() {
        cal= Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }

    private void httpPost(final String url, Object obj) {

        OkhttpUtil.okPost(url, obj, handler);
    }

    //点击返回键弹出对话框判断是否要退出该界面
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            //弹出自定义对话框
            back_dialog();
        }
        return false;
    }

    public void back_dialog(){
        AlertDialog.Builder bulider =new AlertDialog.Builder(PersonalInfoEditActivity.this);
        bulider.setTitle("提示");
        bulider.setMessage("您的个人信息编辑还未提交，请确认是否退出 ？");
        bulider.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                PersonalInfoEditActivity.this.finish();

            }
        });
        bulider.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
            }
        });
        bulider.create().show();

    }

}
