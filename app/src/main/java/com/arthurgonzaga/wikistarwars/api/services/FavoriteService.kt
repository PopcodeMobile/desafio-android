package com.arthurgonzaga.wikistarwars.api.services

import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteService {

    @POST("favorite/{id}")
    @Headers(
        "Prefer: status=200",
        "Content-Type:application/json"
    )
    fun setFavorite(
        @Path("id") id: Int
    ): Call<Unit>
}