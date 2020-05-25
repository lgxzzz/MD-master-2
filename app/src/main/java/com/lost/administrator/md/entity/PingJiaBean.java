package com.lost.administrator.md.entity;

import com.lost.administrator.md.activity.SearchActivity;

public class PingJiaBean {
    private int id;
    private String user;
    private String goodName;
    private String comment;
    private String ratbar;
    private String time;

    public PingJiaBean() {
    }

    public PingJiaBean(int id, String user, String goodName, String comment, String ratbar, String time) {
        this.id = id;
        this.user = user;
        this.goodName = goodName;
        this.comment = comment;
        this.ratbar = ratbar;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRatbar() {
        return ratbar;
    }

    public void setRatbar(String ratbar) {
        this.ratbar = ratbar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
