package com.seven.health;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seven.health.abouthome.DiseaseFragment;
import com.seven.health.abouthome.ImmuneFragment;
import com.seven.health.abouthome.LifecareFragment;
import com.seven.health.abouthome.NewsFragment;

/**
 * Created by lenovo on 2018/8/16.
 */

public class MyHomePageAdapter extends FragmentPagerAdapter {

    private final String[] mTitles;

    public MyHomePageAdapter(FragmentManager fm, String[] titles){
        super(fm);
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new NewsFragment();
        }else if(position == 1){
            return new LifecareFragment();
        }else if(position == 2){
            return new DiseaseFragment();
        }else {
            return new ImmuneFragment();
        }

    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}

