package com.andeptrai.doantotnghiep.data.model;

public class BillReservationOrdered extends BillReservation {

    private String nameRestaurant;
    private int statusConfirm;

    public BillReservationOrdered(String idBill, int idUserOrder, String idRestaurant, String timeCreateBill
            , String datetimeGo, int adultsNumber, int childrenNumber, String notes, String nameRestaurant, int statusConfirm) {
        super(idBill, idUserOrder, idRestaurant, timeCreateBill, datetimeGo, adultsNumber, childrenNumber, notes);
        this.nameRestaurant = nameRestaurant;
        this.statusConfirm = statusConfirm;
    }

    public int getStatusConfirm() {
        return statusConfirm;
    }

    public void setStatusConfirm(int statusConfirm) {
        this.statusConfirm = statusConfirm;
    }

    public String getNameRestaurant() {
        return nameRestaurant;
    }

    public void setNameRestaurant(String nameRestaurant) {
        this.nameRestaurant = nameRestaurant;
    }
}
