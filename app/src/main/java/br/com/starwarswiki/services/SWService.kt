package br.com.starwarswiki.services

import br.com.starwarswiki.models.*
import retrofit2.Call
import retrofit2.http.GET

interface SWService {

    @GET("people")
    fun getPeople(): Call<ServerResponse<Person>>

    @GET("planets")
    fun getPlanets(): Call<ServerResponse<Planet>>

    @GET("species")
    fun getSpecies(): Call<ServerResponse<Specie>>
}