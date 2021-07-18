package br.com.star_wars_wiki.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Root implements Serializable {
    @SerializedName("films")
    public String filmsUrl;
    @SerializedName("people")
    public String peopleUrl;
    @SerializedName("planets")
    public String planetsUrl;
    @SerializedName("species")
    public String speciesUrl;
    @SerializedName("starships")
    public String starshipsUrl;
    @SerializedName("vehicles")
    public String vehiclesUrl;
}
