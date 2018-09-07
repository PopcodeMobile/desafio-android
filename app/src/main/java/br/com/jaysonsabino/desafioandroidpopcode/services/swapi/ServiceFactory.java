package br.com.jaysonsabino.desafioandroidpopcode.services.swapi;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServiceFactory {

    private static final String BASE_URL = "https://swapi.co/api/";
    private final Retrofit retrofit;

    public ServiceFactory() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public PeopleService getPeopleService() {
        return retrofit.create(PeopleService.class);
    }

    public PlanetService getPlanetService() {
        return retrofit.create(PlanetService.class);
    }
}
