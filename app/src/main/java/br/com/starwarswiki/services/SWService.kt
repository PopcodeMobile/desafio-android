package br.com.starwarswiki.services

import br.com.starwarswiki.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SWService {

    @GET("people")
    fun getPeople(@Query("page") page: Int): Call<ServerResponse<Person>>

    @GET("planets")
    fun getPlanets(@Query("page") page: Int): Call<ServerResponse<Planet>>

    @GET("species")
    fun getSpecies(@Query("page") page: Int): Call<ServerResponse<Specie>>
}