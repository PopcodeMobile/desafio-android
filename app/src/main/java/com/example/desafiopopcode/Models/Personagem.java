package com.example.desafiopopcode.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Personagem implements Serializable {

    public int id;

    public String name;

    @SerializedName("birth_year")
    public String birthYear;

    public String gender;

    @SerializedName("hair_color")
    public String hairColor;

    public String height;

    @SerializedName("homeworld")
    public String homeWorldUrl;

    public String mass;

    @SerializedName("eye_color")
    public String eyeColor;

    @SerializedName("skin_color")
    public String skinColor;

    public String created;
    public String edited;
    public String url;

    @SerializedName("films")
    public ArrayList<String> filmsUrls;

    @SerializedName("species")
    public ArrayList<String> speciesUrls;

    @SerializedName("starships")
    public ArrayList<String> starshipsUrls;

    @SerializedName("vehicles")
    public ArrayList<String> vehiclesUrls;

    public int getId() {
        if (url.length() == 30) {
            char aux = url.charAt(url.length() - 2);
            id = Character.getNumericValue(aux);
        }
        else {
            String str;
            char aux1 = url.charAt(url.length() - 3);
            char aux2 = url.charAt(url.length() - 2);
            StringBuilder sb = new StringBuilder();
            sb.append(aux1);
            sb.append(aux2);
            str = sb.toString();
            id = Integer.parseInt(str);
        }
        return id;
    }

    @Override
    public String toString() {
        return "\nName: " + name
                + "\nHeight: " + height
                + "\nGender: " + gender
                + "\nMass: " + mass
                + "\nId: " + getId() + "\n";
    }

    public String detalhar() {
        return "\nName: " + name
                + "\nHeight: " + height
                + "\nMass: " + mass
                + "\nHair color: " + hairColor
                + "\nSkin color: " + skinColor
                + "\nEye color: " + eyeColor
                + "\nBirth year: " + birthYear
                + "\nGender: " + gender
                + "\nHomeworld: " + homeWorldUrl
                + "\nSpecies: " + speciesUrls + "\n";
    }

}