package com.example.lenovo.mytime.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.mytime.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter  {
    private List<People> people = new ArrayList<People>();
    Context ct;
    private LayoutInflater inflater;

    public Adapter(Context ct, List<People> people) {
        // TODO Auto-generated constructor stub
        this.people = people;
        this.ct = ct;
        inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return people.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return people.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        People p = people.get(position);
        if(convertView==null){
            convertView = inflater.inflate(R.layout.people_item, null);
        }
        TextView tv1=(TextView)convertView.findViewById(R.id.name);
        TextView tv2=(TextView)convertView.findViewById(R.id.desc);
        tv1.setText(p.getName());
        tv2.setText(p.getPhome());
        return convertView;
    }


}