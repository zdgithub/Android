package com.seven.health;

import android.view.View;

/**
 * Created by lenovo on 2018/8/16.
 */

public class PersonalFragment extends BasePageTitleFragment {

    @Override
    protected View initView() {
        setTitleText("",false); //不显示通用标题
        View personFragment = View.inflate(getContext(),R.layout.fragment_personal_center, null);
        return personFragment;
    }

    @Override
    protected void initData() {

    }
}
