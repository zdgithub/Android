package com.seven.health.abouttreat.TreatReservation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seven.health.R;
import com.seven.health.bean.ReserveDepart;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveDepartAdapter extends BaseAdapter{
    private List<ReserveDepart> lists;
    private LayoutInflater layoutInflater;

    //自定义构造器，接受数据和加载布局
    public ReserveDepartAdapter(List<ReserveDepart>lists, LayoutInflater layoutInflater){
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
        ReserveDepart reservationDepartmentList=lists.get(position);
        if(convertView==null){

            convertView=layoutInflater.inflate(R.layout.treat_reservation_step2_item,null);

            viewHolder.treat_reservation_department=(TextView)convertView.findViewById(R.id.treat_reservation_department);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.treat_reservation_department.setText(reservationDepartmentList.getDepart_name());
        return convertView;
    }
    class ViewHolder{
        TextView treat_reservation_department;

    }

}
