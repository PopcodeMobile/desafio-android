package com.example.desafio_android.data.api

import com.example.desafio_android.data.model.PeopleResponse
import com.example.desafio_android.data.model.Planet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RequestApi {

    @GET("people")
    suspend fun getPeople(
        @Query("page") page: Int
    ): Response<PeopleResponse?>

    @GET("people")
    suspend fun searchPeople(
        @Query("search") search: String
    ): Response<PeopleResponse?>

    @GET
    suspend fun getNamePlanet(@Url url: String): Response<Planet?>

}