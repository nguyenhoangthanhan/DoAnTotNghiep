package com.andeptrai.doantotnghiep.data.model;

import java.io.Serializable;

public class Notify implements Serializable {
    private String id_notification;
    private String id_restaurant;
    private String title_notify;
    private String content_notification;
    private String time_create_notification;

    public Notify() {
    }

    public Notify(String id_notification, String id_restaurant, String title_notify, String content_notification, String time_create_notification) {
        this.id_notification = id_notification;
        this.id_restaurant = id_restaurant;
        this.title_notify = title_notify;
        this.content_notification = content_notification;
        this.time_create_notification = time_create_notification;
    }

    public Notify(String title_notify, String content_notification) {
        this.title_notify = title_notify;
        this.content_notification = content_notification;
    }

    public String getTitle_notify() {
        return title_notify;
    }

    public void setTitle_notify(String title_notify) {
        this.title_notify = title_notify;
    }

    public String getId_notification() {
        return id_notification;
    }

    public void setId_notification(String id_notification) {
        this.id_notification = id_notification;
    }

    public String getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(String id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public String getContent_notification() {
        return content_notification;
    }

    public void setContent_notification(String content_notification) {
        this.content_notification = content_notification;
    }

    public String getTime_create_notification() {
        return time_create_notification;
    }

    public void setTime_create_notification(String time_create_notification) {
        this.time_create_notification = time_create_notification;
    }
}
