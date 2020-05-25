package com.lost.administrator.md.entity;


import java.io.Serializable;

public class ShangjiaInfo implements Serializable {
    private int id;
    private String shopName;//店名
    private String name;//负责人
    private String phone;//负责人联系方式
    private String adress;//店铺地址
    private String picture;//营业执照
    private String type;  //经营类型
    private String createtime;
    private String isExamine;//是否审核 1代表未审核   2代表审核未通过 3代表审核通过
    public ShangjiaInfo() {
    }

    public ShangjiaInfo(int id, String shopName, String name, String phone, String adress, String picture, String type, String createtime) {
        this.id = id;
        this.shopName = shopName;
        this.name = name;
        this.phone = phone;
        this.adress = adress;
        this.picture = picture;
        this.type = type;
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getIsExamine() {
        return isExamine;
    }

    public void setIsExamine(String isExamine) {
        this.isExamine = isExamine;
    }
}
