package com.seven.health;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seven.health.aboutmedicine.MedicineInfoActivity;
import com.seven.health.bean.Medicine;
import com.seven.health.util.OkhttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.seven.health.MainActivity.mActivity;

/**
 * Created by lenovo on 2018/8/16.
 */

public class MedicineFragment extends BasePageTitleFragment implements SearchView.OnQueryTextListener{

    public static String msg_from_db;

    private SearchView sv;
    private ListView lv;
    private final String[] mStrings={"aaa","bbb","ccc"};
    private TextView tv;
    private List<Medicine> records;
    private List<String> show_records = new ArrayList() ;
    private LayoutInflater inflater;
    private String id;

    static Map<String,Medicine> mp = new HashMap<String,Medicine>();

    private ArrayAdapter adapter;


    public static Map<String,Medicine> getMp(){
        return mp;
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch(msg.what){
                case 0:
                    break;
                case 200:
                    Gson gson = new Gson();
                    records = gson.fromJson(msg.obj.toString(),new TypeToken<List<Medicine>>(){}.getType());
                    int i ;
                    for(i=0;i<records.size();i++){
                        Medicine m = records.get(i);
                        mp.put(m.getDrug_name(),m);

                        show_records.add(m.getDrug_name());
                    }
                    adapter =  new ArrayAdapter<>(mActivity, android.R.layout.simple_list_item_1, show_records);
                    lv.setAdapter(adapter);
                    break;
                case 202:
                    break;
                default:
                    Log.e("response error code=",String.valueOf(msg.what));
                   // Toast.makeText(MainActivity.this, "网络不通，赶紧打开", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };



    @Override
    protected View initView() {
        setTitleText("药 物 查 询",true);
        View medicineFragment = View.inflate(getContext(),R.layout.fragment_medicine, null);

        lv=(ListView)medicineFragment.findViewById(R.id.lv);


        sv=(SearchView)medicineFragment.findViewById(R.id.searchView);
        sv.setOnQueryTextListener(this);
        sv.setSubmitButtonEnabled(true);

        return medicineFragment;
    }

    private void httpGet(final String url) {

        OkhttpUtil.okGet(url, handler);
    }

    @Override
    protected void initData() {

        httpGet("/drug");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getActivity(), MedicineInfoActivity.class);

                Bundle bundle=new Bundle();
                //传入数据

                bundle.putString("DrugName",parent.getAdapter().getItem(position).toString());

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }


    //用户输入字符时激发该方法
    @Override
    public boolean onQueryTextChange(String newText) {
        List<String> mList = new ArrayList<>();
        for(String s:show_records){
            if(s.trim().toLowerCase().contains(newText.trim().toLowerCase())){
                // 规则匹配的话就往集合中添加该数据
                mList.add(s);
            }

        }
        adapter =  new ArrayAdapter<>(mActivity, android.R.layout.simple_list_item_1, mList);
        lv.setAdapter(adapter);


        return true;
    }


    //单击搜索按钮时激发该方法
    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub


        return true;
    }



}

