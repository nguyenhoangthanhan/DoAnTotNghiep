package com.andeptrai.doantotnghiep.data.model;

import java.io.Serializable;

public class BillDeliveryOrdered extends  BillDelivery implements Serializable {

    private int statusConfirm;
    private String nameRestaurant;

    public BillDeliveryOrdered(String idBill, int idUserOrder, String idRestaurant, String timeCreateBill
            , String addressDelivery, String timeDelivery, long totalMoneyBill, String payment, String notes
            , String detailBill, String detailFood, int statusConfirm, String nameRestaurant) {
        super(idBill, idUserOrder, idRestaurant, timeCreateBill, addressDelivery, timeDelivery
                , totalMoneyBill, payment, notes, detailBill, detailFood);
        this.statusConfirm = statusConfirm;
        this.nameRestaurant = nameRestaurant;
    }

    public void setNameRestaurant(String nameRestaurant) {
        this.nameRestaurant = nameRestaurant;
    }

    public String getNameRestaurant() {
        return nameRestaurant;
    }

    public int getStatusConfirm() {
        return statusConfirm;
    }

    public void setStatusConfirm(int statusConfirm) {
        this.statusConfirm = statusConfirm;
    }
}
