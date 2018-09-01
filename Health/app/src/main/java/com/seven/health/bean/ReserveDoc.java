package com.seven.health.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveDoc implements Serializable{

    private int doctor_id;
    private String doctor_name;
    private String sex;
    private String info;
    private int early_max_num;
    private int late_max_num;


    public ReserveDoc() {

    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getEarly_max_num() {
        return early_max_num;
    }

    public void setEarly_max_num(int early_max_num) {
        this.early_max_num = early_max_num;
    }

    public int getLate_max_num() {
        return late_max_num;
    }

    public void setLate_max_num(int late_max_num) {
        this.late_max_num = late_max_num;
    }

}
