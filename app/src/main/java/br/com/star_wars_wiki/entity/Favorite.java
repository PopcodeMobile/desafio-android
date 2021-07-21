package br.com.star_wars_wiki.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity
public class Favorite {
    //Não é uma boa prática colocar name como chave primária, mas para esse desafio acho aceitável
    @PrimaryKey
    @ColumnInfo(name = "name")
    @NonNull
    public String name;

    @ColumnInfo(name = "birth_year")
    @SerializedName("birth_year")
    public String birthYear;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "hair_color")
    @SerializedName("hair_color")
    public String hairColor;

    @ColumnInfo(name = "height")
    public String height;

    @ColumnInfo(name = "homeworld")
    @SerializedName("homeworld")
    public String homeWorldUrl;

    @ColumnInfo(name = "mass")
    public String mass;

    @ColumnInfo(name = "eye_color")
    @SerializedName("eye_color")
    public String eyeColor;

    @ColumnInfo(name = "skin_color")
    @SerializedName("skin_color")
    public String skinColor;

    @ColumnInfo(name = "created")
    public String created;

    @ColumnInfo(name = "edited")
    public String edited;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "films")
    @SerializedName("films")
    public ArrayList<String> filmsUrls;

    @ColumnInfo(name = "species")
    @SerializedName("species")
    public ArrayList<String> speciesUrls;

    @ColumnInfo(name = "starships")
    @SerializedName("starships")
    public ArrayList<String> starshipsUrls;

    @ColumnInfo(name = "vehicles")
    @SerializedName("vehicles")
    public ArrayList<String> vehiclesUrls;

    @ColumnInfo(name = "next_page")
    int nextPage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHomeWorldUrl() {
        return homeWorldUrl;
    }

    public void setHomeWorldUrl(String homeWorldUrl) {
        this.homeWorldUrl = homeWorldUrl;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
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

    public ArrayList<String> getFilmsUrls() {
        return filmsUrls;
    }

    public void setFilmsUrls(ArrayList<String> filmsUrls) {
        this.filmsUrls = filmsUrls;
    }

    public ArrayList<String> getSpeciesUrls() {
        return speciesUrls;
    }

    public void setSpeciesUrls(ArrayList<String> speciesUrls) {
        this.speciesUrls = speciesUrls;
    }

    public ArrayList<String> getStarshipsUrls() {
        return starshipsUrls;
    }

    public void setStarshipsUrls(ArrayList<String> starshipsUrls) {
        this.starshipsUrls = starshipsUrls;
    }

    public ArrayList<String> getVehiclesUrls() {
        return vehiclesUrls;
    }

    public void setVehiclesUrls(ArrayList<String> vehiclesUrls) {
        this.vehiclesUrls = vehiclesUrls;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }
}
