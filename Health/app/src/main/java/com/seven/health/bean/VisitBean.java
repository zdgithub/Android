package com.seven.health.bean;

import java.util.Date;

/**
 * Created by lenovo on 2018/8/22.
 */

public class VisitBean {
    private int visit_id;
    private Date visit_date;
    private int early_num;
    private int late_num;

    public VisitBean() {
    }

    public VisitBean(int visit_id, Date visit_date, int early_num, int late_num) {
        this.visit_id = visit_id;
        this.visit_date = visit_date;
        this.early_num = early_num;
        this.late_num = late_num;
    }

    public int getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(int visit_id) {
        this.visit_id = visit_id;
    }

    public Date getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(Date visit_date) {
        this.visit_date = visit_date;
    }

    public int getEarly_num() {
        return early_num;
    }

    public void setEarly_num(int early_num) {
        this.early_num = early_num;
    }

    public int getLate_num() {
        return late_num;
    }

    public void setLate_num(int late_num) {
        this.late_num = late_num;
    }
}
