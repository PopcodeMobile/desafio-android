package com.android.vferreirati.starwarscharacters.services;

import com.android.vferreirati.starwarscharacters.models.Specie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpecieService {

    @GET("species/{Id}")
    Call<Specie> getSpecie(
            @Path("Id") int Id,
            @Query("format") String format
    );
}
