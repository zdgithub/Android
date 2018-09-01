package com.seven.health.bean;

/**
 * Created by lenovo on 2018/8/20.
 */

public class FeedbackBean {
    private int hospital_id;
    private String comments;
    private int rating;
    private String time;

    public FeedbackBean() {
    }

    public FeedbackBean(int hospital_id, String comments, int rating, String time) {
        this.hospital_id = hospital_id;
        this.comments = comments;
        this.rating = rating;
        this.time = time;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
