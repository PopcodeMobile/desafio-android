package com.siedg.desafio_android.data.api

import com.siedg.desafio_android.data.model.Person
import com.siedg.desafio_android.data.model.PersonList
import com.siedg.desafio_android.data.model.Planet
import com.siedg.desafio_android.data.model.Specie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("people")
    suspend fun getPersonList(@Query("page") index: Int): Response<PersonList>

    @GET("planets/{index}/")
    suspend fun getPlanet(@Query("page") index: Int): Response<Planet>

    @GET("species/{index}/")
    suspend fun getSpecie(@Query("page") index: Int): Response<Specie>
}