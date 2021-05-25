package com.andeptrai.doantotnghiep.data.model;

import java.io.Serializable;

public class Food implements Serializable {
    private String id_restaurant;
    private String id_food;
    private String name_food;
    private int status;
    private int price;
    private int promotion;

    public Food() {
    }

    public Food(String id_restaurant, String id_food, String name_food, int status, int price, int promotion) {
        this.id_restaurant = id_restaurant;
        this.id_food = id_food;
        this.name_food = name_food;
        this.status = status;
        this.price = price;
        this.promotion = promotion;
    }

    public String getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(String id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public String getId_food() {
        return id_food;
    }

    public void setId_food(String id_food) {
        this.id_food = id_food;
    }

    public String getName_food() {
        return name_food;
    }

    public void setName_food(String name_food) {
        this.name_food = name_food;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }
}
