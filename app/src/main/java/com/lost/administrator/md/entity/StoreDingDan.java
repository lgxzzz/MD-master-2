package com.lost.administrator.md.entity;

import java.io.Serializable;

/**
 * Created
 */
public class StoreDingDan implements Serializable {
    private  int id;
    private String user, name,type,money,price,picture,bianhao,time;

    public StoreDingDan() {
    }


    public StoreDingDan(int id, String user, String name, String type, String money, String price, String picture, String bianhao, String time) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.type = type;
        this.money = money;
        this.price = price;
        this.picture = picture;
        this.bianhao = bianhao;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

    public String getpicture() {
        return picture;
    }

    public void setpicture(String picture) {
        this.picture = picture;
    }

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
