package com.seven.health.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/20.
 */

public class TreatRecord implements Serializable{
    private String hospital;
    private String department;
    private String doctor;
    private Date date;
    private String description;
    private String result;
    private int again_id;
    private int record_id;

    public TreatRecord() {
    }

    public TreatRecord(String hospital, String department, Date date){
        this.hospital=hospital;
        this.date=date;
        this.department=department;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getAgain_id() {
        return again_id;
    }

    public void setAgain_id(int again_id) {
        this.again_id = again_id;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id){
        this.record_id = record_id;
    }
}
