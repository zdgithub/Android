package com.seven.health.bean;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveDepart {

    private String depart_name;
    private int depart_id;

    public ReserveDepart(String depart_name, int depart_id) {
        this.depart_name = depart_name;
        this.depart_id = depart_id;
    }

    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
    }

    public int getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(int depart_id) {
        this.depart_id = depart_id;
    }
}
