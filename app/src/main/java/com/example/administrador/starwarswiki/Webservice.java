package com.example.administrador.starwarswiki;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Webservice {
    /**
     * @GET declares an HTTP GET request
     * @Path("id") annotation on the id parameter marks it as a
     * replacement for the {id} placeholder in the @GET path
     */
    @GET("/people/{id}")
    Call<StarWarsCharacter> getStarWarsCharacter(@Path("id") int id);
}

