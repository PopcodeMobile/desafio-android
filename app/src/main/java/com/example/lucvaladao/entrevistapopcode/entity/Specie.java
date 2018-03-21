package com.example.lucvaladao.entrevistapopcode.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lucvaladao on 3/20/18.
 */

public class Specie implements Serializable {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}