package br.com.jaysonsabino.desafioandroidpopcode.services.starwarsfavorites;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class StarWarsFavoritesServiceFactory {

    private static final String BASE_URL = "http://private-782d3-starwarsfavorites.apiary-mock.com/";
    private final Retrofit retrofit;

    public StarWarsFavoritesServiceFactory() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public StarWarsFavoritesService getService() {
        return retrofit.create(StarWarsFavoritesService.class);
    }
}
