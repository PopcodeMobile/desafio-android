package com.knowledge.wikisw_luan.data

import com.knowledge.wikisw_luan.data.models.BasicResponse
import com.knowledge.wikisw_luan.data.models.SwResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SwAPI {
    @GET("people")
    suspend fun getCharacters(): SwResponse

    @GET("species/{id}/")
    suspend fun getSpecies(@Path("id") id: Int): BasicResponse

    @GET("homeworld/{id}/")
    suspend fun getPlanet(@Path("id") id: Int): BasicResponse
}