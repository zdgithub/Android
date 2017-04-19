package com.example.lenovo.mytime;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import static android.widget.AdapterView.OnItemClickListener;
import static android.widget.AdapterView.OnItemSelectedListener;

import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginActivity extends Activity
{
    Switch sw;
    ListView list;
    private String[] names = new String[]
    { "群助手", "苏轼", "李白"};
    private String[] descs = new String[]
            { "有2条未读消息", "好的"
                    , "@全体成员"};
    private int[] imageIds = new int[]
            { R.drawable.tiger , R.drawable.libai
                    , R.drawable.qingzhao };
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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        sw=(Switch) findViewById (R.id.switcher);
        // 创建一个List集合，List集合的元素是Map
        List<Map<String, Object>> listItems =
                new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++)
        {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("header", imageIds[i]);
            listItem.put("personName", names[i]);
            listItem.put("desc", descs[i]);
            listItems.add(listItem);
        }
        // 创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
                R.layout.simple_item,
                new String[] { "personName", "header" , "desc"},
                new int[] { R.id.name, R.id.header , R.id.desc });
        list = (ListView) findViewById(R.id.mylist);
        // 为ListView设置Adapter
        list.setAdapter(simpleAdapter);

        // 为ListView的列表项的单击事件绑定事件监听器

        //switcher开关
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
          //控制列表的显示和隐藏
                if (isChecked) {
                     sw.setChecked(true);
                     list.setVisibility(View.VISIBLE);
                } else {
                     sw.setChecked(false);
                    list.setVisibility(View.GONE);

                }
            }
        });

    }

}

