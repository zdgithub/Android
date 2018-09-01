package com.seven.health.bean;

/**
 * Created by Administrator on 2018/8/21.
 */

public class ReserveDocSchedule {
    private String date;   //日期
    private String week;   //星期
    private String half;   //上午和下午
    private int reserved;  //已预约人数
    private int remain;    //还剩余人数

    public ReserveDocSchedule() {
    }

    public ReserveDocSchedule(String date, String week, String half, int reserved, int remain) {
        this.date = date;
        this.week = week;
        this.half = half;
        this.reserved = reserved;
        this.remain = remain;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getHalf() {
        return half;
    }

    public void setHalf(String half) {
        this.half = half;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

}
