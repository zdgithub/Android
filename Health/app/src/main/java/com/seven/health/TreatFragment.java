package com.seven.health;

import android.view.View;

/**
 * Created by lenovo on 2018/8/16.
 */

public class TreatFragment extends BasePageTitleFragment {

    @Override
    protected View initView() {
        setTitleText("自 助 就 诊",true);
        View treatFragment = View.inflate(getContext(),R.layout.fragment_treat, null);
        return treatFragment;
    }

    @Override
    protected void initData() {

    }
}
