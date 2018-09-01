package com.seven.health;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //底部四个tab
    private LinearLayout mTabHome;
    private LinearLayout mTabMedicine;
    private LinearLayout mTabTreat;
    private LinearLayout mTabPersonal;


    //四个fragment
    private Fragment mFragHome;
    private Fragment mFragMedicine;
    private Fragment mFragTreat;
    private Fragment mFragPersonal;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉首行显示的AppName
        //requestWindowFeature(Window.FEATURE_NO_TITLE); 该方法在AppCompatActivity中失效
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);


        mFragmentManager = getSupportFragmentManager();

        initView();
        initEvent();

        setSelect(0);
    }

    private void initEvent() {
        mTabHome.setOnClickListener(this);
        mTabMedicine.setOnClickListener(this);
        mTabTreat.setOnClickListener(this);
        mTabPersonal.setOnClickListener(this);

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

    //  所有底部按钮默认都不被选中,将所有图片置为暗色
    private void resetImgs() {
        mTabHome.setSelected(false);
        mTabMedicine.setSelected(false);
        mTabTreat.setSelected(false);
        mTabPersonal.setSelected(false);
    }
}
