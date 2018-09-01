package com.seven.health.abouttreat.TreatRecord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seven.health.R;
import com.seven.health.bean.TreatRecord;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/8/20.
 */

public class TreatRecordAdapter extends BaseAdapter {

    private List<TreatRecord>treatRecords;
    private LayoutInflater layoutInflater;

    public TreatRecordAdapter(List<TreatRecord>treatRecords,LayoutInflater layoutInflater){
        this.treatRecords=treatRecords;
        this.layoutInflater=layoutInflater;
    }

    @Override
    public int getCount() {
        return treatRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        TreatRecord record=treatRecords.get(position);

        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.treat_record_item,null);

            viewHolder.treat_record_hospital=(TextView)convertView.findViewById(R.id.treat_record_hospital);
            viewHolder.treat_record_date=(TextView)convertView.findViewById(R.id.treat_record_date);
            viewHolder.treat_record_department=(TextView)convertView.findViewById(R.id.treat_record_department);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        viewHolder.treat_record_hospital.setText(record.getHospital());
        viewHolder.treat_record_date.setText(ft.format(record.getDate()));
        viewHolder.treat_record_department.setText(record.getDepartment());

        return convertView;
    }
    class ViewHolder{
        TextView treat_record_hospital;
        TextView treat_record_date;
        TextView treat_record_department;
    }
}
