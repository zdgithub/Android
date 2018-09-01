package com.seven.health.abouttreat.TreatRecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.seven.health.R;
import com.seven.health.bean.TreatRecord;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/8/20.
 */

public class TreatRecordInfoActivity extends AppCompatActivity{
    List<TreatRecord> records;
    TreatRecord tr;
    TreatRecord againtr;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.treat_record_information);
        //Bundle bundle=getIntent().getExtras();
        tr = (TreatRecord) getIntent().getSerializableExtra("treatrecord");
        records = (List<TreatRecord>) getIntent().getSerializableExtra("allrecords");

        TextView tvHospital=(TextView)findViewById(R.id.treat_record_hospital_info);
        TextView tvDepartment=(TextView)findViewById(R.id.treat_record_department_info);
        TextView tvDoctor=(TextView)findViewById(R.id.treat_record_doctor);
        TextView tvDate=(TextView)findViewById(R.id.treat_record_date_info);
        TextView tvDescription=(TextView)findViewById(R.id.treat_record_description);
        TextView tvResult=(TextView)findViewById(R.id.treat_record_result);
        TextView tvAgain=(TextView)findViewById(R.id.treat_record_again);

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        tvHospital.setText(tr.getHospital());
        tvDepartment.setText(tr.getDepartment());
        tvDoctor.setText(tr.getDoctor());
        tvDate.setText(ft.format(tr.getDate()));
        tvDescription.setText(tr.getDescription());
        tvResult.setText(tr.getResult());
        if(tr.getAgain_id()>0){
            tvAgain.setText("是            #点击查看复诊记录#");

            tvAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (TreatRecord r: records) {
                        if(r.getRecord_id() == tr.getAgain_id()){
                            againtr = r;
                            break;
                        }
                    }
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("treatrecord",againtr);
                    bundle.putSerializable("allrecords", (Serializable) records);
                    //bundle.putString();
                    Intent intent=new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(TreatRecordInfoActivity.this,TreatRecordInfoActivity.class);
                    startActivity(intent);
                    finish();

                }
            });

        }else{
            tvAgain.setText("否");
        }

    }
}
