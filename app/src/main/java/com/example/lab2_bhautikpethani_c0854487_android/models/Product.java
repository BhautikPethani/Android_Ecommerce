package com.example.lab2_bhautikpethani_c0854487_android.models;

public class Product {
    private int id;
    private String name, description;
    private double price;

    public Product(int id, String name, String desc, double price){
        this.id = id;
        this.name = name;
        this.description = desc;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription(){ return description;}

    public double getPrice(){return price;}
}
