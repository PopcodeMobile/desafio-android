package br.com.star_wars_wiki.database.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.star_wars_wiki.entity.Favorite;
import br.com.star_wars_wiki.entity.People;

public class Converters {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    public static Favorite convertePeopleToFavorite(People people){
        Favorite favorite = new Favorite();

        favorite.setName(people.getName());
        favorite.setBirthYear(people.getBirthYear());
        favorite.setGender(people.getGender());
        favorite.setHairColor(people.getHairColor());
        favorite.setHeight(people.getHeight());
        favorite.setHomeWorldUrl(people.getHomeWorldUrl());
        favorite.setMass(people.getMass());
        favorite.setEyeColor(people.getEyeColor());
        favorite.setSkinColor(people.getSkinColor());
        favorite.setCreated(people.getCreated());
        favorite.setEdited(people.getEdited());
        favorite.setUrl(people.getUrl());
        favorite.setFilmsUrls(people.getFilmsUrls());
        favorite.setSpeciesUrls(people.getSpeciesUrls());
        favorite.setStarshipsUrls(people.getStarshipsUrls());
        favorite.setVehiclesUrls(people.getVehiclesUrls());
        favorite.setNextPage(people.getNextPage());

        return favorite;
    }
}
