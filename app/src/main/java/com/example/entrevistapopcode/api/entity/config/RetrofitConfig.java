package com.example.entrevistapopcode.api.entity.config;


import com.example.entrevistapopcode.api.entity.API;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;
    private final String URL_FAV = "http://private-782d3-starwarsfavorites.apiary-mock.com/";
    private  final String URL_BASE = "https://swapi.co/api/";

    public RetrofitConfig(boolean fav) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(fav ? URL_FAV: URL_BASE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



    public API getSwa() {
        return this.retrofit.create(API.class);
    }
}
