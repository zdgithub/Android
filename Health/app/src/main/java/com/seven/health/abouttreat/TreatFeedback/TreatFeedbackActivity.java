package com.seven.health.abouttreat.TreatFeedback;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.health.R;
import com.seven.health.bean.FeedbackBean;
import com.seven.health.util.OkhttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/18.
 */

public class TreatFeedbackActivity extends AppCompatActivity {

    private ScoreStarLinearLayout mStar;
    private TextView tvScore;
    private Button btn_submit;
    private Spinner spinner;
    private EditText comments;
    private List<String> hospitals = new ArrayList<>();
    private int selectedId;
    private JSONArray arr;

    private SharedPreferences prefs;

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
                            JSONObject tmp = (JSONObject) arr.get(i);
                            //String hospital_id = tmp.getString("hospital_id");
                            String hospital_name = tmp.getString("hospital_name");
                            hospitals.add(hospital_name);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TreatFeedbackActivity.this,android.R.layout.simple_spinner_item,hospitals);
                        spinner.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 202: //处理POST请求响应的数据
                    Toast.makeText(TreatFeedbackActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(TreatFeedbackActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.treat_feedback);

        mStar=(ScoreStarLinearLayout)findViewById(R.id.starbar);
        tvScore=(TextView)findViewById(R.id.score);
        btn_submit=(Button)findViewById(R.id.treat_feedback_submit) ;
        spinner = (Spinner) findViewById(R.id.hospital_select);
        comments = (EditText) findViewById(R.id.et_comments);

        //获取该用户去过的医院列表，在spinner中显示
        prefs = getSharedPreferences("myaccount", Context.MODE_PRIVATE);
        String savedPhone = prefs.getString("phone","");
        httpGet("/feedback?phone="+savedPhone);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = spinner.getSelectedItem().toString();

                for(int i=0;i<arr.length();i++){
                    JSONObject tmp = null;
                    try {
                        tmp = (JSONObject) arr.get(i);
                        int hospital_id = tmp.getInt("hospital_id");
                        String name = tmp.getString("hospital_name");
                        if(name.equals(selectedName)){
                            selectedId = hospital_id;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //星级评分
        mStar.setScore((float)0.0);
        tvScore.setText(mStar.getScore()+"");

        mStar.setChangeListener(new ScoreStarLinearLayout.ChangeListener(){
            @Override
           public void Change(int level){
                tvScore.setText(mStar.getScore()+"");
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //弹出submit对话框
                submit_dialog();
            }
        });
    }

    //点击返回键弹出对话框判断是否要退出该界面
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            //弹出自定义对话框
            back_dialog();
        }
        return false;
    }

    public void back_dialog(){
        AlertDialog.Builder bulider =new AlertDialog.Builder(TreatFeedbackActivity.this);
        bulider.setTitle("提示");
        bulider.setMessage("您的反馈还未提交，请确认是否退出 ？");
        bulider.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                TreatFeedbackActivity.this.finish();

            }
        });
        bulider.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
            }
        });
        bulider.create().show();

    }

    public void submit_dialog(){
        AlertDialog.Builder bulider =new AlertDialog.Builder(TreatFeedbackActivity.this);
        bulider.setTitle("提示");
        bulider.setMessage("确认提交反馈意见");
        bulider.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {

                dialog.dismiss();
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //提交到数据库
                FeedbackBean feed = new FeedbackBean();
                feed.setHospital_id(selectedId);
                feed.setComments(comments.getText().toString());
                feed.setRating((int)mStar.getScore());
                feed.setTime(ft.format(System.currentTimeMillis()));

                httpPost("/feedback", feed);

                TreatFeedbackActivity.this.finish();
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

    private void httpPost(final String url, Object obj) {

        OkhttpUtil.okPost(url, obj, handler);
    }

    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }

}
