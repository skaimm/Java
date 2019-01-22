package com.example.ultraslan.wordmemorization.Model;

import java.io.Serializable;

public class StoreProducts implements Serializable{

    private int id;
    private int price;
    private String description;

    public StoreProducts(int id, int price, String description) {
        this.id = id;
        this.price = price;
        this.description = description;
    }

    public StoreProducts() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  description;
    }
}
