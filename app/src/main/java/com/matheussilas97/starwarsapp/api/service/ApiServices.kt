package com.matheussilas97.starwarsapp.api.service

import com.matheussilas97.starwarsapp.api.response.CharactersDetailsResponse
import com.matheussilas97.starwarsapp.api.response.CharactersListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("people/")
    fun listCharacters(@Query("page")page: Int): Call<CharactersListResponse>

    @GET("people/{id}")
    fun details(@Path("id")id: String): Call<CharactersDetailsResponse>

}