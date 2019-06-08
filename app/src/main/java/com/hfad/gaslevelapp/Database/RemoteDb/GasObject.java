package com.hfad.gaslevelapp.Database.RemoteDb;

import com.google.gson.annotations.SerializedName;

public class GasObject {

    @SerializedName("id")
    private int id;
    @SerializedName("weight")
    private float weight;

    public GasObject(int id, float weight) {
        this.id = id;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
