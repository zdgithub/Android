package com.seven.health.aboutmedicine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seven.health.R;
import com.seven.health.bean.Medicine;
import com.seven.health.bean.TreatRecord;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by HuangXin on 2018/8/24.
 */

public class MedicineRecordAdapter extends BaseAdapter {
    private List<String> medicineRecords;
    private LayoutInflater layoutInflater;

    public MedicineRecordAdapter(List<String>medicineRecords,LayoutInflater layoutInflater){
        this.medicineRecords=medicineRecords;
        this.layoutInflater=layoutInflater;
    }

    @Override
    public int getCount() {
        return medicineRecords.size();
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
        String record=medicineRecords.get(position);

        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.medicine_info_item,null);
            viewHolder.medicine_content=(TextView)convertView.findViewById(R.id.medicine_content);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.medicine_content.setText(record);

        return convertView;
    }

    class ViewHolder{
        TextView medicine_content;
    }

}
