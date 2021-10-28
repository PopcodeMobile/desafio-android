package com.matheussilas97.starwarsapp.api.service

import com.matheussilas97.starwarsapp.api.response.CharactersDetailsResponse
import com.matheussilas97.starwarsapp.api.response.CharactersListResponse
import com.matheussilas97.starwarsapp.api.response.HomeWorldResponse
import com.matheussilas97.starwarsapp.api.response.SpeciesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiServices {

    @GET("people/")
    fun listCharacters(@Query("page") page: Long): Call<CharactersListResponse>

    @GET
    fun details(@Url url: String): Call<CharactersDetailsResponse>

    @GET
    fun getHomeWorld(@Url url: String): Call<HomeWorldResponse>

    @GET
    fun getSpecies(@Url url: String): Call<SpeciesResponse>

}