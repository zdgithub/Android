package com.seven.health;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seven.health.abouttreat.TreatRegistered.RegisterationFormActivity;
import com.seven.health.util.OkhttpUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static MainActivity mActivity;

    private LinearLayout mTabHome;
    private LinearLayout mTabMedicine;
    private LinearLayout mTabTreat;
    private LinearLayout mTabPersonal;


    private Fragment mFragHome;
    private Fragment mFragMedicine;
    private Fragment mFragTreat;
    private Fragment mFragPersonal;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    NotificationManager nomgr;  //通知消息
    private SharedPreferences prefs;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    break;
                case 200: //处理get请求响应的数据
                    if(msg.obj.toString().equals("yes")){
                        //设置通知
                        Notification.Builder builder = new Notification.Builder(MainActivity.this);
                        builder.setSmallIcon(R.mipmap.blue_logo);
                        builder.setContentTitle("就诊提醒：");
                        builder.setContentText("您有尚未就诊的挂号记录！");

                        Intent intent = new Intent(MainActivity.this, RegisterationFormActivity.class);
                        PendingIntent pintent = PendingIntent.getActivity(MainActivity.this,1,intent,PendingIntent.FLAG_ONE_SHOT); //这个pintent只能使用一次
                        builder.setContentIntent(pintent); //点击通知后将要启动的组件对应的pendingIntent
                        builder.setAutoCancel(true);  //设置通知被点击后自动消失
                        Notification notifi = builder.getNotification();

                        int notifyId = 1;
                        nomgr.notify(notifyId, notifi);
                    }

                    break;
                case 202:
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                    Toast.makeText(MainActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉首行显示的AppName
        //requestWindowFeature(Window.FEATURE_NO_TITLE); 该方法在AppCompatActivity中失效
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        mActivity = this;


        mFragmentManager = getSupportFragmentManager();
        nomgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        initView();
        initEvent();

        setSelect(0);
    }

    private void initEvent() {
        mTabHome.setOnClickListener(this);
        mTabMedicine.setOnClickListener(this);
        mTabTreat.setOnClickListener(this);
        mTabPersonal.setOnClickListener(this);

        prefs = getSharedPreferences("myaccount", Context.MODE_PRIVATE);
        String savedPhone = prefs.getString("phone","");

        httpGet("/getnotify?phone="+savedPhone);

    }

    private void initView() {
        mTabHome = (LinearLayout) findViewById(R.id.tab_home);
        mTabMedicine = (LinearLayout) findViewById(R.id.tab_medicine);
        mTabTreat = (LinearLayout) findViewById(R.id.tab_treat);
        mTabPersonal = (LinearLayout) findViewById(R.id.tab_personal);
    }

    @Override
    public void onClick(View v) {
        resetImgs();
        switch(v.getId()){
            case R.id.tab_home:
                setSelect(0);
                break;
            case R.id.tab_medicine:
                setSelect(1);
                break;
            case R.id.tab_treat:
                setSelect(2);
                break;
            case R.id.tab_personal:
                setSelect(3);
                break;
        }

    }


//    @Override
//    protected void onResume(){
//        int id=getIntent().getIntExtra("id",0);
//        mTabHome.setSelected(false);
//        setSelect(id);
//        super.onResume();
//    }


    //设置内容区域+设置Tab图片高亮
    private void setSelect(int i) {

        mTransaction = mFragmentManager.beginTransaction();  //开启事务

        //先将所有的fragment都隐藏，再选择显示对应的fragment
        hideFragment(mTransaction);

        switch(i)
        {
            case 0:
                if(mFragHome == null){
                    mFragHome = new HomeFragment();
                    mTransaction.add(R.id.main_frame_content, mFragHome); //通过事务将fragment展示的内容添加到界面布局
                }else{
                    mTransaction.show(mFragHome);
                }
                mTabHome.setSelected(true); //底部按钮高亮
                break;
            case 1:
                if(mFragMedicine == null){
                    mFragMedicine = new MedicineFragment();
                    mTransaction.add(R.id.main_frame_content, mFragMedicine);

                }else{
                    mTransaction.show(mFragMedicine);
                }
                mTabMedicine.setSelected(true);
                break;
            case 2:
                if(mFragTreat == null){
                    mFragTreat = new TreatFragment();
                    mTransaction.add(R.id.main_frame_content, mFragTreat);

                }else{
                    mTransaction.show(mFragTreat);
                }
                mTabTreat.setSelected(true);
                break;
            case 3:
                if(mFragPersonal == null){
                    mFragPersonal = new PersonalFragment();
                    mTransaction.add(R.id.main_frame_content, mFragPersonal);

                }else{
                    mTransaction.show(mFragPersonal);
                }
                mTabPersonal.setSelected(true);
                break;
        }

        mTransaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if(mFragHome != null){
            transaction.hide(mFragHome);
        }
        if(mFragMedicine != null){
            transaction.hide(mFragMedicine);
        }
        if(mFragTreat != null){
            transaction.hide(mFragTreat);
        }
        if(mFragPersonal != null){
            transaction.hide(mFragPersonal);
        }
    }

    // 所有底部按钮默认都不被选中,将所有图片置为暗色
    private void resetImgs() {
        mTabHome.setSelected(false);
        mTabMedicine.setSelected(false);
        mTabTreat.setSelected(false);
        mTabPersonal.setSelected(false);
    }

    //按返回键两次退出程序
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }
}
