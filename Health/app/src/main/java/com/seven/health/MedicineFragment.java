package com.seven.health;

import android.view.View;

/**
 * Created by lenovo on 2018/8/16.
 */

public class MedicineFragment extends BasePageTitleFragment {
    @Override
    protected View initView() {
        setTitleText("药 物 查 询",true);
        View medicineFragment = View.inflate(getContext(),R.layout.fragment_medicine, null);
        return medicineFragment;
    }

    @Override
    protected void initData() {

    }
}
