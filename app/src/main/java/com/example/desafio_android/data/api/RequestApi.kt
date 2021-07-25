package com.example.desafio_android.data.api

import com.example.desafio_android.data.model.PeopleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestApi {

    @GET("people")
    suspend fun getPeople(
        @Query("page") page: Int
    ): Response<PeopleResponse?>

    @GET("people")
    suspend fun searchPeople(
        @Query("search") search: String
    ): Response<PeopleResponse?>

}