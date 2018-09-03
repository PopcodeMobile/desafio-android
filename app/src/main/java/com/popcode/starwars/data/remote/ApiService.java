package com.popcode.starwars.data.remote;

import com.popcode.starwars.data.local.entity.CharacterElement;
import com.popcode.starwars.data.remote.response.CharactersResponse;
import com.popcode.starwars.data.remote.response.GenericResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("people/")
    Call<CharactersResponse> getCharacters(@Query("page") Integer page);

    @GET("people/")
    Call<CharactersResponse> findCharacter(@Query("search") String query);

    @GET("planets/{id}/")
    Call<GenericResponse> getPlanet(@Path("id") Integer id);

    @GET("species/{id}/")
    Call<GenericResponse> getSpecie(@Path("id") Integer id);

    @GET("people/{id}/")
    Call<CharacterElement> getCharacter(@Path("id") Integer id);
}
