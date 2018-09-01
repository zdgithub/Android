package com.seven.health.abouttreat.TreatReservation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.health.R;
import com.seven.health.bean.ReserveDoc;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveDocAdapter extends BaseAdapter {
    private List<ReserveDoc> lists;
    private LayoutInflater layoutInflater;

    //自定义构造器，接受数据和加载布局
    public ReserveDocAdapter(List<ReserveDoc>lists, LayoutInflater layoutInflater){
        this.lists=lists;
        this.layoutInflater=layoutInflater;
    }

    //获取item数量
    @Override
    public int getCount() {
        return lists.size();
    }

    //获取item位置
    @Override
    public Object getItem(int position) {
        return position;
    }

    //获取item的ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder创建对象
        ViewHolder viewHolder=new ViewHolder();
        //获取item下的对象
        ReserveDoc reserveDocList=lists.get(position);
        if(convertView==null){

            convertView=layoutInflater.inflate(R.layout.treat_reservation_step3_item,null);

            viewHolder.sex=(ImageView) convertView.findViewById(R.id.treat_reservation_doctor_sex);
            viewHolder.name=(TextView)convertView.findViewById(R.id.treat_reservation_doctor_name);
            viewHolder.indication=(TextView)convertView.findViewById(R.id.treat_reservation_indication);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.name.setText(reserveDocList.getDoctor_name());
        viewHolder.indication.setText(reserveDocList.getInfo());
        if(reserveDocList.getSex().equals("男")){
            viewHolder.sex.setImageResource(R.mipmap.treat_man_doctor);
        }else viewHolder.sex.setImageResource(R.mipmap.treat_woman_doctor);

        return convertView;
    }
    class ViewHolder{
        ImageView sex;
        TextView name;
        TextView indication;


    }
}
