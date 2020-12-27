package com.example.desafiopopcode.Controllers;

import com.example.desafiopopcode.Models.*;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface SWWiki {

    @GET("/people/")
    public void getAllPeople(@Query("page") int page, Callback<ListaPerson<Personagem>> callback);

    @GET("/people/{id}/")
    public void getPeople(@Path("id") int peopleId, Callback<Personagem> callback);

}