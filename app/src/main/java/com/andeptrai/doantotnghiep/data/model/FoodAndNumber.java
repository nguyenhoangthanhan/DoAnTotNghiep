package com.andeptrai.doantotnghiep.data.model;

public class FoodAndNumber {
    private String nameFood;
    private int number;

    public FoodAndNumber(String nameFood, int number) {
        this.nameFood = nameFood;
        this.number = number;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
