package com.android.vferreirati.starwarscharacters.services;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharacterApi {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://swapi.co/api/people/";

    public static Retrofit getClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }

        return retrofit;
    }
}
