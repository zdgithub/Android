package com.seven.health.abouttreat.TreatReservation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seven.health.MainActivity;
import com.seven.health.R;
import com.seven.health.bean.RegisterBean;
import com.seven.health.bean.ReserveDoc;
import com.seven.health.bean.ReserveDocSchedule;
import com.seven.health.bean.VisitBean;
import com.seven.health.util.OkhttpUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveScheduleActivity extends AppCompatActivity {

    private List<ReserveDocSchedule> reserveDocSchedules = new ArrayList<>();
    private ListView listView;
    private ReserveScheduleAdapter adapter;
    private LayoutInflater inflater;
    //private Context context;
    private ImageView  imSex;
    private TextView tvName;
    private TextView tvIndication;
    private ReserveDoc doctor;
    private List<VisitBean> visits = new ArrayList<>();
    private int earlyMaxNum;
    private int lateMaxNum;

    private ReserveDocSchedule sh; //被选中的一条出诊

    private SharedPreferences prefs;

    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat weekft = new SimpleDateFormat("EEEE", Locale.CHINA);

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    break;
                case 200: //处理get请求响应的数据
                    Gson gson = new Gson();
                    visits = gson.fromJson(msg.obj.toString(),new TypeToken<List<VisitBean>>(){}.getType());
                    for (VisitBean v : visits) {
                        ReserveDocSchedule s1 = new ReserveDocSchedule();
                        s1.setDate(ft.format(v.getVisit_date()));
                        s1.setWeek(weekft.format(v.getVisit_date()));
                        s1.setHalf("上午");
                        s1.setReserved(v.getEarly_num());
                        s1.setRemain(earlyMaxNum-v.getEarly_num());
                        reserveDocSchedules.add(s1);

                        ReserveDocSchedule s2 = new ReserveDocSchedule();
                        s2.setDate(ft.format(v.getVisit_date()));
                        s2.setWeek(weekft.format(v.getVisit_date()));
                        s2.setHalf("下午");
                        s2.setReserved(v.getLate_num());
                        s2.setRemain(lateMaxNum-v.getLate_num());
                        reserveDocSchedules.add(s2);

                    }

                    adapter=new ReserveScheduleAdapter(reserveDocSchedules,inflater,ReserveScheduleActivity.this);
                    listView.setAdapter(adapter);
                    break;
                case 202:
                    Toast.makeText(ReserveScheduleActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(ReserveScheduleActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.treat_reservation_step4);

        imSex=(ImageView) findViewById(R.id.treat_reservation_sex);
        tvName=(TextView)findViewById(R.id.treat_reservation_name);
        tvIndication=(TextView)findViewById(R.id.treat_reservation_doctor_indication);

        doctor = (ReserveDoc) getIntent().getSerializableExtra("detail");
        earlyMaxNum = doctor.getEarly_max_num();
        lateMaxNum = doctor.getLate_max_num();

        if(doctor.getSex().equals("男")){
            imSex.setImageResource(R.mipmap.treat_man_doctor);
        }else{
            imSex.setImageResource(R.mipmap.treat_woman_doctor);
        }
        tvName.setText(doctor.getDoctor_name());
        tvIndication.setText(doctor.getInfo());

        //获取ListView对象
        listView=(ListView)findViewById(R.id.treat_schedule_list);
        //获取布局加载对象
        inflater=getLayoutInflater();

        httpGet("/reserve?md=schedule&docid="+ doctor.getDoctor_id());

        //将预约按钮点击事件传到item点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sh = reserveDocSchedules.get(position);
                if(sh.getRemain() > 0){
                    dialog();

                }
            }
        });

    }


    public void dialog(){
        AlertDialog.Builder bulider =new AlertDialog.Builder(ReserveScheduleActivity.this);
        bulider.setTitle("提示");
        bulider.setMessage("确认预约该时间段就诊？");
        bulider.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();

                RegisterBean rb = new RegisterBean();
                rb.setDoctor_id(doctor.getDoctor_id());
                rb.setDoctor_date(sh.getDate());
                if(sh.getHalf().equals("上午")){
                    rb.setHalf(0);
                }else rb.setHalf(1);

                prefs = getSharedPreferences("myaccount", Context.MODE_PRIVATE);
                String savedPhone = prefs.getString("phone","");
                rb.setUser_phone(savedPhone);

                httpPost("/reserve",rb);

                Intent intent=new Intent(ReserveScheduleActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳转的界面
                startActivity(intent);
                ReserveScheduleActivity.this.finish();

            }
        });
        bulider.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        bulider.create().show();

    }

    @Override
    public void onBackPressed(){
        Intent intent=new Intent(ReserveScheduleActivity.this, ReserveDocActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//设置不要刷新将要跳转的界面
        startActivity(intent);
    }

    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }

    private void httpPost(final String url, Object obj) {

        OkhttpUtil.okPost(url, obj, handler);
    }
}
