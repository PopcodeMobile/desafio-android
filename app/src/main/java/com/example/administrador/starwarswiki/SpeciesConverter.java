package com.example.administrador.starwarswiki;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;

public class SpeciesConverter {

    @TypeConverter
    public static String toString(ArrayList<String> strings){
        return String.join(",",strings);
    }

    @TypeConverter
    public static ArrayList<String> toArray(String string){
        return new ArrayList<String>(Arrays.asList(string.split(",")));
    }
}
