package com.example.lenovo.mytime;

import android.app.Activity;
import android.content.Intent;
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


public class SearchfActivity extends Activity
{
    MyDatabaseHelper dbHelper;

    Button search = null;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchf);

        dbHelper = new MyDatabaseHelper(this, "my.db3", 1);

        search = (Button) findViewById(R.id.search);

        search.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View source)
            {
                // 获取用户输入
                String key = ((EditText) findViewById(R.id.key)).getText()
                        .toString();
                // 执行查询
                Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                        "select * from dict where word like ? or detail like ?",
                        new String[] { "%" + key + "%", "%" + key + "%" });
                // 创建一个Bundle对象
                Bundle data = new Bundle();
                data.putSerializable("data", converCursorToList(cursor));
                // 创建一个Intent
                Intent intent = new Intent(SearchfActivity.this
                        , ResultActivity.class);
                intent.putExtras(data);
                // 启动Activity
                startActivity(intent);
            }
        });
    }
    protected ArrayList<Map<String, String>>
    converCursorToList(Cursor cursor)
    {
        ArrayList<Map<String, String>> result =
                new ArrayList<Map<String, String>>();
        // 遍历Cursor结果集
        while (cursor.moveToNext())
        {
            // 将结果集中的数据存入ArrayList中
            Map<String, String> map = new HashMap<>();
            // 取出查询记录中第2列、第3列的值
            map.put("word", cursor.getString(1));
            map.put("detail", cursor.getString(2));
            result.add(map);
        }
        return result;
    }



}

