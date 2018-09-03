package com.popcode.starwars.data.remote.response;

import com.google.gson.annotations.SerializedName;
import com.popcode.starwars.data.local.entity.CharacterElement;

import java.util.List;

public class CharactersResponse {

    @SerializedName("results")
    public List<CharacterElement> results;

    @SerializedName("next")
    public String next;

    @SerializedName("previous")
    public String previous;

    @SerializedName("count")
    public String count;
}
