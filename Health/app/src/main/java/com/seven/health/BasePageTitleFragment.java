package com.seven.health;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lenovo on 2018/8/15.
 */

public abstract class BasePageTitleFragment extends Fragment {

    private View mGeneralFragment;
    private TextView mTvTitleGeneral;
    private RelativeLayout mTopsee;

    private FrameLayout mFlContentGeneral;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mGeneralFragment = inflater.inflate(R.layout.base_top_title, container, false); //通用布局
        mTvTitleGeneral = (TextView) mGeneralFragment.findViewById(R.id.tv_common_title);
        mTopsee = (RelativeLayout) mGeneralFragment.findViewById(R.id.top_see);

        mFlContentGeneral = (FrameLayout) mGeneralFragment.findViewById(R.id.fl_content_page);

        View view = initView();
        mFlContentGeneral.addView(view);

        return mGeneralFragment;
    }

   // 设置顶行标题是否显示
    public void setTitleText(String msg, boolean show){
        mTvTitleGeneral.setText(msg);
        if(show){
            mTopsee.setVisibility(View.VISIBLE);
            mTvTitleGeneral.setVisibility(View.VISIBLE);
        }else{
           // mTvTitleGeneral.setVisibility(View.GONE);
            mTopsee.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract View initView();
    protected abstract void initData();
}
