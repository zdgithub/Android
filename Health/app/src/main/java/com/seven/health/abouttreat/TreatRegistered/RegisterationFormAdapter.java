package com.seven.health.abouttreat.TreatRegistered;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seven.health.R;
import com.seven.health.bean.RegistrationForm;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/8/20.
 */

public class RegisterationFormAdapter extends BaseAdapter {
    private List<RegistrationForm>form;
    private LayoutInflater layoutInflater;

    //自定义构造器，接受数据和加载布局
    public RegisterationFormAdapter(List<RegistrationForm>form,LayoutInflater layoutInflater){
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
        RegistrationForm registrationForm=form.get(position);
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.treat_registered_item,null);

            viewHolder.treat_register_hospital=(TextView)convertView.findViewById(R.id.treat_register_hospital);
            viewHolder.treat_register_department=(TextView)convertView.findViewById(R.id.treat_register_department);
            viewHolder.treat_register_doctor_name=(TextView)convertView.findViewById(R.id.treat_register_doctor);
            viewHolder.treat_register_number=(TextView)convertView.findViewById(R.id.treat_register_number);
            viewHolder.treat_register_date=(TextView)convertView.findViewById(R.id.treat_register_date);
            viewHolder.treat_register_week=(TextView)convertView.findViewById(R.id.treat_register_week);
            viewHolder.treat_register_half=(TextView)convertView.findViewById(R.id.treat_register_half);

            convertView.setTag(viewHolder); //否则会报空指针异常
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");


        viewHolder.treat_register_hospital.setText(registrationForm.getHospital());
        viewHolder.treat_register_department.setText(registrationForm.getDepartment());
        viewHolder.treat_register_doctor_name.setText(registrationForm.getDoctor());
        viewHolder.treat_register_number.setText(Integer.toString(registrationForm.getNumber()));
        viewHolder.treat_register_date.setText(ft.format(registrationForm.getDate()));
        viewHolder.treat_register_week.setText(registrationForm.getWeek());
        viewHolder.treat_register_half.setText(registrationForm.getHalf());

        return convertView;
    }
    class ViewHolder{
        TextView treat_register_hospital;
        TextView treat_register_department;
        TextView treat_register_doctor_name;
        TextView treat_register_number;
        TextView treat_register_date;
        TextView treat_register_week;
        TextView treat_register_half;

    }
}
