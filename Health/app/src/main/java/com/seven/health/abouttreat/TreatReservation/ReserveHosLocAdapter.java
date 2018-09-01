package com.seven.health.abouttreat.TreatReservation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seven.health.R;
import com.seven.health.bean.ReserveHosLoc;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveHosLocAdapter extends BaseAdapter{
    private List<ReserveHosLoc> form;
    private LayoutInflater layoutInflater;

    //自定义构造器，接受数据和加载布局
    public ReserveHosLocAdapter(List<ReserveHosLoc>form, LayoutInflater layoutInflater){
        this.form=form;
        this.layoutInflater=layoutInflater;
    }

    //获取item数量
    @Override
    public int getCount() {
        return form.size();
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
        ReserveHosLoc reservationHospitalLoc=form.get(position);
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.treat_reservation_step1_item,null);

            viewHolder.treat_reservation_hospital=(TextView)convertView.findViewById(R.id.treat_reservation_hospital);
            viewHolder.treat_reservation_distance=(TextView)convertView.findViewById(R.id.treat_reservation_distance);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.treat_reservation_hospital.setText(reservationHospitalLoc.getHospital_name());
        viewHolder.treat_reservation_distance.setText(String.valueOf(reservationHospitalLoc.getDistance()));

        return convertView;
    }
    class ViewHolder{
        TextView treat_reservation_hospital;
        TextView treat_reservation_distance;

    }
}
