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
import com.seven.health.bean.ReserveDepart;
import com.seven.health.util.OkhttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveDepartActivity extends AppCompatActivity{

    private List<ReserveDepart> departmentList = new ArrayList<>();
    private ListView listView;
    private ReserveDepartAdapter adapter;
    private LayoutInflater inflater;
    private TextView tvDepartment;
    private Button btn_forward;
    private Button btn_next;
    private JSONArray arr;
    private String selectedDepart;

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
                            String depart_name = obj.getString("depart_name");
                            int depart_id = obj.getInt("depart_id");
                            ReserveDepart depart = new ReserveDepart(depart_name,depart_id);
                            departmentList.add(depart);

                        }
                        adapter=new ReserveDepartAdapter(departmentList,inflater);
                        listView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 202:
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(ReserveDepartActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.treat_reservation_step2);

        tvDepartment=(TextView)findViewById(R.id.treat_reservation_department_select);
        btn_forward=(Button)findViewById(R.id.treat_reservation_forward_step2);
        btn_next=(Button)findViewById(R.id.treat_reservation_next_step2);

        //返回上一步
        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //下一步
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(tvDepartment.getText())){
                    Toast.makeText(ReserveDepartActivity.this,"请选择科室！",Toast.LENGTH_SHORT).show();
                }else{
                    Bundle bundle=new Bundle();
                    for (ReserveDepart de:departmentList) {
                        if(de.getDepart_name().equals(selectedDepart)){
                            bundle.putInt("depart_id",de.getDepart_id());
                            break;
                        }
                    }
                    Intent intent=new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(ReserveDepartActivity.this,ReserveDocActivity.class);
                    startActivity(intent);

                }
            }
        });

        Intent intent = getIntent();
        //获取该intent所携带的数据
        Bundle data = intent.getExtras();
        int hospital_id = data.getInt("hospital_id");

        //获取ListView对象
        listView=(ListView)findViewById(R.id.treat_reservation_department_list);
        //获取布局加载对象
        inflater=getLayoutInflater();

        //获取所有科室数据
        httpGet("/reserve?md=depart&hosid="+Integer.toString(hospital_id));


        //item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取选中项
                ReserveDepart dp = departmentList.get(position);
                selectedDepart = dp.getDepart_name();

                tvDepartment.setText(selectedDepart);
            }
        });
    }


    @Override
    public void onBackPressed(){
        Intent intent=new Intent(ReserveDepartActivity.this, ReserveHosLocActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳转的界面
        startActivity(intent);
    }

    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }
}
