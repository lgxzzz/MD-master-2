package com.lost.administrator.md.entity;

/**
 * Created by
 */

public class EventBusBean {
    private String tag;

    private String city;

    private Store store;
    public String getCity() {
        return city;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public EventBusBean(String tag, Store store) {
        this.tag = tag;
        this.store = store;
    }

    public EventBusBean(String tag, String city) {
        this.tag = tag;
        this.city = city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public EventBusBean(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
