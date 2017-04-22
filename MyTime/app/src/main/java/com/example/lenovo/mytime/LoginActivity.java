package com.example.lenovo.mytime;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import static android.widget.AdapterView.OnItemClickListener;
import static android.widget.AdapterView.OnItemSelectedListener;

import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginActivity extends Activity
{
    Switch sw;
    ListView list;
    PopupMenu popup = null;

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
        //以后待补充的功能

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

    public void onPopupButtonClick(View button)
    {
        // 创建PopupMenu对象
        popup = new PopupMenu(this, button);
        // 将R.menu.popup_menu菜单资源加载到popup菜单中
        getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        // 为popup菜单的菜单项单击事件绑定事件监听器
        popup.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {


                        return true;
                    }
                });
        popup.show();
    }

    public void onCustomViewClick(View source)
    {
        // 装载界面布局文件
        LinearLayout loginForm = (LinearLayout)getLayoutInflater()
                .inflate( R.layout.font, null);
        new AlertDialog.Builder(this)
                // 设置对话框的标题
                .setTitle("设置字体大小")
                // 设置对话框显示的View对象
                .setView(loginForm)
                // 为对话框设置一个“确定”按钮
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // 此处可执行登录处理
                    }
                })
                // 为对话框设置一个“取消”按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        // 取消登录，不做任何事情
                    }
                })
                // 创建并显示对话框
                .create()
                .show();
    }

}

