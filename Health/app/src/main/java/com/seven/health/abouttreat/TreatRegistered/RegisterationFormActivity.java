package com.seven.health.abouttreat.TreatRegistered;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seven.health.R;
import com.seven.health.bean.RegistrationForm;
import com.seven.health.util.OkhttpUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/8/20.
 */

public class RegisterationFormActivity extends AppCompatActivity {

    private List<RegistrationForm> registrationForms;
    private ListView listView;
    private RegisterationFormAdapter adapter;
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
                    List<RegistrationForm> forms = gson.fromJson(msg.obj.toString(),new TypeToken<List<RegistrationForm>>(){}.getType());
                    adapter=new RegisterationFormAdapter(forms,inflater);
                    listView.setAdapter(adapter);
                    break;
                case 202:
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(RegisterationFormActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.treat_registered);
        //获取ListView对象
        listView=(ListView)findViewById(R.id.treat_register_list);
        //获取布局加载对象
        inflater=getLayoutInflater();
        //初始化数据
        initData();

    }

    //获取数据
    public void initData(){
        prefs = getSharedPreferences("myaccount", Context.MODE_PRIVATE);
        String savedPhone = prefs.getString("phone","");

        httpGet("/registerform?phone="+savedPhone);

    }

    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }

}
