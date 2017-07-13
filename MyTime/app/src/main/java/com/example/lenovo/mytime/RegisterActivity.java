package com.example.lenovo.mytime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends Activity
{
    Button select_bn,register_btn;
    EditText city,register_name,register_pwd1,register_name2;

    SQLiteDatabase db;

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        db.close();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        // 获取界面上的组件
        select_bn = (Button) findViewById(R.id.select_bn);
        city = (EditText) findViewById(R.id.city);
        register_btn=(Button) findViewById(R.id.register_btn);
        register_name=(EditText) findViewById(R.id.register_name);
        register_pwd1=(EditText) findViewById(R.id.register_pwd1);

        // 为按钮绑定事件监听器
        select_bn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View source)
            {
                // 创建需要对应于目标Activity的Intent
                Intent intent = new Intent(RegisterActivity.this,
                        SelectCityActivity.class);
                // 启动指定Activity并等待返回的结果，其中0是请求码，用于标识该请求
                startActivityForResult(intent, 0);
            }
        });

        register_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name = register_name.getText().toString();
                String password = register_pwd1.getText().toString();
                if (!(name.equals("") && password.equals(""))) {
                    if (addUser(name, password)) {
                        DialogInterface.OnClickListener ss = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                // 跳转到登录界面
                                Intent in = new Intent();
                                in.setClass(RegisterActivity.this,
                                        MainActivity.class);
                                startActivity(in);
                                // 销毁当前activity
                                RegisterActivity.this.onDestroy();
                            }
                        };
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("注册成功").setMessage("注册成功")
                                .setPositiveButton("确定", ss).show();

                    } else {
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("注册失败").setMessage("注册失败")
                                .setPositiveButton("确定", null);
                    }
                } else {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("帐号密码不能为空").setMessage("帐号密码不能为空")
                            .setPositiveButton("确定", null);
                }

            }
        });




    }

    // 添加用户
    public Boolean addUser(String name, String password) {
        String str = "insert into tb_user values(?,?) ";
        MainActivity main = new MainActivity();
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()
                + "/test.dbs", null);
        main.db = db;
        try {
            db.execSQL(str, new String[] { name, password });
            return true;
        } catch (Exception e) {
            main.createDb();
        }
        return false;
    }


    // 重写该方法，该方法以回调的方式来获取指定Activity返回的结果
    @Override
    public void onActivityResult(int requestCode
            , int resultCode, Intent intent)
    {
        // 当requestCode、resultCode同时为0时，也就是处理特定的结果
        if (requestCode == 0 && resultCode == 0)
        {
            // 取出Intent里的Extras数据
            Bundle data = intent.getExtras();
            // 取出Bundle中的数据
            String resultCity = data.getString("city");
            // 修改city文本框的内容
            city.setText(resultCity);
            Toast toast=Toast.makeText(RegisterActivity.this,"您选择的城市为"+resultCity ,Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}


