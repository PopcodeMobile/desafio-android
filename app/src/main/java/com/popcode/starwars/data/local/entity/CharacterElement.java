package com.popcode.starwars.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "character_table")
public class CharacterElement {

    public Integer id;

    @PrimaryKey
    @NonNull
    @SerializedName("name")
    public String name;

    @SerializedName("height")
    public String height;

    @SerializedName("mass")
    public String mass;

    @SerializedName("eye_color")
    public String eyeColor;

    public Boolean loved = false;

    @SerializedName("hair_color")
    public String hairColor;

    @SerializedName("skin_color")
    public String skinColor;

    @SerializedName("birth_year")
    public String birthYear;

    @SerializedName("homeworld")
    public String homeworld;

    @SerializedName("species")
    @Ignore
    public List<String> species;

    public String specie;
    @SerializedName("gender")
    public String gender;

    @SerializedName("created")
    public String created;

    @SerializedName("edited")
    public String edited;

    @SerializedName("url")
    public String url;
}