package com.example.starwarsapi.service

import com.example.starwarsapi.model.api.Starwars
import com.example.starwarsapi.model.api.StarWarsApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApiService {
    @GET("people/{id}")
    fun getStarWarsInfo(@Path("id") id: Int): Call<Starwars>
    @GET("people")
    fun getStarWarsList(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<StarWarsApiResponse>
}