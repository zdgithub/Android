package com.example.lenovo.mytime;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddfActivity extends Activity
{
    MyDatabaseHelper dbHelper;
    Button insert = null;
    Button search = null;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addf);
        // 创建MyDatabaseHelper对象，指定数据库版本为1，此处使用相对路径即可
        // 数据库文件自动会保存在程序的数据文件夹的databases目录下
        dbHelper = new MyDatabaseHelper(this, "my.db3", 1);
        insert = (Button) findViewById(R.id.insert);
        search = (Button) findViewById(R.id.search);
        insert.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View source)
            {
                // 获取用户输入
                String word = ((EditText) findViewById(R.id.word))
                        .getText().toString();
                String detail = ((EditText) findViewById(R.id.detail))
                        .getText().toString();
                // 插入生词记录
                insertData(dbHelper.getReadableDatabase(), word, detail);
                // 显示提示信息
                Toast.makeText(AddfActivity.this, "添加好友成功！"
                        , Toast.LENGTH_LONG).show();
            }
        });

    }

    private void insertData(SQLiteDatabase db, String word
            , String detail)
    {
        // 执行插入语句
        db.execSQL("insert into dict values(null , ? , ?)"
                , new String[] {word, detail });
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // 退出程序时关闭MyDatabaseHelper里的SQLiteDatabase
        if (dbHelper != null)
        {
            dbHelper.close();
        }
    }
}

