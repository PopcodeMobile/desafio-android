package com.matheussilas97.starwarsapp.api.favorites

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoritesService {

    @POST("favorite/{id}")
    fun postFavorites(@Path("id") id: String): Call<FavoritesResponse>

}