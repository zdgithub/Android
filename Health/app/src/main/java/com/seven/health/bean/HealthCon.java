package com.seven.health.bean;

/**
 * Created by lenovo on 2018/8/27.
 */

public class HealthCon {
    private int height;
    private int weight;
    private String conDate;
    private String infection;
    private String chronic;
    private String genetic;
    private String truename;
    private String sex;
    private String birthday;

    public HealthCon() {
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getConDate() {
        return conDate;
    }

    public void setConDate(String conDate) {
        this.conDate = conDate;
    }

    public String getInfection() {
        return infection;
    }

    public void setInfection(String infection) {
        this.infection = infection;
    }

    public String getChronic() {
        return chronic;
    }

    public void setChronic(String chronic) {
        this.chronic = chronic;
    }

    public String getGenetic() {
        return genetic;
    }

    public void setGenetic(String genetic) {
        this.genetic = genetic;
    }
}
