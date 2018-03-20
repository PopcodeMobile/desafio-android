package com.example.lucvaladao.entrevistapopcode.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lucvaladao on 3/19/18.
 */

@Entity
public class Character {

    @NonNull
    @PrimaryKey
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "height")
    @SerializedName("height")
    private String height;

    @ColumnInfo(name = "mass")
    @SerializedName("mass")
    private String mass;

    @ColumnInfo(name = "hair_color")
    @SerializedName("hair_color")
    private String hairColor;

    @ColumnInfo(name = "skin_color")
    @SerializedName("skin_color")
    private String skinColor;

    @ColumnInfo(name = "eye_color")
    @SerializedName("eye_color")
    private String eyeColor;

    @ColumnInfo(name = "birth_year")
    @SerializedName("birth_year")
    private String birthYear;

    @ColumnInfo(name = "gender")
    @SerializedName("gender")
    private String gender;

    @ColumnInfo(name = "homeworld")
    @SerializedName("homeworld")
    private String homeworld;

    @ColumnInfo(name = "species")
    @SerializedName("species")
    private List<String> species;

    @ColumnInfo(name = "created")
    @SerializedName("created")
    private String created;

    @ColumnInfo(name = "edited")
    @SerializedName("edited")
    private String edited;

    @ColumnInfo(name = "url")
    @SerializedName("url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getSpecies() {
        return species;
    }

    public void setSpecies(List<String> species) {
        this.species = species;
    }

}
