package com.example.lucvaladao.entrevistapopcode.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lucvaladao on 3/19/18.
 */

public class CharacterBook {

    @SerializedName("count")
    public int count;
    @SerializedName("next")
    public String next;
    @SerializedName("previous")
    public String previous;
    @SerializedName("results")
    public List<Character> results;

}
