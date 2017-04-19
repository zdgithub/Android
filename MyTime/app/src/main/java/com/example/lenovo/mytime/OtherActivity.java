package com.example.lenovo.mytime;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lenovo.mytime.model.Adapter;
import com.example.lenovo.mytime.model.People;

public class OtherActivity extends Activity {

    List<People> people = new ArrayList<People>() ;
    EditText editinput;
    ListView listview;
    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlist);
        editinput = (EditText)findViewById(R.id.edit);
        listview = (ListView)findViewById(R.id.lv);
        initdata();
        adapter = new Adapter(getApplicationContext(), people);
        listview.setAdapter(adapter);
        editinput.addTextChangedListener(new watcher());
    }

    void initdata(){

        people.add(new People("苏轼","1374456"));
        people.add(new People("李白", "12444455"));
        people.add(new People("杜甫", "1345555"));
        people.add(new People("王一", "1355555"));
        people.add(new People("王二", "1365555"));
        people.add(new People("李三", "13565555"));
        people.add(new People("李一", "123555"));
    }

    class watcher implements TextWatcher{

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
            String aa = s.toString();
            Pattern p = Pattern.compile(aa);
            List<People> we = new ArrayList<People>();
            for(int i=0;i<people.size();i++){
                People pp = people.get(i);
                Matcher matcher = p.matcher(pp.getName()+pp.getPhome());
                if(matcher.find()){
                    we.add(pp);
                }
            }
            adapter = new Adapter(getApplicationContext(), we);
            listview.setAdapter(adapter);
        }

    }


}
