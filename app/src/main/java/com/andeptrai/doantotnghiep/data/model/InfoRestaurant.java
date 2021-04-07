package com.andeptrai.doantotnghiep.data.model;

public class InfoRestaurant {
    private String id_restaurant;
    private String name_restaurant;
    private int phone_restaurant;
    private String password;
    private String address_restaurant;
    private double review_point;
    private int status_restaurant;
    private String short_description;
    private String promotion;

    public InfoRestaurant() {
    }

    public InfoRestaurant(String id_restaurant, String name_restaurant, int phone_restaurant
            , String password, String address_restaurant, double review_point, int status_restaurant) {
        this.id_restaurant = id_restaurant;
        this.name_restaurant = name_restaurant;
        this.phone_restaurant = phone_restaurant;
        this.password = password;
        this.address_restaurant = address_restaurant;
        this.review_point = review_point;
        this.status_restaurant = status_restaurant;
    }

    public InfoRestaurant(String id_restaurant, String name_restaurant, int phone_restaurant
            , String password, String address_restaurant, double review_point
            , int status_restaurant, String short_description, String promotion) {
        this.id_restaurant = id_restaurant;
        this.name_restaurant = name_restaurant;
        this.phone_restaurant = phone_restaurant;
        this.password = password;
        this.address_restaurant = address_restaurant;
        this.review_point = review_point;
        this.status_restaurant = status_restaurant;
        this.short_description = short_description;
        this.promotion = promotion;
    }

    public InfoRestaurant(String name_restaurant, String address_restaurant, float review_point, String short_description, String promotion) {
        this.name_restaurant = name_restaurant;
        this.address_restaurant = address_restaurant;
        this.review_point = review_point;
        this.short_description = short_description;
        this.promotion = promotion;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(String id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public String getName_restaurant() {
        return name_restaurant;
    }

    public void setName_restaurant(String name_restaurant) {
        this.name_restaurant = name_restaurant;
    }

    public int getPhone_restaurant() {
        return phone_restaurant;
    }

    public void setPhone_restaurant(int phone_restaurant) {
        this.phone_restaurant = phone_restaurant;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress_restaurant() {
        return address_restaurant;
    }

    public void setAddress_restaurant(String address_restaurant) {
        this.address_restaurant = address_restaurant;
    }

    public double getReview_point() {
        return review_point;
    }

    public void setReview_point(double review_point) {
        this.review_point = review_point;
    }

    public int getStatus_restaurant() {
        return status_restaurant;
    }

    public void setStatus_restaurant(int status_restaurant) {
        this.status_restaurant = status_restaurant;
    }
}
