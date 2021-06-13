package com.andeptrai.doantotnghiep.data.model;

import java.io.Serializable;

public class Restaurant extends InfoRestaurant implements Serializable {

    private String listKind;

    public Restaurant() {
    }

    public Restaurant(String id_restaurant, String name_restaurant, String phone_restaurant, String password
            , String address_restaurant, double review_point, int status_restaurant, String short_description
            , String promotion, String listKind) {
        super(id_restaurant, name_restaurant, phone_restaurant, password, address_restaurant, review_point
                , status_restaurant, short_description, promotion);
        this.listKind = listKind;
    }

    public String getListKind() {
        return listKind;
    }

    public void setListKind(String listKind) {
        this.listKind = listKind;
    }
}
