package com.hfad.gaslevelapp;

import com.google.gson.annotations.SerializedName;

public class GasObject {

    @SerializedName("id")
    private int id;
    @SerializedName("weight")
    private String weight;

    public GasObject(int id, String weight) {
        this.id = id;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
