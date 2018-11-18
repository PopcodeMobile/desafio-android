package com.example.administrador.starwarswiki.network;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://swapi.co/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public SwapiService getService() {
        return this.retrofit.create(SwapiService.class);
    }
}
