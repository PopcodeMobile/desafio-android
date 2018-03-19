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

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Character> getResults() {
        return results;
    }

}
