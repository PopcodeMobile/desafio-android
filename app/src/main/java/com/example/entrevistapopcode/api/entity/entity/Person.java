package com.example.entrevistapopcode.api.entity.entity;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.entrevistapopcode.util.GithubTypeConverters;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


@Entity(tableName = "personagem_table")
public class Person implements Serializable {


    private int id;

    private Boolean loved = false;

    @PrimaryKey
    @NonNull
    @SerializedName("name")
    private String name;


    @SerializedName("height")
    private String height;


    @SerializedName("mass")
    private String mass;


    @SerializedName("hair_color")
    private String hairColor;

    @SerializedName("skin_color")
    private String skinColor;


    @SerializedName("eye_color")
    private String eyeColor;


    @SerializedName("birth_year")
    private String birthYear;


    @SerializedName("gender")
    private String gender;


    @SerializedName("homeworld")
    private String homeworld;


    @SerializedName("species")
    @TypeConverters(GithubTypeConverters.class)
    private List<String> species;


    @SerializedName("created")
    private String created;


    @SerializedName("edited")
    private String edited;


    @SerializedName("url")
    private String url;


    public String getName() {
        return name;
    }

    public String getHeight() {
        return height;
    }

    public String getMass() {
        return mass;
    }

    public String getHairColor() {
        return hairColor;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getGender() {
        return gender;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public List<String> getSpecies() {
        return species;
    }

    public String getCreated() {
        return created;
    }

    public String getEdited() {
        return edited;
    }

    public String getUrl() {
        return url;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public void setSpecies(List<String> species) {
        this.species = species;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getLoved() {
        return loved;
    }

    public void setLoved(Boolean loved) {
        this.loved = loved;
    }


}


