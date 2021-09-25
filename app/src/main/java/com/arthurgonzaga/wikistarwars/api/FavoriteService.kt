package com.arthurgonzaga.wikistarwars.api

import com.arthurgonzaga.wikistarwars.api.responses.SpecieResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteService {

    @POST("/favorite/{id}")
    fun setFavorite(
        @Path("id") id: Int
    ): Call<Unit>
}