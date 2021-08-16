package com.siedg.desafio_android.data.api

import com.siedg.desafio_android.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("people")
    suspend fun getPersonList(@Query("page") index: Int): Response<PersonList>

    @GET("planets")
    suspend fun getPlanetsList(@Query("page") index: Int): Response<PlanetList>

    @GET("species")
    suspend fun getSpeciesList(@Query("page") index: Int): Response<SpecieList>
}