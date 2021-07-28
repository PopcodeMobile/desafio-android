package com.example.desafio_android.data.api

import com.example.desafio_android.data.model.ApiFavoriteResponse
import com.example.desafio_android.data.model.PeopleResponse
import com.example.desafio_android.data.model.Planet
import com.example.desafio_android.data.model.Specie
import com.example.desafio_android.util.Constants.Companion.BASE_URL_FAVORITE
import com.example.desafio_android.util.Resultado
import org.jsoup.Connection
import retrofit2.Response
import retrofit2.http.*

interface RequestApi {

    @GET("people")
    suspend fun getPeople(
        @Query("page") page: Int
    ): Response<PeopleResponse?>

    @GET("people")
    suspend fun searchPeople(
        @Query("search") search: String
    ): Response<PeopleResponse?>

    @GET
    suspend fun getNamePlanet(@Url url: String): Response<Planet?>

    @GET
    suspend fun getNameSpecies(@Url url: String): Response<Specie?>

    @POST(BASE_URL_FAVORITE+"favorite/{id}")
    suspend fun addFavoriteApi(
        @Path("id") id: Int
    ): Response<ApiFavoriteResponse?>

}