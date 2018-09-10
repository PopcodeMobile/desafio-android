package com.popcode.starwars.util;

public class Converters {

    public static Integer getIdFromUrl(String url) {
        String[] splitted = url.split("/");
        return Integer.valueOf(splitted[splitted.length - 1]);
    }
}
