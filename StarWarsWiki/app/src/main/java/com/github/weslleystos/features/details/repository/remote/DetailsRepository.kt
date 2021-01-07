package com.github.weslleystos.features.details.repository.remote

import com.github.weslleystos.shared.entities.Planet
import com.github.weslleystos.shared.entities.Specie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface DetailsRepository {
    @GET
    fun getHomeWorld(@Url url: String): Call<Planet>

    @GET
    fun getSpecie(@Url url: String): Call<Specie>
}