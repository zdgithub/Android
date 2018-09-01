package com.seven.health.aboutmedicine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.seven.health.MedicineFragment;
import com.seven.health.R;
import com.seven.health.bean.Medicine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HuangXin on 2018/8/23.
 */

public class MedicineInfoActivity extends AppCompatActivity {

    private ListView lv;
    Map<String,Medicine> mp = new HashMap<String,Medicine>();
    private List show_info = new ArrayList() ;
    private MedicineRecordAdapter adapter;
    private LayoutInflater inflater;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.medicine_info);
        inflater=getLayoutInflater();
        lv=(ListView)findViewById(R.id.medicine_record_list);

        Bundle bundle=getIntent().getExtras();


        String drug_name=bundle.getString("DrugName");

        mp = MedicineFragment.getMp();

        Medicine m = mp.get(drug_name);

        show_info.add(m.showDrug_name());
        show_info.add(m.showIndication());
        show_info.add(m.showDrug_usage());
        show_info.add(m.showReaction());
        show_info.add(m.showSpecial_crowd());
        show_info.add(m.showAttention());

        adapter =  new MedicineRecordAdapter(show_info,inflater);
        lv.setAdapter(adapter);

    }


    @Override
    public void onBackPressed(){
        this.finish();
//        Intent intent=new Intent(MedicineInfoActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
//        intent.putExtra("id",1);
//        startActivity(intent);
    }
}
