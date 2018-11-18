package com.example.administrador.starwarswiki.network;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiaryService {

    @POST("/favorite/{id}")
    Call<Object> postFavoriteWithHeader(@Path("id") int id, @Header("Prefer") String value);

    @POST("/favorite/{id}")
    Call<Object> postFavorite(@Path("id") int id);

}
