
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seven.health.R;
import com.seven.health.abouthome.NewsInfoActivity;
import com.seven.health.bean.CollectionRecord;
import com.seven.health.util.OkhttpUtil;

import java.util.List;


public class CollectionActivity extends AppCompatActivity{

    private List<CollectionRecord> collects;
    private ListView listView;
    private CollectionrecordAdapter adapter;
    private LayoutInflater inflater;

    private SharedPreferences prefs;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    break;
                case 200: //处理get请求响应的数据
                    Gson gson = new Gson();
                    collects = gson.fromJson(msg.obj.toString(),new TypeToken<List<CollectionRecord>>(){}.getType());
                    adapter=new CollectionrecordAdapter(collects,inflater);
                    listView.setAdapter(adapter);
                    break;
                case 202:
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(CollectionActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.personal_collection);

        //获取ListView对象
        listView =(ListView)findViewById(R.id.personnal_colletion_list);
        inflater=getLayoutInflater();

        prefs = getSharedPreferences("myaccount", Context.MODE_PRIVATE);
        String savedPhone = prefs.getString("phone","");

        httpGet("/collect?phone="+savedPhone);


        //item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Bundle bundle=new Bundle();
                 bundle.putSerializable("news",collects.get(position));
                 bundle.putInt("collect",1);  //表示从 我的收藏 页面来

                 Intent intent=new Intent();
                 intent.putExtras(bundle);
                 intent.setClass(CollectionActivity.this, NewsInfoActivity.class);
                 startActivity(intent);

            }
        });

    }

    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }

    @Override
    public void onBackPressed(){
//        Intent intent=new Intent(CollectionActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
//        intent.putExtra("id",3);
//        startActivity(intent);
        this.finish();
    }
}
