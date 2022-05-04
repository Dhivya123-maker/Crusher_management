package com.example.crushermanagement.entries;

import java.io.Serializable;

public class Entry_Model {
    String material;

    public Entry_Model(String material, String unit, String price) {
        this.material = material;
        this.unit = unit;
        this.price = price;
    }

    String unit;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String price;

    public  Entry_Model(){}
}
