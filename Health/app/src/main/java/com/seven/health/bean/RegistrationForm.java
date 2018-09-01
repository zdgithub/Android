package com.seven.health.bean;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/20.
 */

public class RegistrationForm {
    private String hospital;
    private String department;
    private String doctor;
    private int number;
    private Date date;
    private String week;
    private String half;

    public RegistrationForm(String hospital,String department,String doctor,int number,Date date,String week,String half){
        this.hospital=hospital;
        this.department=department;
        this.doctor=doctor;
        this.number=number;
        this.date=date;
        this.week=week;
        this.half=half;
    }
    public String getHospital(){
        return hospital;
    }

    public void setHospital(String hospital){
        this.hospital=hospital;
    }

    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department){
        this.department=department;
    }

    public String getDoctor(){
        return doctor;
    }

    public void setDoctor(String doctor){
        this.doctor=doctor;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number=number;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date=date;
    }

    public String getWeek(){
        return week;
    }

    public void setWeek(String week){
        this.week=week;
    }

    public String getHalf (){
        return half;
    }

    public void setHalf(String half){
        this.half=half;
    }
}
