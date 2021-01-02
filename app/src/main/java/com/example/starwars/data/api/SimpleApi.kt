package com.example.starwars.data.api

import com.example.starwars.model.People
import com.example.starwars.util.Constants.Companion.ENDPOINT_PEOPLE
import retrofit2.Response
import retrofit2.http.GET

interface SimpleApi {

    //Endpoint Listar Personagens
    @GET(ENDPOINT_PEOPLE)
    suspend fun getPeople(): Response<List<People>>

}