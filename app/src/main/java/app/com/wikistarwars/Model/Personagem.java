package app.com.wikistarwars.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Personagem extends RealmObject{

    @PrimaryKey
    @SerializedName("name")
    private String name;
    @SerializedName("height")
    private String height;
    @SerializedName("gender")
    private String gender;
    @SerializedName("mass")
    private String mass;
    @SerializedName("hair_color")
    private String hair_color;
    @SerializedName("skin_color")
    private String skin_color;
    @SerializedName("eye_color")
    private String eye_color;
    @SerializedName("birth_year")
    private String birth_year;
    @SerializedName("homeworld")
    private String homeworld;
    @SerializedName("species")
    private RealmList<String> species;
//    private boolean favourite;


//    public boolean isFavourite() {
//        return favourite;
//    }
//
//
//    public void setFavourite(boolean favourite) {
//           this.favourite = favourite;
//    }

    public RealmList<String> getSpecies() {
        return species;
    }

    public void setSpecies(RealmList<String> species) {
        this.species = species;
    }

    public String getHair_color() {
        return hair_color;
    }

    public void setHair_color(String hair_color) {
        this.hair_color = hair_color;
    }

    public String getSkin_color() {
        return skin_color;
    }

    public void setSkin_color(String skin_color) {
        this.skin_color = skin_color;
    }

    public String getEye_color() {
        return eye_color;
    }

    public void setEye_color(String eye_color) {
        this.eye_color = eye_color;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }


    public Personagem(String name, String height, String gender, String mass, String hair_color, String skin_color, String eye_color, String birth_year, String homeWorld, RealmList<String> species) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.gender = gender;
        this.hair_color = hair_color;
        this.skin_color = skin_color;
        this.eye_color = eye_color;
        this.birth_year = birth_year;
        this.homeworld = homeWorld;
        this.species = species;
    }

    public Personagem(){

    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

}
