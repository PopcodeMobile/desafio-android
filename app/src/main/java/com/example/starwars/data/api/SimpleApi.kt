package com.example.starwars.data.api

import com.example.starwars.model.People
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {

    //Endpoint Listar Personagens
    @GET("people")
    suspend fun getPeople(
        @Query("page") page: String
    ): Response<People>

}