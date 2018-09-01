package com.seven.health.abouthome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.seven.health.R;
import com.seven.health.bean.CollectionRecord;
import com.seven.health.util.OkhttpUtil;

public class NewsInfoActivity extends AppCompatActivity {
    TextView tv_title;
    TextView news_title;
    ImageView iv_collect;
    TextView news_date;
    TextView news_content;

    private SharedPreferences prefs;

    CollectionRecord news;
    int news_id;

    int collect;
    int t;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    break;
                case 200:
                    break;
                case 202: //处理post请求响应的数据
                    Toast.makeText(NewsInfoActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(NewsInfoActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.news_content);

        news = (CollectionRecord) getIntent().getSerializableExtra("news");
        news_id = news.getNews_id();

        collect = getIntent().getIntExtra("collect",0);
        t=collect;

        initView();
        initData();

    }


    private void initData() {
        prefs = getSharedPreferences("myaccount", Context.MODE_PRIVATE);
        final String savedPhone = prefs.getString("phone","");

        iv_collect.setOnClickListener(new View.OnClickListener() {
            JsonObject obj = new JsonObject();

            @Override
            public void onClick(View v) {
                if(t%2 == 0){
                    iv_collect.setSelected(true);
                    obj.addProperty("collect","yes");
                    obj.addProperty("news_id", news_id);

                }else{
                    iv_collect.setSelected(false);
                    obj.addProperty("collect","no");
                    obj.addProperty("news_id", news_id);
                }
                obj.addProperty("phone",savedPhone);
                httpPost("/collect", obj);
                t = t+1;
            }
        });
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.title);
        news_title = (TextView) findViewById(R.id.news_title);
        iv_collect = (ImageView) findViewById(R.id.news_collect);
        news_date = (TextView) findViewById(R.id.news_date);
        news_content = (TextView) findViewById(R.id.news_content);

        news_content.setMovementMethod(ScrollingMovementMethod.getInstance());

        news_title.setText(news.getNews_name());
        news_date.setText(news.getNews_time().substring(0,19));
        news_content.setText(news.getNews_content());


        if(t==1){
            iv_collect.setSelected(true);
        }else iv_collect.setSelected(false);
    }

    private void httpPost(final String url, Object obj) {

        OkhttpUtil.okPost(url, obj, handler);
    }



}
