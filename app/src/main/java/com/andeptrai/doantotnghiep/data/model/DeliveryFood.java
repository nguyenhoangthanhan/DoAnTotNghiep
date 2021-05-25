package com.andeptrai.doantotnghiep.data.model;

import java.io.Serializable;

public class DeliveryFood extends Food  implements Serializable {

    public DeliveryFood(String id_restaurant, String id_food, String name_food, int status, int price
            , int promotion, int numberDelivery) {
        super(id_restaurant, id_food, name_food, status, price, promotion);
        this.numberDelivery = numberDelivery;
    }

    private int numberDelivery;

    public DeliveryFood(int numberDelivery) {
        this.numberDelivery = numberDelivery;
    }

    public int getNumberDelivery() {
        return numberDelivery;
    }

    public void setNumberDelivery(int numberDelivery) {
        this.numberDelivery = numberDelivery;
    }
}
