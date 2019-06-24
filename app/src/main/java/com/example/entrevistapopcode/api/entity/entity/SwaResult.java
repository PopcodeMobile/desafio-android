package com.example.entrevistapopcode.api.entity.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SwaResult {

    @SerializedName("count")
    public int count;
    @SerializedName("next")
    public String next;
    @SerializedName("previous")
    public String previous;
    @SerializedName("results")
    public List<Person> results;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Person> getResults() {
        return results;
    }

}
