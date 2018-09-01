package com.seven.health.aboutpersonal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seven.health.R;
import com.seven.health.bean.CollectionRecord;

import java.util.List;

/**
 * Created by Administrator on 2018/8/20.
 */

public class CollectionrecordAdapter extends BaseAdapter {

    private List<CollectionRecord> CollectionRecords;
    private LayoutInflater layoutInflater;

    public CollectionrecordAdapter(List<CollectionRecord>CollectionRecords,LayoutInflater layoutInflater){
        this.CollectionRecords=CollectionRecords;
        this.layoutInflater=layoutInflater;
    }

    @Override
    public int getCount() {
        return CollectionRecords.size();
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
        CollectionRecord record=CollectionRecords.get(position);
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.personal_news_item,null);

            viewHolder.personal_news_title=(TextView)convertView.findViewById(R.id.personal_news_title);
            viewHolder.personal_news_date=(TextView)convertView.findViewById(R.id.personal_news_date);

            convertView.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.personal_news_title.setText(record.getNews_name());
        viewHolder.personal_news_date.setText(record.getNews_time().substring(0,19));

        return convertView;
    }

    class ViewHolder{
        TextView personal_news_title;
        TextView personal_news_date;
    }
}
