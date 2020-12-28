package com.example.desafiopopcode.Controllers;

import com.example.desafiopopcode.Models.Personagem;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Path;

public interface FavController {

    @POST("/favorite/{id}")
    public void favPerson(@Path("id") int id, Callback<Personagem> callback);

}
