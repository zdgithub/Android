package com.seven.health.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/20.
 */

public class CollectionRecord implements Serializable{
    private int news_id;
    private String news_name;
    private int news_category;
    private String news_content;
    private String news_time;

    public CollectionRecord() {
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getNews_name() {
        return news_name;
    }

    public void setNews_name(String news_name) {
        this.news_name = news_name;
    }

    public int getNews_category() {
        return news_category;
    }

    public void setNews_category(int news_category) {
        this.news_category = news_category;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public String getNews_time() {
        return news_time;
    }

    public void setNews_time(String news_time) {
        this.news_time = news_time;
    }
}
