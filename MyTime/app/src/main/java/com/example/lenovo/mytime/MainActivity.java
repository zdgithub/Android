package com.example.lenovo.mytime;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mPwd;

    // 创建SQLite数据库
    public static SQLiteDatabase db;

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        db.close();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mName=(EditText)findViewById(R.id.edit_account);
        mPwd=(EditText) findViewById(R.id.edit_pwd);
        Button loginBtn = (Button) findViewById(R.id.btn_login);
        Button registerBtn = (Button) findViewById(R.id.btn_register);

        db = SQLiteDatabase.openOrCreateDatabase(MainActivity.this.getFilesDir().toString()
                + "/test.dbs", null);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String password = mPwd.getText().toString();
                if (name.equals("") || password.equals("")) {
                    // 弹出消息框
                    new AlertDialog.Builder(MainActivity.this).setTitle("错误")
                            .setMessage("帐号或密码不能空").setPositiveButton("确定", null)
                            .show();
                } else {
                    isUserinfo(name, password);
                }
            }

            // 判断输入的用户是否正确
            public Boolean isUserinfo(String name, String pwd) {
                try{
                    String str="select * from tb_user where name=? and password=?";
                    Cursor cursor = db.rawQuery(str, new String []{name,pwd});
                    if(cursor.getCount()<=0){
                        new AlertDialog.Builder(MainActivity.this).setTitle("错误")
                                .setMessage("帐号或密码错误！").setPositiveButton("确定", null)
                                .show();
                        return false;
                    }else{
                        /*
                        new AlertDialog.Builder(MainActivity.this).setTitle("正确")
                                .setMessage("成功登录").setPositiveButton("确定", null)
                                .show();*/
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        return true;
                    }

                }catch(SQLiteException e){
                    createDb();
                }
                return false;
            }
        });



    }

    // 创建数据库和用户表
    public void createDb() {
        db.execSQL("create table tb_user( name varchar(30) primary key,password varchar(30))");
    }

    //设置菜单项
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // 添加菜单项
        MenuItem item1 = menu.add("添加");
        MenuItem item2 = menu.add("编辑");
        MenuItem item3 = menu.add("删除");
        //为菜单项设置关联的Activity
        item1.setIntent(new Intent(this , OtherActivity.class));
        item2.setIntent(new Intent(this , OtherActivity.class));
        item3.setIntent(new Intent(this , OtherActivity.class));
        return super.onCreateOptionsMenu(menu);
    }
}

