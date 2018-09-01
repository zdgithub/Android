package com.seven.health.aboutpersonal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seven.health.R;
import com.seven.health.bean.HealthCon;
import com.seven.health.util.OkhttpUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2018/8/20.
 */

public class PersonalHealthActivity extends AppCompatActivity{

    private TextView name;
    private TextView sex;
    private TextView age;
    private TextView height;
    private TextView weight;
    private TextView infectious;
    private TextView chronic;
    private TextView genetic;
    private Spinner dateSpinner;

    private String date;

    private LayoutInflater inflater;

    private List<HealthCon> cons = new ArrayList<>();
    private List<String> dates = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    private SharedPreferences prefs;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    break;
                case 200: //处理get请求响应的数据
                    Gson gson = new Gson();
                    cons = gson.fromJson(msg.obj.toString(),new TypeToken<List<HealthCon>>(){}.getType());
                    for(int i=0;i<cons.size();i++){
                        dates.add(cons.get(i).getConDate());
                    }
                    adapter=new ArrayAdapter<String>(PersonalHealthActivity.this,android.R.layout.simple_spinner_item,dates);
                    dateSpinner.setAdapter(adapter);
                    if(cons.size()>0)
                        initData(cons.get(0));
                    break;
                case 202:
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(PersonalHealthActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.personal_healthfile);

        inflater=getLayoutInflater();

        initView();

        prefs = getSharedPreferences("myaccount", Context.MODE_PRIVATE);
        String savedPhone = prefs.getString("phone","");

        httpGet("/healthfile?phone="+savedPhone);


        //获取档案时间
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                initData(cons.get(position));

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


    }

    private void initView(){
        name=(TextView)findViewById(R.id.personal_health_name);
        sex=(TextView)findViewById(R.id.personal_health_sex);
        age= (TextView) findViewById(R.id.personal_health_age);
        height=(TextView)findViewById(R.id.personal_health_height);
        weight=(TextView)findViewById(R.id.personal_health_weight);
        infectious=(TextView)findViewById(R.id.personal_infectious_disease);  //传染病
        chronic=(TextView)findViewById(R.id.personal_chronic);                //慢性病
        genetic=(TextView)findViewById(R.id.personal_genetic);                //遗传病
        dateSpinner=(Spinner) findViewById(R.id.personal_record_date);        //档案日期
    }

    private void initData(HealthCon con){
        if(con.getTruename()!=null){
            name.setText(con.getTruename());
        }
        if(con.getSex()!=null){
            sex.setText(con.getSex());
        }
        if(con.getBirthday()!=null){
            Date cur = new Date();
            String sbir = con.getBirthday().replace('-','/');
            Date bir = new Date(sbir);
            long days = (cur.getTime()-bir.getTime())/(1000*60*60*24);
            String year = String.valueOf(days/365);
            age.setText(year);
        }
        height.setText(String.valueOf(con.getHeight()));
        weight.setText(String.valueOf(con.getWeight()));
        infectious.setText(con.getInfection());
        chronic.setText(con.getChronic());
        genetic.setText(con.getGenetic());

    }


    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }

    @Override
    public void onBackPressed(){
//        Intent intent=new Intent(PersonalHealthActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
//        intent.putExtra("id",3);
//        startActivity(intent);
        this.finish(); //关掉最上面的自己就行了
    }


}
