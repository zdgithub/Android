package com.seven.health.abouttreat.TreatReservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.health.R;
import com.seven.health.bean.ReserveHosLoc;
import com.seven.health.util.OkhttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveHosLocActivity extends AppCompatActivity{

    private List<ReserveHosLoc> hospitalLocs = new ArrayList<>();
    private ListView listView;
    private ReserveHosLocAdapter adapter;
    private LayoutInflater inflater;
    private TextView tvLoction;
    private TextView tvDestination;
    private Button btn_next_step;
    private JSONArray arr;
    private String selectedName;

    private String address;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    break;
                case 200: //处理get请求响应的数据
                    try {
                        arr = new JSONArray(msg.obj.toString());
                        for(int i=0;i<arr.length();i++){
                            JSONObject obj = (JSONObject) arr.get(i);
                            int hospital_id = obj.getInt("hospital_id");
                            String hospital_name = obj.getString("hospital_name");
                            double distance=obj.getDouble("distance");
                            ReserveHosLoc hos = new ReserveHosLoc(hospital_name,hospital_id);
                            hos.setDistance(distance);
                            hospitalLocs.add(hos);
                        }
                        adapter=new ReserveHosLocAdapter(hospitalLocs,inflater);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 202:
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(ReserveHosLocActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.treat_reservation_step1);

        tvLoction=(TextView)findViewById(R.id.treat_current_loc);
        tvDestination=(TextView)findViewById(R.id.treat_destination);
        btn_next_step=(Button)findViewById(R.id.treat_reservation_step1);



        btn_next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(tvDestination.getText())){
                    Toast.makeText(ReserveHosLocActivity.this,"请选择目的医院！",Toast.LENGTH_SHORT).show();
                }else{
                    Bundle bundle=new Bundle();
                    for (ReserveHosLoc hos : hospitalLocs) {
                        if(hos.getHospital_name().equals(selectedName)){
                            bundle.putInt("hospital_id",hos.getHospital_id());
                            break;
                        }
                    }

                    Intent intent=new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(ReserveHosLocActivity.this,ReserveDepartActivity.class);
                    startActivity(intent);

                }
            }
        });

        //获取ListView对象
        listView=(ListView)findViewById(R.id.treat_location_list);
        //获取布局加载对象
        inflater=getLayoutInflater();
        //初始化数据
        initData();


        //item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取选中项
                ReserveHosLoc hosloc = hospitalLocs.get(position);
                selectedName = hosloc.getHospital_name();

                tvDestination.setText(selectedName);

                //跳转到地图页面
                //Intent intent = new Intent(ReserveHosLocActivity.this,ReserveLocationActivity.class);
                //startActivity(intent);

            }
        });


    }

    //获取所有医院的数据
    public void initData(){

        httpGet("/reserve?md=hospital");

    }

//    从百度地图传来的当前定位信息，目前已经去掉百度地图功能
//    @Override
//    protected void onResume() {
//        super.onResume();
//        address=getIntent().getExtras().getString("address");
//        tvLoction.setText(address);
//    }

    @Override
    public void onBackPressed(){
        this.finish();  //这样就可以返回到上一个页面
    }

    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }
}
