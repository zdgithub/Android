package com.example.lenovo.mytime;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button login = (Button) findViewById(R.id.btn_login);
        Button register = (Button) findViewById(R.id.btn_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

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

