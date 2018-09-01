package com.seven.health;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.seven.health.abouttreat.TreatFeedback.TreatFeedbackActivity;
import com.seven.health.abouttreat.TreatRecord.TreatRecordActivity;
import com.seven.health.abouttreat.TreatRegistered.RegisterationFormActivity;
import com.seven.health.abouttreat.TreatReservation.ReserveHosLocActivity;

/**
 * Created by lenovo on 2018/8/16.
 */

public class TreatFragment extends BasePageTitleFragment {

    Button reservationButton;
    Button registerFormButton;
    Button recordButton;
    Button feedbackButton;

    @Override
    protected View initView() {
        setTitleText("自 助 就 诊",true);
        View treatFragment = View.inflate(getContext(),R.layout.fragment_treat, null);


        reservationButton=(Button)treatFragment.findViewById(R.id.treat_reservation);
        registerFormButton=(Button)treatFragment.findViewById(R.id.treat_register);
        recordButton=(Button)treatFragment.findViewById(R.id.treat_medical_record);
        feedbackButton=(Button)treatFragment.findViewById(R.id.treat_medical_feedback);

        return treatFragment;
    }

    @Override
    protected void initData() {

        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ReserveHosLocActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        registerFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), RegisterationFormActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TreatRecordActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TreatFeedbackActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });
    }
}
