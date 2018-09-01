package com.seven.health.abouttreat.TreatReservation;

import android.content.Intent;
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
import com.seven.health.bean.ReserveDoc;
import com.seven.health.util.OkhttpUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveDocActivity extends AppCompatActivity{

    private List<ReserveDoc> doctors;
    private ListView listView;
    private ReserveDocAdapter adapter;
    private LayoutInflater inflater;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    break;
                case 200: //处理get请求响应的数据
                    Gson gson = new Gson();
                    doctors = gson.fromJson(msg.obj.toString(),new TypeToken<List<ReserveDoc>>(){}.getType());
                    adapter=new ReserveDocAdapter(doctors,inflater);
                    listView.setAdapter(adapter);

                    break;
                case 202:
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(ReserveDocActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.treat_reservation_step3);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        int depart_id = data.getInt("depart_id");


        //获取ListView对象
        listView=(ListView)findViewById(R.id.treat_doc_list);
        //获取布局加载对象
        inflater=getLayoutInflater();

        //获取所有医生数据
        httpGet("/reserve?md=doctor&departid="+Integer.toString(depart_id));


        //item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();

                bundle.putSerializable("detail",doctors.get(position));

                Intent intent=new Intent();
                intent.putExtras(bundle);
                intent.setClass(ReserveDocActivity.this,ReserveScheduleActivity.class);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onBackPressed(){
        Intent intent=new Intent(ReserveDocActivity.this, ReserveDepartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳转的界面
        startActivity(intent);
    }



    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }
}
