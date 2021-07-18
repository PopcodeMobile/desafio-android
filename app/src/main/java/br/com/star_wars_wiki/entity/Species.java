package br.com.star_wars_wiki.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Species implements Serializable {
    public String name;
    public String classification;
    public String designation;

    @SerializedName("average_height")
    public String averageHeight;

    @SerializedName("average_lifespan")
    public String averageLifespan;

    @SerializedName("eye_colors")
    public String eyeColors;

    @SerializedName("hair_colors")
    public String hairColors;

    @SerializedName("skin_colors")
    public String skinColors;

    @SerializedName("homeworld")
    public String homeWorld;

    public String language;
    public String created;
    public String edited;
    public String url;

    @SerializedName("people")
    public ArrayList<String> peopleUrls;

    @SerializedName("films")
    public ArrayList<String> filmsUrls;
}