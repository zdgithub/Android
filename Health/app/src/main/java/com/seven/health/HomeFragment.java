package com.seven.health;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lenovo on 2018/8/16.
 */



public class HomeFragment extends BasePageTitleFragment {
    private TabLayout mTlTopTabs;
    private ViewPager mVpTabContent;

    @Override
    protected View initView() {
        setTitleText("新 闻 资 讯",true);
        View homeFragment = View.inflate(getContext(),R.layout.fragment_home, null);
        mTlTopTabs = (TabLayout) homeFragment.findViewById(R.id.tl_top_tabs);
        mVpTabContent = (ViewPager) homeFragment.findViewById(R.id.vp_tab_content);

        return homeFragment;
    }

    @Override
    protected void initData() {
        String[] mTitles = getResources().getStringArray(R.array.homepage_tabs);

        mVpTabContent.setAdapter(new MyHomePageAdapter(getChildFragmentManager(), mTitles));
        mTlTopTabs.setupWithViewPager(mVpTabContent);


    }
}