package com.example.administrador.starwarswiki;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://swapi.co/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public Webservice getService() {
        return this.retrofit.create(Webservice.class);
    }
}
