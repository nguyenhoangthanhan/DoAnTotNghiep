package com.andeptrai.doantotnghiep.data.model;

import java.io.Serializable;

public class Bill implements Serializable {
    private String idBill;
    private int idUserOrder;
    private String idRestaurant;
    private String timeCreateBill;

    public Bill(String idBill, int idUserOrder, String idRestaurant, String timeCreateBill) {
        this.idBill = idBill;
        this.idUserOrder = idUserOrder;
        this.idRestaurant = idRestaurant;
        this.timeCreateBill = timeCreateBill;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public int getIdUserOrder() {
        return idUserOrder;
    }

    public void setIdUserOrder(int idUserOrder) {
        this.idUserOrder = idUserOrder;
    }

    public String getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(String idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getTimeCreateBill() {
        return timeCreateBill;
    }

    public void setTimeCreateBill(String timeCreateBill) {
        this.timeCreateBill = timeCreateBill;
    }
}

