package com.android.vferreirati.starwarscharacters.services;

import com.android.vferreirati.starwarscharacters.models.Planet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlanetService {

    @GET("planets/{Id}")
    Call<Planet> getPlanet(
            @Path("Id") int planetId,
            @Query("format") String format
    );
}
