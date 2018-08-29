package com.android.vferreirati.starwarscharacters.services;

import com.android.vferreirati.starwarscharacters.models.PeopleQuery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CharacterService {

    @GET("people")
    Call<PeopleQuery> getCharacters(
        @Query("format") String format,
        @Query("page") int pageIndex
    );
}
