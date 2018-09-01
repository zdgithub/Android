package com.seven.health.bean;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveHosLoc {


    private double distance;
    private String hospital_name;
    private int hospital_id;

    public ReserveHosLoc(String hospital_name, int hospital_id) {
        this.hospital_name = hospital_name;
        this.hospital_id = hospital_id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }
}
