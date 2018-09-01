package com.seven.health.abouttreat.TreatReservation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.health.MainActivity;
import com.seven.health.R;
import com.seven.health.bean.ReserveDocSchedule;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveScheduleAdapter extends BaseAdapter {
    private List<ReserveDocSchedule> schedules;
    private LayoutInflater layoutInflater;
    private Context context;

    private ReserveDocSchedule reserveDocSchedule;


    //自定义构造器，接受数据和加载布局
    public ReserveScheduleAdapter(List<ReserveDocSchedule>schedules, LayoutInflater layoutInflater,Context context){
        this.schedules=schedules;
        this.layoutInflater=layoutInflater;
        this.context=context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //获取item数量
    @Override
    public int getCount() {
        return schedules.size();
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
        reserveDocSchedule=schedules.get(position);
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.treat_reservation_step4_item,null);

            viewHolder.date=(TextView)convertView.findViewById(R.id.treat_reservation_date);
            viewHolder.week=(TextView)convertView.findViewById(R.id.treat_reservation_week);
            viewHolder.half=(TextView)convertView.findViewById(R.id.treat_reservation_half);
            viewHolder.reserved=(TextView)convertView.findViewById(R.id.treat_reservation_reserved);
            viewHolder.remain=(TextView)convertView.findViewById(R.id.treat_reservation_remain);
            viewHolder.appointable=(Button) convertView.findViewById(R.id.treat_reservation_appointable);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.date.setText(reserveDocSchedule.getDate().toString());
        viewHolder.week.setText(reserveDocSchedule.getWeek());
        viewHolder.half.setText(reserveDocSchedule.getHalf());
        viewHolder.reserved.setText(String.valueOf(reserveDocSchedule.getReserved()));
        viewHolder.remain.setText(String.valueOf(reserveDocSchedule.getRemain()));

        //当可预约人数大于0时可预约按钮才显示
        if(reserveDocSchedule.getRemain() > 0){
            viewHolder.appointable.setVisibility(View.VISIBLE);

        }else{
            viewHolder.appointable.setVisibility(View.INVISIBLE);

        }

        return convertView;
    }


    class ViewHolder{
        TextView date;
        TextView week;
        TextView half;
        TextView reserved;
        TextView remain;
        Button  appointable;

    }


}
