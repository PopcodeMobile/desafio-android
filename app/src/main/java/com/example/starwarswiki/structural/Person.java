package com.example.starwarswiki.structural;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.starwarswiki.handlers.DataConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "name",
        "height",
        "mass",
        "hair_color",
        "skin_color",
        "eye_color",
        "birth_year",
        "gender",
        "homeworld",
        "species"
})
@Entity(tableName = "person_table")
public class Person {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    @JsonProperty("name")
    private String name;
    @ColumnInfo(name = "height")
    @JsonProperty("height")
    private String height;
    @ColumnInfo(name = "mass")
    @JsonProperty("mass")
    private String mass;
    @ColumnInfo(name = "hair_color")
    @JsonProperty("hair_color")
    private String hairColor;
    @ColumnInfo(name = "skin_color")
    @JsonProperty("skin_color")
    private String skinColor;
    @ColumnInfo(name = "eye_color")
    @JsonProperty("eye_color")
    private String eyeColor;
    @ColumnInfo(name = "birth_year")
    @JsonProperty("birth_year")
    private String birthYear;
    @ColumnInfo(name = "gender")
    @JsonProperty("gender")
    private String gender;
    @ColumnInfo(name = "homeworld")
    @JsonProperty("homeworld")
    private String homeworldURL;
    @TypeConverters(DataConverter.class)
    @ColumnInfo(name = "species")
    @JsonProperty("species")
    private List<String> speciesURL = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("height")
    public String getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(String height) {
        this.height = height;
    }

    @JsonProperty("mass")
    public String getMass() {
        return mass;
    }

    @JsonProperty("mass")
    public void setMass(String mass) {
        this.mass = mass;
    }

    @JsonProperty("hair_color")
    public String getHairColor() {
        return hairColor;
    }

    @JsonProperty("hair_color")
    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    @JsonProperty("skin_color")
    public String getSkinColor() {
        return skinColor;
    }

    @JsonProperty("skin_color")
    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    @JsonProperty("eye_color")
    public String getEyeColor() {
        return eyeColor;
    }

    @JsonProperty("eye_color")
    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    @JsonProperty("birth_year")
    public String getBirthYear() {
        return birthYear;
    }

    @JsonProperty("birth_year")
    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("homeworld")
    public String getHomeworldURL() {
        return homeworldURL;
    }

    @JsonProperty("homeworld")
    public void setHomeworldURL(String homeworld) {
        this.homeworldURL = homeworld;
    }

    @JsonProperty("species")
    public List<String> getSpeciesURL() {
        return speciesURL;
    }

    @JsonProperty("species")
    public void setSpeciesURL(List<String> speciesURL) {
        this.speciesURL = speciesURL;
    }
}