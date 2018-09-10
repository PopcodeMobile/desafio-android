package br.com.jaysonsabino.desafioandroidpopcode.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character implements Serializable {

    private static final String CHARACTER_URL_PATTERN = "https://swapi.co/api/people/([0-9]+)/";

    @PrimaryKey
    private int id;
    private String name;
    private String height;
    private String mass;
    @JsonProperty("hair_color")
    private String hairColor;
    @JsonProperty("skin_color")
    private String skinColor;
    @JsonProperty("eye_color")
    private String eyeColor;
    @JsonProperty("birth_year")
    private String birthYear;
    private String gender;
    private String url;
    private String created;
    /**
     * TODO O ideal para os campos homeworld e species seria salvar apenas o id
     */
    private String homeworld;
    @Ignore
    private List<String> species;
    private Integer specie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public Integer getHomeworldId() {
        return Planet.getIdByUrl(homeworld);
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public List<String> getSpecies() {
        return species;
    }

    public void setSpecies(List<String> species) {
        this.species = species;
    }

    public Integer getSpecie() {
        return specie;
    }

    public void setSpecie(Integer specie) {
        this.specie = specie;
    }

    public void setIdByUrl() {
        Pattern pattern = Pattern.compile(CHARACTER_URL_PATTERN);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            String id = matcher.group(1);
            this.id = Integer.parseInt(id);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return name.equals(character.name) &&
                url.equals(character.url);
    }

    @Override
    public int hashCode() {
        return (name + url).hashCode();
    }

    public static class CharacterWithFavorite implements Serializable {

        @Embedded
        private Character character;
        @Embedded
        private FavoriteCharacter favoriteCharacter;

        public Character getCharacter() {
            return character;
        }

        public void setCharacter(Character character) {
            this.character = character;
        }

        public FavoriteCharacter getFavoriteCharacter() {
            return favoriteCharacter;
        }

        public void setFavoriteCharacter(FavoriteCharacter favoriteCharacter) {
            this.favoriteCharacter = favoriteCharacter;
        }
    }
}
