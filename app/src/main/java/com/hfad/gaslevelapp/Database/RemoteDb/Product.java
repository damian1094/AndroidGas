package com.hfad.gaslevelapp.Database.RemoteDb;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("path")
    private String path;
    @SerializedName("weight")
    private String weight;
    @SerializedName("price")
    private String price;

    public Product(int id, String name, String path, String weight, String price) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.weight = weight;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
