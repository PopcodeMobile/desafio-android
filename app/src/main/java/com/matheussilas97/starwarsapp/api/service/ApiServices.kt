package com.matheussilas97.starwarsapp.api.service

import com.matheussilas97.starwarsapp.api.response.CharactersListResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("people")
    fun listCharacters(): Call<CharactersListResponse>

}