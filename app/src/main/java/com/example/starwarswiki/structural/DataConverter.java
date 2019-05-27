package com.example.starwarswiki.structural;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public String listToJson(List<String> speciesList) {
        Type type = new TypeToken<List<String>>() {}.getType();
        String result = new Gson().toJson(speciesList, type);
        return result;
    }

    @TypeConverter
    public List<String> jsonToList(String speciesString) {
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> result = new Gson().fromJson(speciesString, type);
        return result;
    }
}
