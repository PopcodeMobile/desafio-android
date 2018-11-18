package com.example.administrador.starwarswiki.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofitSwapi;
    private final Retrofit retrofitApiary;

    public RetrofitConfig() {
        OkHttpClient okHttpClient = new OkHttpClient();
        JacksonConverterFactory jacksonConverterFactory = JacksonConverterFactory.create();

        this.retrofitSwapi = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://swapi.co/")
                .addConverterFactory(jacksonConverterFactory)
                .build();
        this.retrofitApiary = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://private-782d3-starwarsfavorites.apiary-mock.com/")
                .addConverterFactory(jacksonConverterFactory)
                .build();
    }

    public SwapiService getSwapiService() {
        return this.retrofitSwapi.create(SwapiService.class);
    }
    public ApiaryService getApiaryService() {
        return this.retrofitApiary.create(ApiaryService.class);
    }
}
