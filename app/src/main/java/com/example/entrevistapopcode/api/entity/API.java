package com.example.entrevistapopcode.api.entity;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @GET("people")
    Call<SwaResult> getPerson();



    @GET("people/")
    Call<SwaResult> getPersonPage(@Query("page") String page);

    @GET("planets/{id}/")
    Observable<OnlyNameGeneric> getPlanet(@Path("id") Integer id);

    @GET("species/{id}/")
    Observable<OnlyNameGeneric> getSpecie(@Path("id") Integer id);

    @POST("favorite/{id}")
    Call<Favorito> postFavoriteCharacter(@Header("Prefer") String header, @Path("id") String id);


}
