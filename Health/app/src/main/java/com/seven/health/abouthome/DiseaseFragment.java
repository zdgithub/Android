package com.seven.health.abouthome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seven.health.R;
import com.seven.health.aboutpersonal.CollectionrecordAdapter;
import com.seven.health.bean.CollectionRecord;
import com.seven.health.util.OkhttpUtil;

import java.util.ArrayList;
import java.util.List;

import static com.seven.health.MainActivity.mActivity;

/**
 * Created by lenovo on 2018/8/16.
 */

public class DiseaseFragment  extends Fragment {


    private List<CollectionRecord> datas = new ArrayList<>();
    private ListView listView;
    private CollectionrecordAdapter adapter;
    private LayoutInflater minflater;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    break;
                case 200: //处理get请求响应的数据
                    Gson gson = new Gson();
                    datas = gson.fromJson(msg.obj.toString(),new TypeToken<List<CollectionRecord>>(){}.getType());
                    adapter=new CollectionrecordAdapter(datas,minflater);
                    listView.setAdapter(adapter);
                    break;
                case 202:
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    // Toast.makeText(mActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.home_news,container,false);

        listView=(ListView)view.findViewById(R.id.news_list);
        minflater = inflater;

        httpGet("/news?category=3");


        //item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("news",datas.get(position));

                Intent intent=new Intent();
                intent.putExtras(bundle);
                intent.setClass(mActivity, NewsInfoActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }

    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }



}